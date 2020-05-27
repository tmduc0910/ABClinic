package com.abclinic.asynctask;

import android.content.Context;

import com.abclinic.constant.StorageConstant;
import com.abclinic.entity.UserInfo;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.UserInfoMapper;

import java.io.IOException;

import retrofit2.Retrofit;

public class GetUserInfoTask extends CustomAsyncTask<String, Void, UserInfo> {
    public GetUserInfoTask(Context context, String storageKey, AsyncResponse<UserInfo> delegate) {
        super(context, storageKey, delegate);
    }

    @Override
    protected UserInfo doInBackground(String... strings) {
        UserInfo userInfo = null;
        Retrofit retrofit = RetrofitClient.getClient(strings[0]);

        UserInfoMapper userInfoMapper = retrofit.create(UserInfoMapper.class);

        try {
            userInfo = userInfoMapper.getInfo().execute().body();
            storageService.saveCache(StorageConstant.KEY_USER, userInfo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
