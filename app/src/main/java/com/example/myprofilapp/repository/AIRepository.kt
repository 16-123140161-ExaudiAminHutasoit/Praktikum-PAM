package com.example.myprofilapp.repository

import com.example.myprofilapp.network.GeminiService

interface AIRepository {
    suspend fun chat(message: String): Result<String>
    suspend fun analyzeNotes(notesJson: String): Result<String>
    fun clearChat()
}

class AIRepositoryImpl(private val geminiService: GeminiService) : AIRepository {
    override suspend fun chat(message: String): Result<String> {
        return geminiService.generateContent(message)
    }

    override suspend fun analyzeNotes(notesJson: String): Result<String> {
        val prompt = """
            Berikut adalah daftar catatan saya dalam format JSON:
            $notesJson
            
            Tolong berikan analisis singkat mengenai catatan-catatan tersebut:
            1. Kelompokkan ke dalam kategori (misal: Tugas, Pribadi, Ide).
            2. Berikan 3 saran produktivitas berdasarkan isi catatan tersebut.
            3. Berikan ringkasan dalam 2 kalimat.
            
            Jawab dalam Bahasa Indonesia yang ramah dan profesional.
        """.trimIndent()
        return geminiService.generateContent(prompt)
    }

    override fun clearChat() {
        geminiService.clearHistory()
    }
}
