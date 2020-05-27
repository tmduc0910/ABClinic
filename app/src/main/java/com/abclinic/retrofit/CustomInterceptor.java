package com.abclinic.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {
    private static CustomInterceptor instance;
    private String uid;
    private Request.Builder builder;

    private CustomInterceptor(String uid) {
        this.uid = uid;
    }

    public static CustomInterceptor getInstance(String uid) {
        if (instance == null)
            instance = new CustomInterceptor(uid);
        else instance.uid = uid;
        return instance;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        builder = original.newBuilder()
                .header("Client-Type", "Mobile")
                .header("Accept", "application/json")
                .header("Authorization", uid != null ? uid : "");
        return chain.proceed(builder.build());
    }
}
