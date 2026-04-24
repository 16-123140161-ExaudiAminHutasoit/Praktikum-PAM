# Tugas Praktikum 8

**Nama : Exaudi Amin Hutasoit**

**NIM : 123140161**

**Kelas : PAM RB**

## Deskripsi

Upgrade aplikasi **Notes App** dengan implementasi **Dependency Injection (Koin)** dan **Platform Specific Features** (Device Info & Network Monitor). Aplikasi ini menggunakan arsitektur yang bersih dengan pemisahan tanggung jawab yang jelas.

## Fitur Utama

* **Koin Dependency Injection**: Semua dependensi (Database, Repository, Settings, Platform Features, ViewModel) dikelola dan di-inject menggunakan Koin.
* **Platform Info (expect/actual)**: Menampilkan informasi perangkat (Model, Pabrikan, Versi OS) di halaman Settings.
* **Network Monitor**: Menampilkan indikator status jaringan (Online/Offline) secara real-time di layar utama.
* **Database SQLDelight**: Penyimpanan lokal persisten untuk catatan.
* **Jetpack DataStore**: Menyimpan preferensi tema dan urutan sortir.
* **Fitur CRUD & Search**: Membuat, membaca, memperbarui, menghapus, dan mencari catatan.

## Arsitektur

Aplikasi menggunakan pola arsitektur **MVVM (Model-View-ViewModel)** dengan **Dependency Injection**:
* **UI (Compose)**: Halaman aplikasi yang reaktif.
* **ViewModel**: Mengelola state UI dan berinteraksi dengan repository.
* **Repository**: Abstraksi sumber data (SQLDelight).
* **Platform Module**: Implementasi fitur spesifik Android (Network & Device Info).
* **DI Module (Koin)**: Mengatur siklus hidup dan penyediaan objek ke seluruh aplikasi.

## Database Schema (SQLDelight)

```sql
CREATE TABLE NoteEntity (
    id TEXT NOT NULL PRIMARY KEY,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    isFavorite INTEGER NOT NULL DEFAULT 0,
    createdAt INTEGER NOT NULL
);
```

## Cara Menjalankan

1. Pilih branch **week-8**.
2. Clone repository dan buka di Android Studio.
3. Tunggu proses **Gradle Sync**.
4. Jalankan aplikasi pada emulator atau perangkat fisik.

## Screenshot Aplikasi

| Network Indicator (Online) | Network Indicator (Offline) | Device Info (Settings) |
|---|---|---|
| ![Online](screenshot_online.png) | ![Offline](screenshot_offline.png) | ![Device Info](screenshot_device_info.png) |
