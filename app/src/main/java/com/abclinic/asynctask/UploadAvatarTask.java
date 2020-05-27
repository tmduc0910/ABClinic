package com.abclinic.asynctask;

import android.content.Context;

import com.abclinic.constant.HttpStatus;
import com.abclinic.exception.BadRequestException;
import com.abclinic.exception.InternalServerErrorException;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.ImageMapper;
import com.example.abclinic.R;

import java.io.IOException;

import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadAvatarTask extends CustomAsyncTask<MultipartBody.Part, Void, String> {
    public UploadAvatarTask(Context context, String storageKey, AsyncResponse<String> delegate) {
        super(context, storageKey, delegate);
    }

    @Override
    protected String doInBackground(MultipartBody.Part... parts) {
        String uid = storageService.getUid();
        Retrofit retrofit = RetrofitClient.getClient(uid);
        try {
            Response<String> response = retrofit.create(ImageMapper.class).uploadAvatar(parts[0]).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                switch (response.code()) {
                    case HttpStatus.BAD_REQUEST:
                        exception = new BadRequestException(getStringResource(R.string.image_type_err));
                        break;
                    default:
                        exception = new InternalServerErrorException(getStringResource(R.string.internal_server_err));

                }
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
