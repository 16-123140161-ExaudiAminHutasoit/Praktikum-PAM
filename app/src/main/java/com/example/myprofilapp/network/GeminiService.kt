package com.example.myprofilapp.network

import com.example.myprofilapp.model.GeminiRequest
import com.example.myprofilapp.model.GeminiResponse
import com.example.myprofilapp.model.Content
import com.example.myprofilapp.model.Part
import com.example.myprofilapp.model.GenerationConfig
import com.example.myprofilapp.platform.ApiConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class GeminiService(private val client: HttpClient) {
    private val modelName = "gemini-2.5 -flash"
    private val conversationHistory = mutableListOf<Content>()

    suspend fun generateContent(prompt: String): Result<String> = runCatching {
        val apiKey = ApiConfig.geminiApiKey
        if (apiKey.isBlank()) {
            return@runCatching "Error: API Key masih kosong. Silakan isi di local.properties dan REBUILD PROJECT."
        }


        val userContent = Content(parts = listOf(Part(text = prompt)), role = "user")
        
        val request = GeminiRequest(
            contents = conversationHistory.toList() + userContent,
            generationConfig = GenerationConfig(
                temperature = 0.7,
                maxOutputTokens = 1000
            )
        )


        val response = client.post("https://generativelanguage.googleapis.com/v1beta/models/$modelName:generateContent") {
            contentType(ContentType.Application.Json)
            parameter("key", apiKey)
            setBody(request)
        }

        if (response.status.isSuccess()) {
            val geminiResponse: GeminiResponse = response.body()
            val assistantContent = geminiResponse.candidates.firstOrNull()?.content
            
            if (assistantContent != null) {
                conversationHistory.add(userContent)
                conversationHistory.add(assistantContent)
                assistantContent.parts.firstOrNull()?.text ?: "Jawaban kosong dari AI."
            } else {
                "AI tidak memberikan kandidat jawaban."
            }
        } else {
            val errorBody = response.bodyAsText()
            "Gagal (Status ${response.status.value}). Detail: $errorBody"
        }
    }

    fun clearHistory() {
        conversationHistory.clear()
    }
}
