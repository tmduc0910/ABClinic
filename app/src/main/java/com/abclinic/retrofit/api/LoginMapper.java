package com.abclinic.retrofit.api;

import com.abclinic.entity.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginMapper {
    @POST("auth/login")
    Call<String> login(@Body Account account);
}
