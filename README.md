# Tugas Praktikum Minggu 10 - MyProfilApp

Aplikasi Notes sederhana dengan implementasi Dependency Injection (Koin) dan pengujian otomatis (Unit, Flow, & UI Testing) untuk memenuhi kriteria Praktikum Pengembangan Aplikasi Mobile.

---

## 👤 Identitas
- **Nama:** Exaudi AMin Hutasoit
- **NIM:** 123140161
- **Kelas:** RA

---

## 🛠️ Implementasi Dependency Injection (Koin)
Proyek ini menggunakan **Koin DI** yang terbagi menjadi 2 modul utama:
1.  **`dataModule`**: Mengatur penyediaan `NoteRepository`, `NoteDatabase` (SQLDelight), `SettingsManager`, serta platform logic seperti `DeviceInfo` dan `NetworkMonitor`.
2.  **`viewModelModule`**: Mengatur penyediaan `NoteViewModel` dan `ProfileViewModel` dengan menggunakan DSL `viewModel { ... }`.

Inisialisasi dilakukan pada kelas `MainApplication` yang didaftarkan dalam `AndroidManifest.xml`.

---

## 🧪 Daftar Test Cases (Total: 18 Test Cases)

### 1. Unit Test: NoteRepository (`NoteRepositoryTest.kt`)
*Menguji operasi CRUD pada database menggunakan in-memory SQLite driver.*
- `insert and get all notes`: Verifikasi penyimpanan data berhasil.
- `delete note`: Verifikasi penghapusan data berdasarkan ID.
- `search notes`: Verifikasi fitur filter berdasarkan judul/isi.
- `get note by id`: Verifikasi pengambilan detail catatan.
- `update note`: Verifikasi perubahan data pada catatan yang sudah ada.
- `get note by id returns null if not found`: Penanganan kasus data kosong.
- `search notes with no match returns empty list`: Penanganan kasus pencarian tidak ditemukan.

### 2. Unit Test: NoteViewModel (`NoteViewModelTest.kt`)
*Menguji logika bisnis ViewModel menggunakan **MockK** untuk isolasi.*
- `onSearchQueryChange updates searchQuery state`: Validasi update UI state saat mengetik.
- `addNote calls repository insertNote`: Memastikan fungsi repositori dipanggil saat tambah data.
- `deleteNote calls repository deleteNote`: Memastikan fungsi hapus terhubung ke repositori.
- `setSortOrder calls settingsManager`: Validasi penyimpanan preferensi urutan.
- `setThemeMode calls settingsManager`: Validasi penyimpanan preferensi tema.

### 3. Flow Test: NoteViewModel Flow (`NoteViewModelFlowTest.kt`)
*Menguji aliran data (StateFlow/Flow) secara asinkron menggunakan **Turbine**.*
- `notesState emits Loading then Empty`: Validasi transisi state saat inisialisasi.
- `searchQuery emits updated values`: Validasi emisi data pada pencarian.
- `isOnline emits values from networkMonitor`: Validasi respon UI terhadap status internet.

### 4. UI Test: Notes Screen (`NotesScreenTest.kt`)
*Menguji komponen antarmuka menggunakan **Compose Test Rule**.*
- `notesScreen_showsTopAppBarTitle`: Memastikan header muncul dengan teks yang benar.
- `notesScreen_showsEmptyState`: Memastikan tampilan "Empty State" muncul jika tidak ada catatan.
- `notesScreen_showsSearchField`: Memastikan kolom input pencarian tersedia.

---

## ⚙️ Cara Menjalankan Pengujian
1. Buka proyek di **Android Studio**.
2. Untuk menjalankan **Unit & Flow Test**:
   - Klik kanan folder `app/src/test` -> **Run 'Tests in...'**
3. Untuk menjalankan **UI Test**:
   - Pastikan Emulator/HP terhubung.
   - Klik kanan folder `app/src/androidTest` -> **Run 'Tests in...'**
4. Untuk melihat **Coverage**:
   - Pilih menu **Run** -> **Run 'Tests in...' with Coverage**.
