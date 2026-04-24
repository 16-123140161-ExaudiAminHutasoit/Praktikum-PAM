package com.example.myprofilapp.platform

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build

class AndroidDeviceInfo(private val context: Context) : DeviceInfo {
    override fun getModel(): String = Build.MODEL
    override fun getOsVersion(): String = Build.VERSION.RELEASE
    override fun getManufacturer(): String = Build.MANUFACTURER

    override fun getBatteryLevel(): Int {
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        return if (level != -1 && scale != -1) {
            (level * 100 / scale.toFloat()).toInt()
        } else {
            0
        }
    }
}
