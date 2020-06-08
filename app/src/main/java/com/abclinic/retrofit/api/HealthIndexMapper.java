package com.abclinic.retrofit.api;

import com.abclinic.dto.RequestCreateResultDto;
import com.abclinic.entity.HealthIndexSchedule;
import com.abclinic.entity.PageableEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HealthIndexMapper {
    @GET("health_indexes/schedule")
    Call<PageableEntity<HealthIndexSchedule>> getSchedules(@Query("page") int page, @Query("size") int size);

    @GET("health_indexes/schedule/{id}")
    Call<HealthIndexSchedule> getSchedule(@Path("id") long id);

    @POST("health_indexes/result")
    Call<Void> uploadResult(@Body RequestCreateResultDto requestCreateResultDto);
}
