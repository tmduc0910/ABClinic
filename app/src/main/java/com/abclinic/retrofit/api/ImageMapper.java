package com.abclinic.retrofit.api;

import com.abclinic.dto.ResponseAlbumDto;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ImageMapper {
    @Multipart
    @POST("images")
    Call<ResponseAlbumDto> uploadImages(@Part MultipartBody.Part... files);

    @Multipart
    @POST("images/avatar")
    Call<String> uploadAvatar(@Part MultipartBody.Part... files);

    @GET("images")
    Call<List<String>> getImages(@Query("album_id") String albumId);
}
