package com.example.makinde.shakesnap;


import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class UseCamera extends AppCompatActivity {

    private Camera mCamera;
    private CameraView mCameraView;
    private SensorManager sm;
    private Sensor mySensor;
    private ShakeDetector mShakeDetector;
    public PictureHandler pictureCallback;

    public final static String DEBUG_TAG = "UseCamera";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mCameraView = new CameraView(this, mCamera);
        pictureCallback = new PictureHandler(getApplicationContext());
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_view);
        preview.addView(mCameraView);

        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake() {
                mCamera.takePicture(null, null, pictureCallback);
                sm.unregisterListener(mShakeDetector);

            }
        });

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);

        mySensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sm.registerListener(mShakeDetector, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private void releaseCameraAndPreview() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            mCameraView =null;
        }
    }

    public void restart(View view)
    {
        this.recreate();
    }
    @Override
    protected void onPause() {
        releaseCameraAndPreview();
        sm.unregisterListener(mShakeDetector);
        super.onPause();
    }


  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void startBrowsePicture(View view) {
        Intent intent = new Intent(this, BrowsePicture.class);
        startActivity(intent);

        //Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().build();
        //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //startActivity(intent);
    }
}
