package com.abclinic.retrofit.api;

import com.abclinic.dto.RequestUpdateInfoDto;
import com.abclinic.entity.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface UserInfoMapper {
    @GET("user")
    Call<UserInfo> getInfo();

    @PUT("user")
    Call<UserInfo> changeInfo(@Body RequestUpdateInfoDto request);
}
