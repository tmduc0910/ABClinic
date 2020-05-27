package com.abclinic.asynctask;

import android.content.Context;

import com.abclinic.constant.HttpStatus;
import com.abclinic.constant.StorageConstant;
import com.abclinic.entity.Account;
import com.abclinic.exception.InternalServerErrorException;
import com.abclinic.exception.WrongCredentialException;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.LoginMapper;
import com.example.abclinic.R;

import java.net.SocketTimeoutException;

import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginTask extends CustomAsyncTask<Account, Void, String> {
    private String uid = null;
    private boolean isSuccess = false;

    public LoginTask(Context context, String storageKey) {
        super(context, storageKey);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    protected String doInBackground(Account... accounts) {
        Retrofit retrofit = RetrofitClient.getClient(null);
        LoginMapper loginMapper = retrofit.create(LoginMapper.class);
        try {
            Response<String> response = loginMapper.login(accounts[0]).execute();
            if (response.isSuccessful()) {
                uid = response.body();
                storageService.saveCache(StorageConstant.KEY_UID, uid);
                isSuccess = true;
                return uid;
            } else {
                switch (response.code()) {
                    case HttpStatus.NOT_FOUND:
                        this.exception = new WrongCredentialException(getStringResource(R.string.login_failed_err));
                        break;
                    default:
                        this.exception = new InternalServerErrorException(getStringResource(R.string.internal_server_err));
                }
                return null;
            }
        } catch (SocketTimeoutException e) {
            this.exception = new InternalServerErrorException(getStringResource(R.string.internal_server_err));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uid;
    }
}
