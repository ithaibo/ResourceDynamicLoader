package com.andy.resourcedynamicloader;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.resource_loader.Loader;

public class MainActivity extends AppCompatActivity {
    private static final String APK_NAME = "res_lib-debug.apk";
    private static final String PACKAGE_NAME = "com.andy.res_lib";
    private static final String NAME_RESOURCE = "label";
    private static final String TYPE_RESOURCE = "string";

    private String mPath;
    private Resources mResourcesLoaded;
    private ImageView mImageView;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + APK_NAME;

        mTvResult = findViewById(R.id.tv_result);

        Loader.getInstance().init(MainActivity.this, mPath);

        mImageView = findViewById(R.id.img);

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mResourcesLoaded) {
                    return;
                }
//                loadTextLabel();
                Log.i("Main", "load image from sdcard");
                loadImage(PACKAGE_NAME);

            }
        });

        findViewById(R.id.btn_load_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Main", "load image local");
                loadImage(getPackageName());
            }
        });
    }


    private void loadImage(String packageName) {
        long start = System.currentTimeMillis();
        Drawable drawable = Loader.getInstance().getDrawable("img_splash", packageName);
        mImageView.setImageDrawable(drawable);
        Log.i("Main", "load cost: " + (System.currentTimeMillis() - start));
    }

    private void loadTextLabel() {
        try {
            String stringLoaded = Loader.getInstance().getString(NAME_RESOURCE, PACKAGE_NAME);
            if (null != stringLoaded) {
                mTvResult.setText(stringLoaded);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
