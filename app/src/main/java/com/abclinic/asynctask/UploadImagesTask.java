package com.abclinic.asynctask;

import android.content.Context;

import com.abclinic.dto.RecyclerImageDto;
import com.abclinic.dto.ResponseAlbumDto;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.ImageMapper;
import com.abclinic.utils.FileUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import okhttp3.MultipartBody;

public class UploadImagesTask extends CustomAsyncTask<RecyclerImageDto, Void, ResponseAlbumDto> {
    private ResponseAlbumDto album;

    public UploadImagesTask(Context context, String storageKey, AsyncResponse<ResponseAlbumDto> delegate) {
        super(context, storageKey, delegate);
    }

    @Override
    protected ResponseAlbumDto doInBackground(RecyclerImageDto... recyclerImageDtos) {
        MultipartBody.Part[] files = Arrays.stream(recyclerImageDtos)
                .map(i -> FileUtils.getPart(i.getPath()))
                .collect(Collectors.toList())
                .toArray(new MultipartBody.Part[0]);

//        MultipartBody[] files = Arrays.stream(recyclerImageDtos)
//                .map(i -> new MultipartBody.Builder()
//                .addFormDataPart("files", i.getPath(), RequestBody.create(MediaType.parse("media/type"), new File(i.getPath())))
//                .setType(MultipartBody.FORM)
//                .build())
//                .collect(Collectors.toList())
//                .toArray(new MultipartBody[0]);
        String uid = storageService.getUid();
        ImageMapper imageMapper = RetrofitClient.getClient(uid).create(ImageMapper.class);
        try {
            album = imageMapper.uploadImages(files).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return album;
    }
}
