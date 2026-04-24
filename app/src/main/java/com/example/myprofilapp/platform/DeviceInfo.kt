package com.example.myprofilapp.platform

interface DeviceInfo {
    fun getModel(): String
    fun getOsVersion(): String
    fun getManufacturer(): String
}
