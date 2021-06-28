package com.simplemobiletools.gallery.pro.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simplemobiletools.gallery.pro.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LicenseActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        RecyclerView recyclerView = findViewById(R.id.rv_license);
        LicenseAdapter licenseAdapter = new LicenseAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(licenseAdapter);
    }

    static class License {
        private final int titleId;
        private final int textId;
        private final int urlId;

        public License(int titleId, int textId, int urlId) {
            this.titleId = titleId;
            this.textId = textId;
            this.urlId = urlId;
        }

        public int getTitleId() {
            return titleId;
        }

        public int getTextId() {
            return textId;
        }

        public int getUrlId() {
            return urlId;
        }
    }

    class LicenseAdapter extends RecyclerView.Adapter<LicenseAdapter.LicenseViewHolder> {

        public static final int ITEM_VIEW_TYPE_HEADER = 0;
        public static final int ITEM_VIEW_TYPE_COMMON = 1;

        private List<License> mLicenseList;

        public LicenseAdapter() {
            mLicenseList = new ArrayList<>();
            initLicense();
        }

        @NonNull
        @Override
        public LicenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rootView;
            if (ITEM_VIEW_TYPE_HEADER == viewType) {
                rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_license_header, parent, false);
                return new LicenseViewHolder(rootView, true);

            } else {
                rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_license_layout, parent, false);
                return new LicenseViewHolder(rootView, false);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull LicenseViewHolder holder, int position) {
            if (holder.isHeader()) {
                Spanned headerText = Html.fromHtml(getString(R.string.third_party_license_title).replace("\n", "<br>"));
                SpannableStringBuilder style = new SpannableStringBuilder();
                style.append(headerText);
                style.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(getString(R.string.dvgallery_url)));
                        startActivity(intent);
                    }
                }, 43, 82, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.headerTv.setText(style);
                style.setSpan(new ForegroundColorSpan(Color.parseColor("#3888CB")),
                        43, 82, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.headerTv.setMovementMethod(LinkMovementMethod.getInstance());
                holder.headerTv.setText(style);

            } else {
                License license = mLicenseList.get(position - 1);
                holder.titleTv.setText(getString(license.getTitleId()));
                holder.titleTv.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(getString(license.getUrlId())));
                    startActivity(intent);
                });
                holder.textTv.setText(getString(license.getTextId()));
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position < 1) {
                // Header view.
                return ITEM_VIEW_TYPE_HEADER;

            } else {
                return ITEM_VIEW_TYPE_COMMON;
            }
        }

        @Override
        public int getItemCount() {
            if (mLicenseList != null) {
                return mLicenseList.size() + 1;

            } else {
                return 0;
            }
        }

        class LicenseViewHolder extends RecyclerView.ViewHolder {
            boolean isHeader;
            TextView headerTv;
            TextView titleTv;
            TextView textTv;

            public LicenseViewHolder(@NonNull View itemView, boolean isHeader) {
                super(itemView);
                this.isHeader = isHeader;
                if (isHeader) {
                    headerTv = itemView.findViewById(R.id.tv_third_party_license_title);

                } else {
                    titleTv = itemView.findViewById(R.id.tv_license_title);
                    titleTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                    textTv = itemView.findViewById(R.id.tv_license_text);
                }
            }

            public boolean isHeader() {
                return isHeader;
            }
        }

        private void initLicense() {
            License[] licenseArray = new License[] {
                    new License(R.string.simplegallery_title, R.string.simplegallery_text, R.string.simplegallery_url),
                    new License(R.string.kotlin_title, R.string.kotlin_text, R.string.kotlin_url),
                    new License(R.string.subsampling_title, R.string.subsampling_text, R.string.subsampling_url),
                    new License(R.string.glide_title, R.string.glide_text, R.string.glide_url),
                    new License(R.string.cropper_title, R.string.cropper_text, R.string.cropper_url),
                    new License(R.string.rtl_viewpager_title, R.string.rtl_viewpager_text, R.string.rtl_viewpager_url),
                    new License(R.string.joda_title, R.string.joda_text, R.string.joda_url),
                    new License(R.string.stetho_title, R.string.stetho_text, R.string.stetho_url),
                    new License(R.string.otto_title, R.string.otto_text, R.string.otto_url),
                    new License(R.string.photoview_title, R.string.photoview_text, R.string.photoview_url),
                    new License(R.string.picasso_title, R.string.picasso_text, R.string.picasso_url),
                    new License(R.string.pattern_title, R.string.pattern_text, R.string.pattern_url),
                    new License(R.string.reprint_title, R.string.reprint_text, R.string.reprint_url),
                    new License(R.string.gif_drawable_title, R.string.gif_drawable_text, R.string.gif_drawable_url),
                    new License(R.string.autofittextview_title, R.string.autofittextview_text, R.string.autofittextview_url),
                    new License(R.string.robolectric_title, R.string.robolectric_text, R.string.robolectric_url),
                    new License(R.string.espresso_title, R.string.espresso_text, R.string.espresso_url),
                    new License(R.string.gson_title, R.string.gson_text, R.string.gson_url),
                    new License(R.string.leak_canary_title, R.string.leakcanary_text, R.string.leakcanary_url),
                    new License(R.string.number_picker_title, R.string.number_picker_text, R.string.number_picker_url),
                    new License(R.string.exoplayer_title, R.string.exoplayer_text, R.string.exoplayer_url),
                    new License(R.string.panorama_view_title, R.string.panorama_view_text, R.string.panorama_view_url),
                    new License(R.string.sanselan_title, R.string.sanselan_text, R.string.sanselan_url),
                    new License(R.string.filters_title, R.string.filters_text, R.string.filters_url),
                    new License(R.string.gesture_views_title, R.string.gesture_views_text, R.string.gesture_views_url),
                    new License(R.string.indicator_fast_scroll_title, R.string.indicator_fast_scroll_text, R.string.indicator_fast_scroll_url),
                    new License(R.string.event_bus_title, R.string.event_bus_text, R.string.event_bus_url),
                    new License(R.string.audio_record_view_title, R.string.audio_record_view_text, R.string.audio_record_view_url),
                    new License(R.string.sms_mms_title, R.string.sms_mms_text, R.string.sms_mms_url),
                    new License(R.string.apng_title, R.string.apng_text, R.string.apng_url)};
            mLicenseList = Arrays.asList(licenseArray);
        }

    }
}