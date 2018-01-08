package com.boc.myupdateversion;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.boc.updateversionlibrary.update.PermissionUtils;
import com.boc.updateversionlibrary.update.manager.UpdateManager;

public class MainActivity extends AppCompatActivity {
    private String[] writePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int MY_PERMISSION_REQUEST_CODE = 10009;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void update(View view){
        boolean ishavePermission = PermissionUtils.checkPermissionAllGranted(this,writePermission);
        if (!ishavePermission){
            ActivityCompat.requestPermissions(this,writePermission,MY_PERMISSION_REQUEST_CODE);
        }else {
            new UpdateManager(this).checkUpdate(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MY_PERMISSION_REQUEST_CODE){
            boolean permission = true;
            for (int grantResult : grantResults) {
                if (grantResult!= PackageManager.PERMISSION_GRANTED){
                    permission = false;
                    break;
                }
            }
            if (permission){
                new UpdateManager(this).checkUpdate(false);
            }else {
                PermissionUtils.openAppDetails(this);
            }
        }
    }
}
