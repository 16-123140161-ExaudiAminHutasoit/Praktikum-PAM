package com.example.myprofilapp.network

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

val client = HttpClient(Android) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        })
    }
    
    // Menambahkan timeout agar jika tidak ada internet, aplikasi cepat mendeteksi error
    install(HttpTimeout) {
        requestTimeoutMillis = 5000 // 5 detik
        connectTimeoutMillis = 5000
        socketTimeoutMillis = 5000
    }
}
