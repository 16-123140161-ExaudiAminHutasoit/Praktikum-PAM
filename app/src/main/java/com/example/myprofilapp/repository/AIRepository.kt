package com.example.myprofilapp.repository

import com.example.myprofilapp.network.GeminiService

interface AIRepository {
    suspend fun chat(message: String): Result<String>
    fun clearChat()
}

class AIRepositoryImpl(private val geminiService: GeminiService) : AIRepository {
    override suspend fun chat(message: String): Result<String> {
        return geminiService.generateContent(message)
    }

    override fun clearChat() {
        geminiService.clearHistory()
    }
}
