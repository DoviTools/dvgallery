package com.thcommon.business;

import android.content.Context;

public class ThConfigHost {
    private static ConfigProxy gConfigProxy = new ConfigProxy("th_config");

    private static final String LAUNCH_COUNT = "launch_count";
    private static final String RATE_NEVER_SHOW = "rate_never_show";

    public static int getLaunchCount(Context context) {
        return gConfigProxy.getValue(context, LAUNCH_COUNT, 0);
    }

    public static void setLaunchCount(Context context, int launchCount) {
        gConfigProxy.setValue(context, LAUNCH_COUNT, launchCount);
    }

    public static boolean getRateNeverShow(Context context) {
        return gConfigProxy.getValue(context, RATE_NEVER_SHOW, false);
    }

    public static void setRateNeverShow(Context context, boolean value) {
        gConfigProxy.setValue(context, RATE_NEVER_SHOW, value);
    }
}
