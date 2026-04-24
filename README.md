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
![Loding State](<img width="476" height="1052" alt="L" src="https://github.com/user-attachments/assets/8ebe320a-16f2-4f7d-acc3-da96a2c55f1c" />
)
![Empty State](<img width="467" height="1053" alt="mp" src="https://github.com/user-attachments/assets/9021317f-0b45-4dfb-b4e8-fadca315ac3b" />
)
![Content State](<img width="466" height="1051" alt="D" src="https://github.com/user-attachments/assets/4a0a131f-2eb6-4609-8162-0a5719d0a330" />
)
![Search Berhasil](<img width="473" height="1051" alt="s" src="https://github.com/user-attachments/assets/0293abee-207f-491b-8cca-9f0bfe122dfa" />
)
![Search Tidak Ditemukan](<img width="474" height="1051" alt="s2" src="https://github.com/user-attachments/assets/a64e2377-0cf0-40b8-b12a-9a8ea2c86b63" />
)
![Edit Note](<img width="469" height="1054" alt="me" src="https://github.com/user-attachments/assets/529640c4-ca8a-4094-9119-85a96b7b0797" />
)
![Settings Screen](<img width="460" height="1055" alt="m" src="https://github.com/user-attachments/assets/87284ca5-58a9-4688-aa52-106ef0265390" />
)

