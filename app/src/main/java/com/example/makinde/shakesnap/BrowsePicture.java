package com.example.makinde.shakesnap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class BrowsePicture extends AppCompatActivity {

    private int currentPhoto;

    private ImageView image2;
    private ImageView image;
   // private TextView text;
    private Button a;
    private Button b;
    private Button c;
    private File[] photos;
    private File album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);

        currentPhoto = 0;
        image = findViewById(R.id.photo);
        image2 = findViewById(R.id.photo2);
        a = findViewById(R.id.back);
        b = findViewById(R.id.buttonDelete);
        c = findViewById(R.id.buttonNext);
        //text = findViewById(R.id.text);

        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        album = new File(sdDir, "A3");
        photos = album.listFiles();
        if(photos.length == 0){
            image.setVisibility(View.GONE);
            image2.setVisibility(View.VISIBLE);
            a.setVisibility(View.GONE);
            b.setVisibility(View.GONE);
            c.setVisibility(View.GONE);
          //  image.setBackgroundResource(R.drawable.shakeimage);
            //text.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.VISIBLE);
            image2.setVisibility(View.GONE);
            Bitmap bitmap = BitmapFactory.decodeFile(photos[currentPhoto].getPath());
            image.setImageBitmap(bitmap);
            image.setRotation(90);
        }
    }

    public void delete(View view){
        if(photos.length == 0){
            return;
        }
        photos[currentPhoto].delete();
        if(currentPhoto != 0){
            currentPhoto--;
        }
        photos = album.listFiles();
        if(photos.length == 0){
            image.setVisibility(View.GONE);
            image2.setVisibility(View.VISIBLE);
            a.setVisibility(View.GONE);
            b.setVisibility(View.GONE);
            c.setVisibility(View.GONE);
          //  image.setBackgroundResource(R.drawable.shakeimage);
        } else {
            image.setVisibility(View.VISIBLE);
            image2.setVisibility(View.GONE);
            Bitmap bitmap = BitmapFactory.decodeFile(photos[currentPhoto].getPath());
            image.setImageBitmap(bitmap);
            image.setRotation(90);
        }

    }

    public void next(View view){
        if(photos.length != 0 && currentPhoto != photos.length - 1) {
            currentPhoto++;
            Bitmap bitmap = BitmapFactory.decodeFile(photos[currentPhoto].getPath());
            image.setImageBitmap(bitmap);
            image.setRotation(90);
        }
    }

    public void previous(View view){
        if(photos.length != 0 && currentPhoto != 0) {
            currentPhoto--;
            Bitmap bitmap = BitmapFactory.decodeFile(photos[currentPhoto].getPath());
            image.setImageBitmap(bitmap);
            image.setRotation(90);
        }
    }

    public void startCamera(View view) {
        Intent intent = new Intent(this, UseCamera.class);
        startActivity(intent);
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
}
