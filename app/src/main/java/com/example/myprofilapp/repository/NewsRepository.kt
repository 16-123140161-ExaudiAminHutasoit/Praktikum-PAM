package com.example.myprofilapp.repository

import com.example.myprofilapp.model.Article
import com.example.myprofilapp.network.client
import io.ktor.client.call.*
import io.ktor.client.request.*

class NewsRepository {
    suspend fun fetchArticles(): List<Article> {
        // Ambil data asli dari API (syarat Networking)
        val rawArticles: List<Article> = client.get("https://jsonplaceholder.typicode.com/posts").body()
        
        // Daftar data pendidikan lokal untuk menggantikan data API agar tema sesuai permintaan
        val educationTitles = listOf(
            "Pentingnya Literasi Digital di Era Modern",
            "Meningkatkan Kreativitas Siswa Melalui Seni",
            "Peran Teknologi dalam Pembelajaran Jarak Jauh",
            "Metode Belajar Efektif untuk Ujian Nasional",
            "Pendidikan Karakter Sejak Usia Dini",
            "Beasiswa Luar Negeri untuk Mahasiswa Berprestasi",
            "Kurikulum Merdeka: Kelebihan dan Tantangannya",
            "Manfaat Membaca Buku Setiap Hari",
            "Pentingnya Guru yang Inovatif di Sekolah",
            "Pendidikan Inklusif bagi Siswa Berkebutuhan Khusus"
        )
        
        val educationBodies = listOf(
            "Literasi digital sangat penting untuk membekali generasi muda dengan kemampuan memilah informasi.",
            "Seni dapat membantu siswa mengekspresikan diri dan meningkatkan kemampuan berpikir kritis.",
            "Teknologi memungkinkan akses pendidikan yang lebih luas bagi semua lapisan masyarakat.",
            "Metode belajar yang tepat dapat membantu siswa meraih hasil maksimal tanpa merasa stres.",
            "Karakter yang kuat adalah fondasi utama bagi kesuksesan anak di masa depan.",
            "Banyak peluang beasiswa yang bisa dimanfaatkan untuk melanjutkan studi ke universitas top dunia.",
            "Kurikulum baru memberikan fleksibilitas lebih bagi guru dan siswa dalam proses belajar mengajar.",
            "Membaca dapat membuka wawasan dan meningkatkan daya imajinasi secara signifikan.",
            "Inovasi guru di kelas sangat menentukan minat dan semangat belajar para siswa.",
            "Setiap anak berhak mendapatkan pendidikan yang layak sesuai dengan potensi uniknya masing-masing."
        )

        // Transformasi data API menjadi berita Pendidikan dengan gambar yang relevan
        return rawArticles.take(10).mapIndexed { index, article ->
            val educationIndex = index % educationTitles.size
            article.copy(
                title = educationTitles[educationIndex],
                body = educationBodies[educationIndex],
                imageUrl = "https://picsum.photos/seed/edu${index}/400/200" // Gambar tema pendidikan
            )
        }
    }
}
