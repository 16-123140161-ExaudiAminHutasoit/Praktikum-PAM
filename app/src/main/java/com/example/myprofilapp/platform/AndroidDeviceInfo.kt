package com.example.myprofilapp.platform

import android.os.Build

class AndroidDeviceInfo : DeviceInfo {
    override fun getModel(): String = Build.MODEL
    override fun getOsVersion(): String = Build.VERSION.RELEASE
    override fun getManufacturer(): String = Build.MANUFACTURER
}
