# Tugas Praktikum 7

**Nama : Exaudi Amin Hutasoit**

**NIM : 123140161**

**Kelas : PAM RA**

## Deskripsi

Aplikasi **Notes App** dengan penyimpanan lokal menggunakan **SQLDelight** serta fitur **CRUD**, **Search**, **Settings**, dan konsep **Offline-First**.

## Fitur Utama

* Menyimpan data catatan secara lokal menggunakan **SQLDelight**
* Menampilkan daftar catatan pada halaman utama
* Menambahkan catatan baru (**Create**)
* Menampilkan detail catatan (**Read**)
* Mengedit catatan yang sudah ada (**Update**)
* Menghapus catatan (**Delete**)
* Mencari catatan berdasarkan judul atau isi (**Search**)
* Mengatur **theme** aplikasi (**Light**, **Dark**, **System**)
* Mengatur **sort order** catatan (**Newest First**, **Oldest First**)
* Mendukung konsep **offline-first**, sehingga data tetap dapat diakses tanpa internet
* Menampilkan state **loading**, **empty**, dan **content**

## Database yang Digunakan

* **SQLDelight**
* Schema tabel yang digunakan:
    ```sql
    CREATE TABLE NoteEntity (
        id TEXT NOT NULL PRIMARY KEY,
        title TEXT NOT NULL,
        content TEXT NOT NULL,
        isFavorite INTEGER NOT NULL DEFAULT 0,
        createdAt INTEGER NOT NULL
    );
    ```

## Cara Menjalankan (Android Studio)

1. Pilih branch **week-7**.
2. Clone / download repository:
    * `https://github.com/16-123140161-ExaudiAminHutasoit/Praktikum-PAM.git`
3. Buka folder project tugas praktikum 7 menggunakan Android Studio.
4. Tunggu proses **Gradle Sync** sampai selesai.
5. Jalankan aplikasi dengan menekan tombol **Run**.
6. Pilih emulator/device Android, lalu aplikasi akan terbuka.

## Screenshot Aplikasi

| Deskripsi | Screenshot |
|---|---|
| Home Screen | ![Home](1.png) |
| Search Feature | ![Search](2.png) |
| Loading State | ![Loading](3.png) |
| Add Note | ![Add](4.png) |
| Edit Note | ![Edit](5.png) |
| Settings | ![Settings](6.png) |
| Dark Mode | ![Dark](7.png) |
| Detail Screen | ![Detail](8.png) |

## Demo

https://youtu.be/TOJ-9FblY3E 
