// Package declaration
package com.example.cupcake.ui.theme

// Mengimpor library yang dibutuhkan untuk menggunakan Compose dan elemen-elemen lainnya.
import androidx.compose.foundation.layout.Arrangement // Untuk mengatur jarak antar elemen dalam layout.
import androidx.compose.foundation.layout.Column // Untuk membuat kolom vertikal.
import androidx.compose.foundation.layout.Row // Untuk membuat baris horizontal.
import androidx.compose.foundation.layout.fillMaxHeight // Untuk mengisi tinggi penuh.
import androidx.compose.foundation.layout.fillMaxWidth // Untuk mengisi lebar penuh.
import androidx.compose.foundation.layout.padding // Untuk memberi padding di sekitar elemen.
import androidx.compose.foundation.selection.selectable // Untuk menambahkan pilihan seleksi.
import androidx.compose.material3.Button // Untuk membuat tombol.
import androidx.compose.material3.Divider // Untuk membuat garis pemisah.
import androidx.compose.material3.OutlinedButton // Untuk membuat tombol bertema outline.
import androidx.compose.material3.RadioButton // Untuk membuat tombol radio.
import androidx.compose.material3.Text // Untuk menampilkan teks di UI.
import androidx.compose.runtime.Composable // Menandai fungsi sebagai Composable untuk UI deklaratif.
import androidx.compose.runtime.getValue // Untuk mengambil nilai dari variabel.
import androidx.compose.runtime.mutableStateOf // Untuk membuat variabel yang bisa berubah.
import androidx.compose.runtime.saveable.rememberSaveable // Untuk mengingat nilai meskipun layar direfresh.
import androidx.compose.runtime.setValue // Untuk mengubah nilai variabel.
import androidx.compose.ui.Alignment // Untuk mengatur posisi elemen dalam layout.
import androidx.compose.ui.Modifier // Untuk mengubah atau menyesuaikan tampilan elemen.
import androidx.compose.ui.res.dimensionResource // Untuk mengambil dimensi dari sumber daya.
import androidx.compose.ui.res.stringResource // Untuk mengambil string dari file sumber daya.
import androidx.compose.ui.tooling.preview.Preview // Untuk preview UI di Android Studio.
import com.example.cupcake.R // Untuk mengakses sumber daya aplikasi.
import com.example.cupcake.ui.theme.FormattedPriceLabel // Mengimpor fungsi komponen untuk menampilkan harga.
import com.example.cupcake.ui.theme.CupcakeTheme // Tema aplikasi untuk digunakan dalam preview.

/**
 * Composable untuk menampilkan daftar pilihan opsi menggunakan RadioButton.
 *
 * [onSelectionChanged] adalah lambda yang memberi tahu saat opsi baru dipilih.
 * [onCancelButtonClicked] adalah lambda yang membatalkan pesanan saat tombol cancel diklik.
 * [onNextButtonClicked] adalah lambda yang memicu navigasi ke layar berikutnya.
 */
@Composable
fun SelectOptionScreen(
    subtotal: String, // Total harga untuk ditampilkan.
    options: List<String>, // Daftar opsi yang akan ditampilkan.
    onSelectionChanged: (String) -> Unit = {}, // Lambda untuk menangani perubahan pilihan.
    onCancelButtonClicked: () -> Unit = {}, // Lambda untuk membatalkan pesanan.
    onNextButtonClicked: () -> Unit = {}, // Lambda untuk navigasi ke layar berikutnya.
    modifier: Modifier = Modifier // Modifier untuk penyesuaian tambahan pada elemen UI.
) {
    // Variabel untuk menyimpan pilihan yang dipilih, menggunakan rememberSaveable agar tetap tersimpan saat rotasi perangkat.
    var selectedValue by rememberSaveable { mutableStateOf("") }

    // Layout utama menggunakan Column, dengan jarak antar elemen vertikal yang diatur.
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Kolom pertama untuk menampilkan opsi-opsi yang dapat dipilih oleh pengguna.
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            // Iterasi atas setiap item dalam daftar opsi dan membuat radio button untuk setiap opsi.
            options.forEach { item ->
                Row(
                    modifier = Modifier.selectable( // Menambahkan kemampuan memilih pada setiap baris.
                        selected = selectedValue == item, // Menandakan item yang dipilih.
                        onClick = {
                            selectedValue = item // Update nilai saat item dipilih.
                            onSelectionChanged(item) // Panggil callback untuk memberitahu pemilihan baru.
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Radio button untuk setiap item.
                    RadioButton(
                        selected = selectedValue == item, // Menandakan status terpilih.
                        onClick = {
                            selectedValue = item // Update nilai pilihan.
                            onSelectionChanged(item) // Notifikasi perubahan.
                        }
                    )
                    // Menampilkan nama item opsi.
                    Text(item)
                }
            }
            // Divider untuk memberi pemisah antara daftar opsi dan label harga.
            Divider(
                thickness = dimensionResource(R.dimen.thickness_divider),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )
            // Menampilkan label harga subtotal dengan format yang telah disesuaikan.
            FormattedPriceLabel(
                subtotal = subtotal,
                modifier = Modifier
                    .align(Alignment.End) // Menyelaraskan ke kanan.
                    .padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
            )
        }
        // Baris tombol Cancel dan Next di bagian bawah.
        Row(
            modifier = Modifier
                .fillMaxWidth() // Tombol memenuhi lebar layar.
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.Bottom
        ) {
            // Tombol Cancel
            OutlinedButton(
                modifier = Modifier.weight(1f), // Tombol berbagi ruang horizontal secara merata.
                onClick = onCancelButtonClicked // Menangani klik tombol Cancel.
            ) {
                Text(stringResource(R.string.cancel)) // Menampilkan teks "Cancel"
            }
            // Tombol Next (berfungsi hanya setelah memilih opsi)
            Button(
                modifier = Modifier.weight(1f), // Tombol berbagi ruang horizontal.
                enabled = selectedValue.isNotEmpty(), // Tombol aktif hanya jika ada pilihan.
                onClick = onNextButtonClicked // Menangani klik tombol Next.
            ) {
                Text(stringResource(R.string.next)) // Menampilkan teks "Next"
            }
        }
    }
}

// Preview fungsi SelectOptionScreen dalam tema aplikasi.
@Preview
@Composable
fun SelectOptionPreview() {
    CupcakeTheme { // Menggunakan tema aplikasi untuk preview.
        SelectOptionScreen(
            subtotal = "299.99", // Menampilkan harga subtotal.
            options = listOf("Option 1", "Option 2", "Option 3", "Option 4"), // Daftar pilihan opsi.
            modifier = Modifier.fillMaxHeight() // Menyesuaikan tinggi layar untuk preview.
        )
    }
}
