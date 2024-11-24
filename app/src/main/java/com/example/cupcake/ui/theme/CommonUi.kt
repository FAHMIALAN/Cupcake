// Package declaration: menentukan lokasi file dalam struktur proyek
package com.example.cupcake.ui.theme

// Import libraries yang digunakan dalam file ini
import androidx.compose.material3.MaterialTheme // Untuk menerapkan tema Material Design 3, seperti gaya teks dan warna.
import androidx.compose.material3.Text // Komponen untuk menampilkan teks pada UI.
import androidx.compose.runtime.Composable // Anotasi untuk menandai fungsi sebagai Composable, digunakan dalam Jetpack Compose.
import androidx.compose.ui.Modifier // Untuk mengubah atau menyesuaikan tampilan elemen UI.
import androidx.compose.ui.res.stringResource // Untuk mendapatkan string dari file sumber daya (res/values/strings.xml).
import com.example.cupcake.R // Mengimpor referensi ke file `R` untuk mengakses sumber daya aplikasi seperti string, drawable, dll.

@Composable // Menandai fungsi sebagai Composable, sehingga bisa digunakan untuk mendesain UI di Jetpack Compose.
fun FormattedPriceLabel(
    subtotal: String, // Parameter untuk menerima subtotal harga dalam bentuk string.
    modifier: Modifier = Modifier // Parameter opsional untuk memungkinkan penyesuaian elemen UI (default-nya adalah Modifier kosong).
) {
    Text( // Komponen Compose untuk menampilkan teks.
        text = stringResource(R.string.subtotal_price, subtotal),
        // Mengambil string dari file res/values/strings.xml dengan ID subtotal_price 
        // dan menggantikan placeholder dengan nilai `subtotal`.

        modifier = modifier, // Mengaplikasikan Modifier untuk kustomisasi UI.

        style = MaterialTheme.typography.headlineSmall
        // Menggunakan gaya teks `headlineSmall` dari tema Material Design 3 untuk konsistensi desain.
    )
}
