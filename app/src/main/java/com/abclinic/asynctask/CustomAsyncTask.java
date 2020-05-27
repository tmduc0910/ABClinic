package com.abclinic.asynctask;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;

import com.abclinic.exception.CustomRuntimeException;
import com.abclinic.utils.services.LocalStorageService;

import cn.pedant.SweetAlert.SweetAlertDialog;

public abstract class CustomAsyncTask<X, Y, Z> extends AsyncTask<X, Y, Z> {
    protected LocalStorageService storageService;
    protected AsyncResponse<Z> delegate = null;
    protected CustomRuntimeException exception;
    private SweetAlertDialog progressDialog;
    private Resources resources;

    public CustomAsyncTask(Context context, String storageKey, AsyncResponse<Z> delegate) {
        this.storageService = new LocalStorageService(context, storageKey);
        this.delegate = delegate;
        resources = context.getResources();
        this.progressDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
    }


    public CustomAsyncTask(Context context, String storageKey) {
        this.storageService = new LocalStorageService(context, storageKey);
        resources = context.getResources();
        this.progressDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
    }

    public void setDelegate(AsyncResponse<Z> delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressDialog != null) {
            progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            progressDialog.setTitleText("Loading")
                    .setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected void onPostExecute(Z z) {
        super.onPostExecute(z);
        if (progressDialog != null)
            progressDialog.dismissWithAnimation();
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
}
