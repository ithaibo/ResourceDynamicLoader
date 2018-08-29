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

        mResourcesLoaded = Loader.loadResourceFromDir(mPath, MainActivity.this);

        mImageView = findViewById(R.id.img);

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null == mResourcesLoaded) {
                    return;
                }

//                loadTextLabel();
                Log.i("Main", "load image from sdcard");
                loadImage(mResourcesLoaded, PACKAGE_NAME);

            }
        });

        findViewById(R.id.btn_load_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Main", "load image local");
                loadImage(getResources(), getPackageName());
            }
        });
    }


    private void loadImage(Resources resources, String packageName) {
        long start = System.currentTimeMillis();
        int ivId = resources.getIdentifier("img_splash", "drawable", packageName);
        if (ivId <=0) {
            return;
        }
        Drawable drawable = resources.getDrawable(ivId);
        mImageView.setImageDrawable(drawable);
        Log.i("Main", "load cost: " + (System.currentTimeMillis() - start));
    }

    private void loadTextLabel() {
        int idFromResource = mResourcesLoaded.getIdentifier(NAME_RESOURCE, TYPE_RESOURCE,
                PACKAGE_NAME);
        Log.i("Main", "idFromResource = " + idFromResource);

        try {
            String stringLoaded = mResourcesLoaded.getString(idFromResource);
            if (null != stringLoaded) {
                mTvResult.setText(stringLoaded);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
