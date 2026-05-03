# Tugas Praktikum 9 - Pengembangan Aplikasi Mobile

**Nama : Exaudi Amin Hutasoit**

**NIM : 123140161**

**Kelas : PAM RA**

## Deskripsi Tugas
Integrasi AI (Google Gemini) ke dalam aplikasi Android "MyProfilApp" untuk fitur **Smart Chatbot/Assistant**. Fitur ini membantu pengguna dalam mengelola catatan, memberikan ringkasan, dan menjawab pertanyaan terkait produktivitas.

---

## Kriteria Penilaian & Implementasi

### 1. AI Integration (30%)
*   **Service Layer**: Menggunakan `GeminiService.kt` untuk menangani komunikasi API secara terpisah.
*   **Networking**: Implementasi menggunakan **Ktor Client** dengan konfigurasi timeout dan `ContentNegotiation` JSON.
*   **Dependency Injection**: Seluruh komponen didefinisikan dalam `AppModule.kt` menggunakan **Koin**.

### 2. Prompt Engineering (25%)
*   **System Prompt**: Menggunakan instruksi sistem yang terdefinisi di `SystemPrompts.kt` untuk mengatur persona AI sebagai asisten aplikasi catatan yang profesional.
*   **Model**: Menggunakan model terbaru `gemini-1.5-flash` untuk respon yang cepat dan akurat.

### 3. Error Handling (20%)
*   **Graceful Handling**: Menangkap error jaringan, API limit (429), dan invalid key (403/404) menggunakan blok `runCatching`.
*   **Retry Logic**: UI menyediakan tombol **"Retry"** jika terjadi kesalahan pengiriman pesan agar pengguna tidak perlu mengetik ulang.

### 4. UI/UX (15%)
*   **Loading State**: Implementasi `TypingIndicator` dengan animasi bergerak saat AI sedang memproses.
*   **Responsive Chat**: Menggunakan chat bubble dengan perbedaan warna kontras antara user (Primary) dan AI (SurfaceVariant).
*   **Clear Chat**: Fitur untuk menghapus riwayat percakapan.

### 5. Code Quality (10%)
*   **Architecture**: Mengikuti pola MVVM (Model-View-ViewModel) dengan pembagian folder `model`, `viewmodel`, `repository`, dan `network`.
*   **Security**: API Key disimpan aman di `local.properties` dan diakses melalui `BuildConfig`.

---

## Fitur Bonus (Bonus Points)
*   **[v] Multi-turn Conversation (+5%)**: AI mampu mengingat konteks percakapan sebelumnya dalam satu sesi menggunakan riwayat pesan (`conversationHistory`).

---

## Cara Instalasi

1.  Clone repository ini dan masuk ke branch `week-9`.
2.  Buka [Google AI Studio](https://aistudio.google.com/) untuk mendapatkan API Key.
3.  Buka file `local.properties` di root project.
4.  Tambahkan baris berikut:
    ```properties
    GEMINI_API_KEY=KODE_API_KEY_ANDA
    ```
5.  Lakukan **Build > Rebuild Project** di Android Studio.
6.  Jalankan aplikasi dan klik ikon **AI Assistant (Wajah)** di Top Bar layar utama.

---

## Screenshot Aplikasi

| Chat Interface | Typing Indicator | Error Handling |
|--- | --- | --- |
| ![Chat](chat.png) | ![Typing](typing.png) | ![Error](retry.png) |

---

## Demo Video
[Link Video Demo Tugas 9]
