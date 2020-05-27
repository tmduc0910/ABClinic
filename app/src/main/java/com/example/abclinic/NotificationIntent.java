package com.example.abclinic;

import android.content.Context;
import android.content.Intent;

import com.abclinic.constant.Constant;

public class NotificationIntent extends Intent {
    public NotificationIntent(Context packageContext, Class<?> cls, int type, long id) {
        super(packageContext, cls);
        this.putExtra(Constant.TYPE, type);
        this.putExtra(Constant.PAYLOAD_ID, id);
    }
}
