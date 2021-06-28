package com.thcommon.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.thcommon.R;
import com.thcommon.business.ThConfigHost;
import com.thcommon.business.ThUtils;

public abstract class BaseRateStarsDialogFragment extends DialogFragment {

    private boolean mIsAnimating = false;

    private RatingBar mRatingBar;

    public void onPrimaryButtonClick(int ratingStars) {
        if (ratingStars >= 5) {
            Context context = getContext();
            if (context != null) {
                String packageName = context.getPackageName();
                ThUtils.openMarketUrl(context,
                        "https://play.google.com/store/apps/details?id=" + packageName);
            }

        } else {
            Activity activity = getActivity();
            String targetEmailAddress = getTargetEmailAddress();
            String appInternalName = getAppInternalName();
            if (activity != null && !TextUtils.isEmpty(targetEmailAddress) && !TextUtils.isEmpty(appInternalName)) {
                ThUtils.openFeedback(activity, targetEmailAddress, appInternalName);
            }
        }

        ThConfigHost.setRateNeverShow(getContext(), true);
    }

    @SuppressWarnings("WeakerAccess")
    protected void onCancelled() {

    }

    protected abstract String getTargetEmailAddress();

    protected abstract String getAppInternalName();

    protected abstract String getAppName();

    @Override
    public void onStart() {
        super.onStart();
        playAnimation(1);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        onCancelled();
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.dialog_rate_stars, null);

        final ImageView expressionImageView = view.findViewById(R.id.iv_expression);
        final TextView primaryButton = view.findViewById(R.id.btn_primary);
        TextView titleTextView = view.findViewById(R.id.tv_title);
        final TextView messageTextView = view.findViewById(R.id.tv_content);
        mRatingBar = view.findViewById(R.id.ratingBar);

        titleTextView.setText(getString(R.string.th_dialog_title_rate_stars, getAppName()));

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ratingStars = Math.round(rating);
                switch (ratingStars) {
                    case 1:
                        expressionImageView.setImageResource(R.drawable.th_img_vector_star1);
                        if (fromUser) {
                            messageTextView.setText(R.string.th_dialog_msg_rate_stars);
                        }
                        break;
                    case 2:
                        expressionImageView.setImageResource(R.drawable.th_img_vector_star2);
                        if (fromUser) {
                            messageTextView.setText(R.string.th_dialog_msg_rate_stars);
                        }
                        break;
                    case 3:
                        expressionImageView.setImageResource(R.drawable.th_img_vector_star3);
                        if (fromUser) {
                            messageTextView.setText(R.string.th_dialog_msg_rate_stars);
                        }
                        break;
                    case 4:
                        expressionImageView.setImageResource(R.drawable.th_img_vector_star4);
                        if (fromUser) {
                            messageTextView.setText(R.string.th_dialog_msg_rate_stars);
                        }
                        break;
                    case 5:
                        expressionImageView.setImageResource(R.drawable.th_img_vector_star5);
                        if (fromUser) {
                            messageTextView.setText(R.string.th_dialog_msg_rate_stars_5_stars);
                        }
                        break;
                    default:
                        expressionImageView.setImageResource(R.drawable.th_img_vector_star5);
                        messageTextView.setText(R.string.th_dialog_msg_rate_stars);
                        break;
                }
            }
        });

        primaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity == null) {
                    return;
                }

                if (mIsAnimating) {
                    return;
                }

                int ratingStar = Math.round(mRatingBar.getRating());
                if (ratingStar <= 0) {
                    playAnimation(0);
                    Toast.makeText(activity, R.string.th_toast_rate_stars_not_rated, Toast.LENGTH_LONG).show();
                    return;
                }

                onPrimaryButtonClick(ratingStar);
                dismiss();
            }
        });

        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelled();
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    dismiss();
                }
            }
        });

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .create();
    }

    private void playAnimation(int repeatCount) {
        ValueAnimator animator = ValueAnimator.ofInt(1, 5);
        animator.setDuration(DateUtils.SECOND_IN_MILLIS);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRatingBar.setRating((Integer) animation.getAnimatedValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRatingBar.setRating(0);
                        mIsAnimating = false;
                    }
                }, DateUtils.SECOND_IN_MILLIS);
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(repeatCount);
        animator.start();
    }
}
