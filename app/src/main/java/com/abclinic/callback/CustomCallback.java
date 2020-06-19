package com.abclinic.callback;

import android.graphics.Color;
import android.widget.Toast;

import com.abclinic.constant.HttpStatus;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.utils.services.LocalStorageService;
import com.example.abclinic.R;
import com.example.abclinic.activity.CustomActivity;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public abstract class CustomCallback<T> implements Callback<T> {
    protected CustomActivity context;
    protected Retrofit retrofit;
    private Map<Integer, String> exceptions;
    private SweetAlertDialog progressDialog;

    public CustomCallback(CustomActivity context) {
        this.context = context;
        LocalStorageService storageService = new LocalStorageService(context, null);
        retrofit = RetrofitClient.getClient(storageService.getUid());
        exceptions = new LinkedHashMap<>();

        if (useDialog()) {
            context.runOnUiThread(() -> {
                this.progressDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                progressDialog.setTitleText("Loading")
                        .setCancelable(false);
                progressDialog.show();
            });
        }
        exceptions.put(HttpStatus.INTERNAL_SERVER, context.getResources().getString(R.string.internal_server_err));
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        context.runOnUiThread(() -> {
            onEndAnimation();
            if (response.isSuccessful()) {
                processResponse(response);
            } else {
                processException(response);
            }
        });
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onEndAnimation();
        t.printStackTrace();
        Toast.makeText(context, "Lỗi kết nối máy chủ. Xin vui lòng thử lại.", Toast.LENGTH_LONG).show();
    }

    protected abstract void processResponse(Response<T> response);

    protected void processException(Response<T> response) {
        Toast.makeText(context, getMessage(response.code()), Toast.LENGTH_LONG).show();
    }

    protected void onEndAnimation() {
        if (progressDialog != null) {
            progressDialog.dismissWithAnimation();
        }
    }

    public CustomCallback<T> handle(int code, int stringId) {
        exceptions.put(code, context.getResources().getString(stringId));
        return this;
    }

    public CustomCallback<T> handle(int code, int stringId, String... formats) {
        exceptions.put(code, context.getResources().getString(stringId, formats));
        return this;
    }

    protected boolean useDialog() {
        return true;
    }

    public String getMessage(int code) {
        return exceptions.get(code);
    }
}
