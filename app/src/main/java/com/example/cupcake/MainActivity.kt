package com.example.cupcake // Menentukan package tempat file ini berada. Berguna untuk pengorganisasian dan namespace.

import android.os.Bundle // Mengimpor class Bundle yang digunakan untuk menyimpan dan mengambil data saat activity dibuat atau di-restart.
import androidx.activity.ComponentActivity // Mengimpor ComponentActivity, sebuah activity dasar yang mendukung Jetpack Compose.
import androidx.activity.compose.setContent // Mengimpor fungsi untuk mengatur konten UI menggunakan Jetpack Compose.
import androidx.activity.enableEdgeToEdge // Mengimpor fungsi untuk mengaktifkan tampilan edge-to-edge (layar penuh).
import com.example.cupcake.ui.theme.CupcakeTheme // Mengimpor tema khusus aplikasi yang sudah didefinisikan di folder `ui.theme`.

class MainActivity : ComponentActivity() { // Mendeklarasikan MainActivity yang mewarisi ComponentActivity, komponen dasar untuk aplikasi Compose.
    override fun onCreate(savedInstanceState: Bundle?) { // Fungsi yang dipanggil saat activity dibuat.
        enableEdgeToEdge() // Mengaktifkan fitur edge-to-edge untuk pengalaman UI layar penuh.
        super.onCreate(savedInstanceState) // Memanggil implementasi superclass untuk memastikan lifecycle activity berjalan normal.
        setContent { // Mengatur konten UI untuk activity ini menggunakan Jetpack Compose.
            CupcakeTheme { // Menerapkan tema Cupcake untuk seluruh komponen dalam UI.
                CupcakeApp() // Menampilkan fungsi utama aplikasi yang mendefinisikan UI.
            }
        }
    }
}
