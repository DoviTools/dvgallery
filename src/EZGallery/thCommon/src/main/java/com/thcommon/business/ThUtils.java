package com.thcommon.business;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.example.thcommon.R;

import static android.content.Intent.ACTION_SENDTO;

public class ThUtils {


    private static final String PACKAGE_NAME_PLAY_STORE = "com.android.vending";

    public static void openFeedback(Activity activity, String email, String appInternalName) {
        String chooseTitle = activity.getString(R.string.feedback);
        VersionInfo versionInfo = getVersionCode(activity, activity.getPackageName());
        String versionName = versionInfo == null ? "null" : versionInfo.versionName;
        String subject = appInternalName + "_" + versionName + "[" + Build.VERSION.SDK_INT + "]";
        sendEmail(activity, email, chooseTitle, subject, null);
    }

    public static void sendEmail(Activity activity, String email, String chooseTitle, String subject, String text) {
        final Intent result = new Intent(ACTION_SENDTO);
        result.setData(Uri.parse("mailto:"));
        result.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        result.putExtra(Intent.EXTRA_SUBJECT, subject);
        result.putExtra(Intent.EXTRA_TEXT, text);

        activity.startActivity(Intent.createChooser(result, chooseTitle));
    }

    public static VersionInfo getVersionCode(Context context, String pkgName) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
            VersionInfo versionInfo = new VersionInfo();
            versionInfo.versionCode = pInfo.versionCode;
            versionInfo.versionName = pInfo.versionName;
            return versionInfo;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class VersionInfo {
        public int versionCode;
        public String versionName;
    }

    public static boolean openMarketUrl(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        String specificMarketPkgName = null;
        if (isAppInstalled(context, PACKAGE_NAME_PLAY_STORE)) {
            specificMarketPkgName = PACKAGE_NAME_PLAY_STORE;
        }

        if (specificMarketPkgName != null) {
            intent.setPackage(specificMarketPkgName);
            try {
                context.startActivity(intent);
                return true;

            } catch (ActivityNotFoundException e) {
                Log.e("Utils", "Exception when open url with PlayStore", e);

                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (!(context instanceof Activity)) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(intent);
                    return true;

                } catch (ActivityNotFoundException e1) {
                    Log.e("Utils", "Exception when open url", e1);
                }
            }

        } else {
            try {
                context.startActivity(intent);
                return true;

            } catch (ActivityNotFoundException e) {
//                gDebug.e("Exception when open url", e);
            }
        }
        return false;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        if (pm == null) {
            return false;
        }
        boolean appInstalled;
        try {
            pm.getPackageInfo(packageName, 0);
            appInstalled = true;

        } catch (PackageManager.NameNotFoundException e) {
            appInstalled = false;
        }
        return appInstalled;
    }
}
