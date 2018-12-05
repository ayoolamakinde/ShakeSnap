package com.example.makinde.shakesnap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;

    public ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.image);
        if(!checkPermissionForCamera())
            requestPermissionForCamera();
    }

    public void startCamera(View view) {
        Intent intent = new Intent(this, UseCamera.class);
        startActivity(intent);
    }

    public void startBrowsePicture(View view) {
        Intent intent = new Intent(this, BrowsePicture.class);
        startActivity(intent);

        //Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().build();
        //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //startActivity(intent);
    }

    public boolean checkPermissionForCamera(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForCamera(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            Toast.makeText(this, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
