package com.abclinic.utils.services;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class PermissionUtils {
    public static final int REQUEST_CAMERA = 0;
    public static final int REQUEST_READ_EXT_STORAGE = 1;
    public static final int REQUEST_WRITE_EXT_STORAGE = 2;
    public static final int REQUEST_INTERNET = 3;

    public static final String PERM_CAMERA = Manifest.permission.CAMERA;
    public static final String PERM_READ_EXT_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERM_WRITE_EXT_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String PERM_INTERNET = Manifest.permission.INTERNET;

    private PermissionGrantedExecuter executer;

    public PermissionUtils(PermissionGrantedExecuter executer) {
        this.executer = executer;
    }

    public void checkPermission(Activity context, String permission, String detail, int code) {
        if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            executer.execute();
        } else {
            if (context.shouldShowRequestPermissionRationale(permission)) {
                Toast.makeText(context, "Đã phân quyền cho " + detail + " thành công!!", Toast.LENGTH_LONG).show();
            }
            context.requestPermissions(new String[]{permission}, code);
        }
    }

    public void grantedPermission(Activity context, int[] grantedResults) {
        if (grantedResults[0] == PackageManager.PERMISSION_GRANTED) {
            executer.execute();
        } else {
            Toast.makeText(context, "Phân quyền thất bại", Toast.LENGTH_LONG).show();
        }
    }

    public interface PermissionGrantedExecuter {
        void execute();
    }
}
