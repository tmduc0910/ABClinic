package com.abclinic.asynctask;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.abclinic.exception.CustomRuntimeException;
import com.abclinic.utils.services.LocalStorageService;

public abstract class CustomAsyncTask<X, Y, Z> extends AsyncTask<X, Y, Z> {
    protected LocalStorageService storageService;
    protected AsyncResponse<Z> delegate = null;
    protected CustomRuntimeException exception;
    private Resources resources;

    public CustomAsyncTask(Context context, String storageKey, AsyncResponse<Z> delegate) {
        this.storageService = new LocalStorageService(context, storageKey);
        this.delegate = delegate;
        resources = context.getResources();
    }


    public CustomAsyncTask(Context context, String storageKey) {
        this.storageService = new LocalStorageService(context, storageKey);
        resources = context.getResources();
    }

    public void setDelegate(AsyncResponse<Z> delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Z z) {
        super.onPostExecute(z);
        if (delegate != null) {
            if (z != null) {
                delegate.processResponse(z);
            }
            if (exception != null) {
                delegate.handleException(exception);
            }
        }
    }

    protected String getStringResource(int id) {
        return resources.getString(id);
    }

    protected boolean useDialog() {
        return true;
    }
}
