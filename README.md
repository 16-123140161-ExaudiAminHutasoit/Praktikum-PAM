# Tugas Praktikum 6

**Nama : Exaudi Amin Hutasoit**

**NIM : 123140161**


## Deskripsi

Aplikasi **News Reader** dengan integrasi API publik menggunakan **Ktor Client**. Aplikasi ini menampilkan berita dengan tema Pendidikan dan mendukung fitur navigasi serta pengelolaan state UI (Loading, Success, Error).

## Fitur Utama

* Mengambil data berita dari public API (JSONPlaceholder)
* Menampilkan daftar artikel berita dengan tema Pendidikan
* Menampilkan **title**, **description**, dan **image** pada setiap artikel
* Menampilkan **detail screen** saat artikel diklik
* Mendukung **pull to refresh** untuk memperbarui data
* Menampilkan state **loading**, **success**, dan **error**
* Menggunakan **Repository Pattern** untuk pemanggilan API

## API yang Digunakan

* **JSONPlaceholder** (Mocking News API)
* Endpoint yang digunakan:
    * `https://jsonplaceholder.typicode.com/posts`
    * Data ditransformasi secara lokal di Repository untuk simulasi konten berita Pendidikan.

## Cara Menjalankan (Android Studio)

1. Pilih branch **week-6**.
2. Clone / download repository:
    * `https://github.com/16-123140161-ExaudiAminHutasoit/Praktikum-PAM.git`
3. Buka folder project tugas praktikum 6 menggunakan Android Studio.
4. Tunggu proses **Gradle Sync** sampai selesai.
5. Jalankan aplikasi dengan menekan tombol **Run**.
6. Pilih emulator/device Android, lalu aplikasi akan terbuka dan menampilkan daftar berita.

## Screenshot Aplikasi

<img width="813" height="938" alt="Cuplikan layar 2026-04-20 214943" src="https://github.com/user-attachments/assets/58b1bed5-a472-417f-b087-3151646dbed3" /> 
<img width="668" height="715" alt="Cuplikan layar 2026-04-20 215921" src="https://github.com/user-attachments/assets/cea2f431-2244-4466-92d2-a1d14a7ec1da" /> 
<img width="761" height="733" alt="Cuplikan layar 2026-04-20 220427" src="https://github.com/user-attachments/assets/2395d1e1-735a-49a6-bac4-e64adc231bef" />


 <img width="653" height="721" alt="Cuplikan layar 2026-04-20 220452" src="https://github.com/user-attachments/assets/220aba9a-955a-4e47-acbf-5c072c22dccd" />
