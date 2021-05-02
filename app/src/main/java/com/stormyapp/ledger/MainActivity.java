package com.stormyapp.ledger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String OTP_REGEX = "[0-9]{1,6}";
    public Button chooseContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseContact = findViewById(R.id.button2);
        checkAndRequestPermissions();
    }


    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private int checkRequiredPermissions () {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if (permission == -1){
            permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        }
        if (permission == -1) {
            permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        }
        if (permission == -1) {
            permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        }
        return permission;
    }

    private boolean checkAndRequestPermissions() {

        int sms = this.checkRequiredPermissions();

        if (sms != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_PHONE_STATE, 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_CONTACTS
                }, 1);
            }
        } else {
            Toast.makeText(MainActivity.this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
        }
        return  false;
    }

}