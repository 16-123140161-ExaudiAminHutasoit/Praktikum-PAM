package com.example.myprofilapp.di

import com.example.myprofilapp.NoteViewModel
import com.example.myprofilapp.ProfileViewModel
import com.example.myprofilapp.database.DatabaseModule
import com.example.myprofilapp.database.SettingsManager
import com.example.myprofilapp.repository.NoteRepository
import com.example.myprofilapp.platform.AndroidDeviceInfo
import com.example.myprofilapp.platform.AndroidNetworkMonitor
import com.example.myprofilapp.platform.DeviceInfo
import com.example.myprofilapp.platform.NetworkMonitor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Database & Settings
    single { DatabaseModule.getDatabase(androidContext()) }
    single { SettingsManager(androidContext()) }
    single { NoteRepository(get()) }

    // Platform Features
    single<DeviceInfo> { AndroidDeviceInfo(androidContext()) }
    single<NetworkMonitor> { AndroidNetworkMonitor(androidContext()) }

    // ViewModels
    viewModel { NoteViewModel(androidApplication(), get(), get(), get(), get()) }
    viewModel { ProfileViewModel() }
}
