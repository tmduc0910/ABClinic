package com.abclinic.asynctask;

import android.content.Context;

import com.abclinic.entity.UserInfo;
import com.abclinic.room.entity.UserEntity;
import com.abclinic.room.utils.AppDatabase;

public class UpdateUserInfoTask extends CustomAsyncTask<UserInfo, Void, UserEntity> {
    private AppDatabase appDatabase;

    public UpdateUserInfoTask(AppDatabase appDatabase, Context context, String storageKey, AsyncResponse<UserEntity> delegate) {
        super(context, storageKey, delegate);
        this.appDatabase = appDatabase;
    }

    @Override
    protected UserEntity doInBackground(UserInfo... infos) {
        appDatabase.getUserDao().resetLogon();
        UserEntity data = appDatabase.getUserDao().getUser(infos[0].getId());
        UserEntity u = new UserEntity(infos[0]);
        if (data != null) {
            if (data.hashCode() != u.hashCode()) {
                data.clone(u);
                appDatabase.getUserDao().addUser(data);
                return data;
            }
        } else appDatabase.getUserDao().addUser(u);
        return u;
    }
}
