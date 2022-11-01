package com.example.cit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

public class PermissionChecking extends AppCompatActivity {

    RxPermissions rxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(this);
    }

    private void PermissionCheck(){
        rxPermissions.requestEachCombined(Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.SEND_SMS).subscribe(permission -> {
            if(permission.granted){
                if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    Context context = getApplicationContext();
                    // Check whether has the write settings permission or not.
                    boolean settingsCanWrite = false;
                    settingsCanWrite = Settings.System.canWrite(context);
                    if (settingsCanWrite) {
                        // If do not have write settings permission then open the Can modify system settings panel.
                        Toast.makeText(PermissionChecking.this,
                                "Please select Allow modify system setting", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                        startActivity(intent);
                    } else {
                        // If has permission then show an alert dialog with message.
                        Intent intent = new Intent(PermissionChecking.this, MainActivity.class);
                        this.startActivity(intent);
                    }

                }
                else{
                    Intent intent = new Intent(PermissionChecking.this, MainActivity.class);
                    this.startActivity(intent);
                }
            }
            else if (permission.shouldShowRequestPermissionRationale) {
                // Denied permission without ask never again
                Toast.makeText(PermissionChecking.this,
                        "Give us a Permission",Toast.LENGTH_SHORT).show();
                PermissionCheck();
            } else {
                Toast.makeText(PermissionChecking.this,
                        "Give us a Permission in setting",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                // Denied permission with ask never again
                // Need to go to the settings
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        PermissionCheck();
    }
}
