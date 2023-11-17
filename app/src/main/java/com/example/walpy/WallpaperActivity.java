package com.example.walpy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shashank.sony.fancytoastlib.FancyToast;
//import com.soepic.sefancytoast.FancyToast;
//import com.shashank.sony.fancytoastlib.FancyToast;
//import com.soepic.sefancytoast.FancyToast;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView IvWallpaper;
    Button setWallpaperButton;
    String imageUrl;
    WallpaperManager wallpaperManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        IvWallpaper=findViewById(R.id.IvWallpaper);
        setWallpaperButton=findViewById(R.id.setWallpaperbutton);
        imageUrl=getIntent().getStringExtra("imgurl");
        Log.d("image url","is  "+imageUrl);
        Glide.with(this).load(imageUrl).into(IvWallpaper);
        wallpaperManager=wallpaperManager.getInstance(getApplicationContext());
        setWallpaperButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.setWallpaperbutton:
                Glide.with(this).asBitmap().load(imageUrl).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(WallpaperActivity.this, "Failed to load Image", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(resource);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(WallpaperActivity.this, "Failed to set Wallpaper...", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                }).submit();
                FancyToast.makeText(this,"Wallpaper set to HomeScreen",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
//                Toast.makeText(WallpaperActivity.this, "Wallpaper set to HomeScreen", Toast.LENGTH_SHORT).show();
                break;


        }
    }
}