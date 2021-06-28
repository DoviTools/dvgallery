/*
 * Copyright (C) 2014 Hari Krishna Dulipudi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplemobiletools.gallery.pro.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;

import com.simplemobiletools.commons.helpers.BaseConfig;
import com.simplemobiletools.gallery.pro.BuildConfig;
import com.simplemobiletools.gallery.pro.R;

import java.util.Objects;

import static android.content.Intent.ACTION_SENDTO;


public class AboutActivity extends SimpleActivity implements View.OnClickListener {

    private BaseConfig mBaseConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mBaseConfig = new BaseConfig(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
        initControls();
    }

    private void initControls() {
        findViewById(R.id.ll_head).setBackgroundColor(mBaseConfig.getPrimaryColor());
        TextView versionName = findViewById(R.id.tv_version_name);
        versionName.setText(BuildConfig.VERSION_NAME);

        TextView action_rate = findViewById(R.id.action_rate);
        TextView action_share = findViewById(R.id.action_share);
        TextView action_feedback = findViewById(R.id.action_feedback);
        TextView action_privacy_policy = findViewById(R.id.action_privacy_policy);
        TextView action_open_source = findViewById(R.id.action_open_source);

        action_rate.setText(getString(R.string.rate_us_on_play_store, getString(R.string.app_name)));
        action_rate.setTextColor(mBaseConfig.getTextColor());
        action_rate.setOnClickListener(this);

        action_share.setOnClickListener(this);
        action_share.setTextColor(mBaseConfig.getTextColor());

        action_feedback.setOnClickListener(this);
        action_feedback.setTextColor(mBaseConfig.getTextColor());

        action_privacy_policy.setOnClickListener(this);
        action_privacy_policy.setTextColor(mBaseConfig.getTextColor());

        action_open_source.setOnClickListener(this);
        action_open_source.setTextColor(mBaseConfig.getTextColor());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_feedback:
                openFeedback();
                break;

            case R.id.action_rate:
                openPlayStore();
                break;

            case R.id.action_share:
                String shareText = getString(R.string.share_content, getAppPlayUrl());
                ShareCompat.IntentBuilder
                        .from(this)
                        .setText(shareText)
                        .setType("text/plain")
                        .setChooserTitle(getString(R.string.share))
                        .startChooser();
                break;
            case R.id.action_privacy_policy:
                openPrivacyPolicy();
                break;
            case R.id.action_open_source:
                openOpenSource();
                break;
        }
    }

    private void openOpenSource() {
        Intent intent = new Intent(this, LicenseActivity.class);
        startActivity(intent);
    }

    private void openPrivacyPolicy() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mobcoolgames.github.io/ezgallery_privacy_policy.html"));
        startActivity(intent);
    }

    public void openFeedback() {
        sendEmail();
    }

    public void sendEmail() {
        final Intent result = new Intent(ACTION_SENDTO);
        result.setData(Uri.parse("mailto:"));
        result.putExtra(Intent.EXTRA_EMAIL, new String[]{"mobcoolgames@gmail.com"});
        result.putExtra(Intent.EXTRA_SUBJECT, "EZ Gallery Feedback [v" + BuildConfig.VERSION_NAME + "]");

        startActivity(Intent.createChooser(result, "Send Feedback"));
    }

    public void openPlayStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getAppPlayUrl()));
        startActivity(intent);
    }

    private String getAppPlayUrl() {
        return "https://play.google.com/store/apps/details?id=" + getPackageName();
    }
}