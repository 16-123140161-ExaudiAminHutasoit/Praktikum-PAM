# Tugas Praktikum Minggu 10 - Implementasi DI dan Testing

Tugas ini mencakup implementasi Dependency Injection menggunakan Koin dan pengujian aplikasi secara menyeluruh (Unit Test, Flow Test, dan UI Test).

## 1. Implementasi Dependency Injection (Koin)
DI diimplementasikan menggunakan library **Koin** dengan pembagian 3 modul utama untuk memenuhi syarat minimal 2 modul:
*   **`networkModule`**: Konfigurasi HttpClient Ktor untuk kebutuhan API.
*   **`dataModule`**: Injeksi untuk Repository, Database (SQLDelight), dan SettingsManager.
*   **`viewModelModule`**: Injeksi untuk seluruh ViewModel di dalam aplikasi.

## 2. Daftar Test Cases

### A. NoteRepository (Unit Test - 5 Test Cases)
Menggunakan database *in-memory* untuk menjamin kecepatan dan isolasi data.
1.  `insert and get all notes`: Memastikan data berhasil disimpan dan dibaca kembali.
2.  `delete note`: Memastikan penghapusan catatan berfungsi dengan benar.
3.  `search notes`: Menguji logika pencarian berdasarkan judul atau isi catatan.
4.  `get note by id`: Memastikan pengambilan satu data catatan berdasarkan ID unik.
5.  `update note`: Menguji pembaruan data pada catatan yang sudah ada.

### B. NoteViewModel (Unit Test with MockK - 4 Test Cases)
Menggunakan **MockK** untuk memverifikasi interaksi antara ViewModel dan Repository.
1.  `onSearchQueryChange updates searchQuery state`: Verifikasi perubahan state saat user mencari.
2.  `addNote calls repository insertNote`: Memastikan fungsi repositori terpanggil saat menambah data.
3.  `deleteNote calls repository deleteNote`: Memastikan fungsi hapus terhubung ke repositori.
4.  `setSortOrder calls settingsManager`: Memastikan preferensi pengurutan data tersimpan.

### C. Flow Test (Turbine - 2 Test Cases)
Menggunakan library **Turbine** untuk menguji aliran data asinkron.
1.  `notesState emits Loading then Empty`: Menguji emisi status UI saat aplikasi pertama kali dibuka.
2.  `searchQuery emits updated values`: Menguji aliran state pada input pencarian.

### D. UI Test (NotesScreen - 3 Test Cases)
Menggunakan **Compose Test Rule** untuk menguji tampilan antarmuka.
1.  `notesScreen_showsEmptyState`: Verifikasi tampilan saat tidak ada catatan.
2.  `notesScreen_showsNotesList`: Verifikasi daftar catatan muncul saat data tersedia.
3.  `notesScreen_showsTopAppBarTitle`: Memastikan header aplikasi tampil dengan benar.

## 3. Laporan Code Coverage
Minimal syarat coverage adalah 60%. Berikut adalah hasil coverage untuk business logic (Repository & ViewModel):

![Test Coverage Report]([PASTE_NAMA_FILE_SCREENSHOT_ANDA_DISINI])

## 4. Video Demo (45 Detik)
Video demo menjalankan semua test dan menunjukkan hasil coverage dapat dilihat pada file:
`[NAMA_FILE_VIDEO_DEMO.mp4]` atau [Link Video Jika Ada]
