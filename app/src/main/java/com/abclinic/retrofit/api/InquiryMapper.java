package com.abclinic.retrofit.api;

import com.abclinic.dto.RequestCreateInquiryDto;
import com.abclinic.entity.Inquiry;
import com.abclinic.entity.PageableEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InquiryMapper {
    @POST("inquiries")
    Call<Inquiry> createInquiry(@Body RequestCreateInquiryDto requestCreateInquiryDto);

    @GET("inquiries")
    Call<PageableEntity<Inquiry>> getInquiryList(@Query("page") int page,
                                                 @Query("size") int size);

    @GET("inquiries/monthly")
    Call<List<Inquiry>> getInquiryListByMonth(@Query("month") int month,
                                              @Query("year") int year);

    @GET("inquiries/{id}")
    Call<Inquiry> getInquiry(@Path("id") long id);
}
