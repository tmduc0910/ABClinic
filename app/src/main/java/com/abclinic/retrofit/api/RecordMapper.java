package com.abclinic.retrofit.api;

import com.abclinic.entity.Record;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecordMapper {
    @GET("records/{id}")
    Call<Record> getRecord(@Path("id") long id, @Query("type") int type);
}
