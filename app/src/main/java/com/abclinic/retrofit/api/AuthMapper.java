package com.abclinic.retrofit.api;

import com.abclinic.entity.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthMapper {
    @POST("auth/login")
    Call<String> login(@Body Account account);

    @POST("auth/sign_out")
    Call<Void> logout();
}
