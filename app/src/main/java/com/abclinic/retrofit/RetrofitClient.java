package com.abclinic.retrofit;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient {
    private static final String LOCAL_URL_1 = "http://192.168.43.129:8109/api/";
    private static final String LOCAL_URL_2 = "http://192.168.1.24:8109/api/";
    private static final String REMOTE_URL = "https://fathomless-savannah-38522.herokuapp.com/api/";

    private static Retrofit retrofit = null;
    private static Retrofit.Builder builder;

    public static <T> Retrofit getClient(final String uid) {
        if (retrofit == null) {
            builder = new Retrofit.Builder()
                    .baseUrl(getUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .callbackExecutor(Executors.newCachedThreadPool());
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(CustomInterceptor.getInstance(uid))
                .connectTimeout(120, TimeUnit.SECONDS)
                .callTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
        return builder.client(client)
                .build();
    }

    public static String getUrl() {
        return REMOTE_URL;
    }

//
//    private static <T> Gson getConverter(JsonDeserializer<T> deserializer, Class<T> c) {
//        return new GsonBuilder()
//                .registerTypeAdapter(c, deserializer)
//                .create();
//    }
}
