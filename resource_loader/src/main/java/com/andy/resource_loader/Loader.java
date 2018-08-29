package com.andy.resource_loader;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import dalvik.system.DexClassLoader;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author wuhaibo
 * create date: 2018/8/29.
 */
public class Loader {

    public static Resources loadResourceFromDir(String dir, Context context) {
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

    public static class ResourceHelper {
        private static ResourceHelper mResource = null;
        private static String mPackagename = null;
        private static Class<?> mLayout = null;
        private static Class<?> mDrawable = null;
        private static Class<?> mID = null;
        private static Class<?> mString = null;
        private static Class<?> mAttr = null;

        public static ResourceHelper getInstance(String packagename) {
            if (mResource == null) {
                mPackagename = packagename;
                mResource = new ResourceHelper(mPackagename);
            }
            return mResource;
        }

        public ResourceHelper(String packageName) {
            try {
                mString = Class.forName(packageName + ".R$string");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        private int getResourceId(Class<?> classType, String resourceName) {
            if (classType != null) {
                try {
                    Field field = classType.getField(resourceName);
                    return field.getInt(resourceName);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("ResourceHelper",
                            "Error getting resource. Make sure you have copied all resources "
                                    + "(res/) from SDK to your project.");

                }
            }
            return -1;
        }

        public int getStringId(String resourceName) {
            return getResourceId(mString, resourceName);
        }

    }

}
