package com.example.makinde.shakesnap;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureHandler implements Camera.PictureCallback {
    private final Context context;
    public File pictureFile;

    public PictureHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {


        File pictureFileDir = getDir();

        if (!pictureFileDir.exists()) {
            if (!pictureFileDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                Toast.makeText(context, "Can't create directory to save image.",
                        Toast.LENGTH_LONG).show();
                return;
            }
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_" + date + ".jpg";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;

        pictureFile = new File(filename);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            Toast toast;

               toast=  Toast.makeText(context, "New Image saved:" + photoFile+"\n\nClick the Above to Take another Picture",
                        Toast.LENGTH_LONG);
               toast.setGravity(Gravity.CENTER,0,0);
               toast.show();




            //The picture is in the gallery but we can't see it directly, the gallery need to be refresh
        } catch (FileNotFoundException e) {
            Log.d("MyCameraApp", "File not found: " + e.getMessage());
            Toast.makeText(context, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.d("MyCameraApp", "Error accessing file: " + e.getMessage());
            Toast.makeText(context, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "A3");
    }


}

