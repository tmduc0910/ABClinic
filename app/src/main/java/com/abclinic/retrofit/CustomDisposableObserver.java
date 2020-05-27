package com.abclinic.retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.abclinic.constant.Constant;
import com.abclinic.constant.HttpError;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public abstract class CustomDisposableObserver<T> extends DisposableObserver<T> {
    private List<HttpError> errors = new ArrayList<>();
    private Context context;

    public CustomDisposableObserver<T> handleError(int code, String message) {
        this.errors.add(new HttpError(code, message));
        return this;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            errors.forEach(er -> {
                if (er.getCode() == ((HttpException) e).code())
                    Toast.makeText(context, er.getMessage(), Toast.LENGTH_LONG).show();
            });
        }
    }

    @Override
    public void onComplete() {
        Log.d(Constant.DEBUG_TAG, "Hoàn thành");
    }
}
