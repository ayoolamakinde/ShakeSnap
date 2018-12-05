package com.example.makinde.shakesnap;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class ShakeDetector implements SensorEventListener {


    private static final int FORCE_THRESHOLD = 350;


    private static final int TIME_THRESHOLD = 300;


    private static final int SHAKE_TIMEOUT = 1000;


    private static final int SHAKE_DURATION = 1000;

    private static final int SHAKE_COUNT = 2;

    private float mLastX=-1.0f, mLastY=-1.0f, mLastZ=-1.0f;
    private long mLastTime;
    private int mShakeCount = 0;
    private long mLastShake;
    private long mLastForce;
    private boolean onShake = false;

    private OnShakeListener mListener;

    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        //not used
    }

    public interface OnShakeListener {
        public void onShake();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long now = System.currentTimeMillis();
        if ((now - mLastForce) > SHAKE_TIMEOUT) {
            mShakeCount = 0;
        }

        if ((now - mLastTime) > TIME_THRESHOLD) {
            long diff = now - mLastTime;
            float speed = Math.abs(event.values[0] + event.values[1] + event.values[2] - mLastX - mLastY - mLastZ) / diff * 10000;
            if (speed > FORCE_THRESHOLD) {
                if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
                    mLastShake = now;
                    mShakeCount = 0;
                    onShake = true;
                }
                mLastForce = now;
            } else {
                if(onShake && (now + 1000 >= mLastTime)){
                    onShake = false;
                    mListener.onShake();
                }
            }
            mLastTime = now;
            mLastX = event.values[0];
            mLastY = event.values[1];
            mLastZ = event.values[2];
        }
    }
}
