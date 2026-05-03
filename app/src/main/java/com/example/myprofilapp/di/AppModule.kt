package com.example.myprofilapp.di

import com.example.myprofilapp.NoteViewModel
import com.example.myprofilapp.ProfileViewModel
import com.example.myprofilapp.database.DatabaseModule
import com.example.myprofilapp.database.SettingsManager
import com.example.myprofilapp.network.GeminiService
import com.example.myprofilapp.repository.AIRepository
import com.example.myprofilapp.repository.AIRepositoryImpl
import com.example.myprofilapp.repository.NewsRepository
import com.example.myprofilapp.repository.NoteRepository
import com.example.myprofilapp.platform.AndroidDeviceInfo
import com.example.myprofilapp.platform.AndroidNetworkMonitor
import com.example.myprofilapp.platform.DeviceInfo
import com.example.myprofilapp.platform.NetworkMonitor
import com.example.myprofilapp.viewmodel.ChatViewModel
import com.example.myprofilapp.viewmodel.NewsViewModel
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Ktor Client
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30000
                connectTimeoutMillis = 30000
                socketTimeoutMillis = 30000
            }
        }
    }

    // Services
    single { GeminiService(get()) }

    // Repositories
    single<AIRepository> { AIRepositoryImpl(get()) }
    single { NoteRepository(get()) }
    single { NewsRepository(get()) }

    // Database & Settings
    single { DatabaseModule.getDatabase(androidContext()) }
    single { SettingsManager(androidContext()) }

    // Platform Features
    single<DeviceInfo> { AndroidDeviceInfo(androidContext()) }
    single<NetworkMonitor> { AndroidNetworkMonitor(androidContext()) }

    // ViewModels
    viewModel { NoteViewModel(androidApplication(), get(), get(), get(), get()) }
    viewModel { ProfileViewModel() }
    viewModel { ChatViewModel(get()) }
    viewModel { NewsViewModel(get()) }
}
