package com.simplemobiletools.gallery.pro

import android.os.StrictMode
import androidx.multidex.MultiDexApplication
import com.github.ajalt.reprint.core.Reprint
import com.simplemobiletools.commons.extensions.checkUseEnglish

class MainApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        checkUseEnglish()
        Reprint.initialize(this)

        // Remove the restriction for FileUriExposure
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
    }
}
