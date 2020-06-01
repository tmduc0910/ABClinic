package com.abclinic.retrofit.api;

import com.abclinic.dto.RequestCreateReplyDto;
import com.abclinic.entity.PageableEntity;
import com.abclinic.entity.Reply;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReplyMapper {
    @POST("replies")
    Call<Reply> createReply(@Body RequestCreateReplyDto requestCreateReplyDto);

    @GET("replies")
    Call<PageableEntity<Reply>> getReplies(@Query("inquiry-id") long id, @Query("page") int page, @Query("size") int size);
}
