package com.abclinic.retrofit.api;

import com.abclinic.entity.Notification;
import com.abclinic.entity.PageableEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotificationMapper {
    @GET("notifications")
    Call<PageableEntity<Notification>> getNotificationList(@Query("page") int page,
                                                           @Query("size") int size);

    @GET("notifications/{id}")
    Call<Notification> getNotification(@Path("id") long id);
}
