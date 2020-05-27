package com.abclinic.retrofit.api;

import com.abclinic.dto.RequestUpdateInfoDto;
import com.abclinic.entity.UserInfo;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface UserInfoMapper {
    @GET("user")
    Call<UserInfo> getInfo();

    @PUT("user")
    Call<UserInfo> changeInfo(@Body RequestUpdateInfoDto request);

    @POST("images/avatar")
    Call<String> uploadAvatar(@Part MultipartBody.Part file);
}
