package com.abclinic.utils;

import android.os.AsyncTask;

import com.abclinic.utils.services.JsonJavaConvertingService;
import com.abclinic.utils.services.RequestHandlingService;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public abstract class RequestTask extends AsyncTask<String, Void, String> {

    protected static String method;

    protected final String TAG = "DEBUG LOG";
    protected RequestHandlingService requestHandler = new RequestHandlingService();
    protected JsonJavaConvertingService converter = new JsonJavaConvertingService();
    protected SweetAlertDialog progressDialog;

    public SweetAlertDialog getProgressDialog() {
        return progressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading")
                .setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            //Log.d(DEBUG_TAG, "EXECUTING");
            if (RequestTask.method == "POST")
                return requestHandler.postRequest(strings[0].trim(), strings[1].trim());
            else if (RequestTask.method == "GET") return requestHandler.getRequest(strings[0]);
            else return null;
        } catch (IOException e) {
            return "Unable to retrieve data";
        }
    }

    @Override
    protected abstract void onPostExecute(String result);
}
