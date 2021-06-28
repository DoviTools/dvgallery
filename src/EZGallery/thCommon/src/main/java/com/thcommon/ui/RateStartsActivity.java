package com.thcommon.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.fragment.app.DialogFragment;


public class RateStartsActivity extends DialogFragmentActivity {

    public static void show(Activity activity, String appName, String appInternalName, String targetEmail) {
        Intent intent = new Intent(activity, RateStartsActivity.class);
        intent.putExtra(APP_NAME, appName);
        intent.putExtra(APP_INTERNAL_NAME, appInternalName);
        intent.putExtra(TARGET_EMAIL_ADDRESS, targetEmail);
        activity.startActivity(intent);
    }

    public static final String APP_NAME = "app_name";
    public static final String APP_INTERNAL_NAME = "app_internal_name";
    public static final String TARGET_EMAIL_ADDRESS = "target_email_address";

    @Override
    protected void showDialogFragment() {
        try {
            DialogFragment dialogFragment = ActivityRateStarsDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "ActivityRateStarsDialogFragment");

        } catch (IllegalStateException e) {
            // ignore
            e.printStackTrace();
            finish();
        }
    }

    public static class ActivityRateStarsDialogFragment extends BaseRateStarsDialogFragment {

        static ActivityRateStarsDialogFragment newInstance() {
            ActivityRateStarsDialogFragment dialogFragment = new ActivityRateStarsDialogFragment();
            dialogFragment.setCancelable(false);
            return dialogFragment;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            Activity activity = getActivity();
            if (activity != null) {
                activity.finish();
            }
            super.onDismiss(dialog);
        }

        @Override
        public void onPrimaryButtonClick(final int ratingStars) {
            // Fix the issue that when an activity cover the dialog when call dismiss, the getActivity method
            // in onDismiss ont work.
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (getActivity() == null) {
                        return;
                    }
                    ActivityRateStarsDialogFragment.super.onPrimaryButtonClick(ratingStars);
                }
            });
        }

        @Override
        protected String getTargetEmailAddress() {
            RateStartsActivity activity = (RateStartsActivity)getActivity();
            if (activity == null) {
                return null;
            }
            return activity.getIntent().getStringExtra(TARGET_EMAIL_ADDRESS);
        }

        @Override
        protected String getAppInternalName() {
            RateStartsActivity activity = (RateStartsActivity)getActivity();
            if (activity == null) {
                return null;
            }
            return activity.getIntent().getStringExtra(APP_INTERNAL_NAME);
        }

        @Override
        protected String getAppName() {
            RateStartsActivity activity = (RateStartsActivity)getActivity();
            if (activity == null) {
                return null;
            }
            return activity.getIntent().getStringExtra(APP_NAME);
        }
    }
}
