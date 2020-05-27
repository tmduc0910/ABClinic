package com.abclinic.asynctask;

import android.content.Context;

import com.abclinic.constant.HttpStatus;
import com.abclinic.entity.Notification;
import com.abclinic.entity.PageableEntity;
import com.abclinic.exception.NotFoundException;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.NotificationMapper;
import com.example.abclinic.R;

import java.io.IOException;

import retrofit2.Response;

public class GetNotificationListTask extends CustomAsyncTask<Integer, Void, PageableEntity<Notification>> {
    public GetNotificationListTask(Context context, String storageKey, AsyncResponse<PageableEntity<Notification>> delegate) {
        super(context, storageKey, delegate);
    }

    @Override
    protected PageableEntity<Notification> doInBackground(Integer... integers) {
        try {
            String uid = storageService.getUid();
            Response<PageableEntity<Notification>> response = RetrofitClient.getClient(uid)
                    .create(NotificationMapper.class)
                    .getNotificationList(integers[0], integers[1]).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                switch (response.code()) {
                    case HttpStatus.NOT_FOUND:
                        this.exception = new NotFoundException(String.format(getStringResource(R.string.not_found_err), "thông báo"));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
