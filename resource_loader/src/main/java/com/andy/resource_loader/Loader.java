package com.andy.resource_loader;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.lang.reflect.Method;

/**
 * @author wuhaibo
 * create date: 2018/8/29.
 */
public class Loader {
    public static final String TYPE_STRING = "string";
    public static final String TYPE_DRAWABLE = "drawable";
    public static final String TYPE_COLOR = "color";
    public static final String TYPE_ID = "id";
    public static final String TYPE_MIPMAP = "mipmap";
    public static final String TYPE_STYLE = "style";
    public static final String TYPE_LAYOUT = "layout";
    private Resources mResources;

    private static final class Holder {
        private static final Loader instance = new Loader();
    }

    public static Loader getInstance() {
        return Holder.instance;
    }

    public void init(Context context, String apkPath) {
        mResources = initResource(apkPath, context);
    }

    public static Resources initResource(String dir, Context context) {
        AssetManager assetManager = null;
        try {
            assetManager = AssetManager.class.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null == assetManager) {
            return null;
        }

        try {
            Method addAssetpath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetpath.invoke(assetManager, dir);

            return new Resources(assetManager, context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return null;
        }
    }

    public String getString(@NonNull String name, @NonNull String packageName) {
        if (null == mResources) {
            return null;
        }

        int srcId = mResources.getIdentifier(name, TYPE_STRING, packageName);
        if (0 >= srcId) {
            return null;
        }

        String text;
        try {
            text = mResources.getString(srcId);
        } catch (Exception e) {
            text = null;
            e.printStackTrace();
        }

        return text;
    }

    public Drawable getDrawable(@NonNull String name, @NonNull String packageName) {
        if (null == mResources) {
            return null;
        }

        int srcId = mResources.getIdentifier(name, TYPE_DRAWABLE, packageName);
        if (0 >= srcId) {
            return null;
        }

        Drawable drawable;
        try {
            drawable = mResources.getDrawable(srcId);
        } catch (Exception e) {
            drawable = null;
            e.printStackTrace();
        }

        return drawable;
    }
}
