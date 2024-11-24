// Package declaration
package com.example.cupcake.ui.theme

// Mengimpor library yang diperlukan untuk komponen UI, pengelolaan layout, dan gambar.
import androidx.annotation.StringRes // Untuk menggunakan referensi string resource.
import androidx.compose.foundation.Image // Untuk menampilkan gambar.
import androidx.compose.foundation.layout.* // Untuk menggunakan layout dan modifier lainnya.
import androidx.compose.material3.Button // Untuk membuat tombol.
import androidx.compose.material3.MaterialTheme // Untuk mengakses tema material.
import androidx.compose.material3.Text // Untuk menampilkan teks.
import androidx.compose.runtime.Composable // Menandai fungsi sebagai composable UI.
import androidx.compose.ui.Alignment // Untuk mengatur penempatan elemen di dalam layout.
import androidx.compose.ui.Modifier // Untuk modifikasi elemen UI.
import androidx.compose.ui.res.dimensionResource // Untuk mengambil dimensi dari sumber daya.
import androidx.compose.ui.res.painterResource // Untuk memuat gambar dari sumber daya.
import androidx.compose.ui.res.stringResource // Untuk memuat string dari sumber daya.
import androidx.compose.ui.tooling.preview.Preview // Untuk preview UI di Android Studio.
import androidx.compose.ui.unit.dp // Untuk menetapkan ukuran dalam dp.
import com.example.cupcake.R // Untuk mengakses sumber daya aplikasi.
import com.example.cupcake.data.DataSource // Untuk mengakses data sumber daya.
import com.example.cupcake.ui.theme.CupcakeTheme // Tema aplikasi untuk digunakan dalam preview.

/**
 * Composable yang menampilkan layar pemesanan kue cupcake.
 * [quantityOptions] adalah daftar pasangan (jumlah, harga) untuk setiap opsi jumlah kue yang bisa dipilih.
 * [onNextButtonClicked] adalah lambda yang dipanggil ketika tombol Next diklik.
 */
@Composable
fun StartOrderScreen(
    quantityOptions: List<Pair<Int, Int>>, // Daftar opsi jumlah dan harga kue.
    onNextButtonClicked: (Int) -> Unit, // Lambda yang akan dijalankan saat tombol Next diklik.
    modifier: Modifier = Modifier // Modifier untuk penyesuaian layout.
) {
    // Layout utama menggunakan Column untuk menata elemen-elemen secara vertikal.
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween // Memberikan ruang antar elemen secara vertikal.
    ) {
        // Kolom pertama untuk menampilkan gambar dan teks.
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally, // Menyelaraskan elemen secara horizontal.
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)) // Menambahkan jarak antar elemen.
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium))) // Menambahkan ruang kosong.
            Image(
                painter = painterResource(R.drawable.cupcake), // Menampilkan gambar cupcake.
                contentDescription = null, // Deskripsi konten untuk aksesibilitas (dikosongkan di sini).
                modifier = Modifier.width(300.dp) // Menetapkan lebar gambar.
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium))) // Menambahkan ruang kosong.
            Text(
                text = stringResource(R.string.order_cupcakes), // Menampilkan teks "Order Cupcakes".
                style = MaterialTheme.typography.headlineSmall // Menetapkan gaya teks.
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small))) // Menambahkan ruang kosong.
        }
        // Kolom kedua untuk menampilkan tombol jumlah kue yang dapat dipilih.
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally, // Menyelaraskan tombol secara horizontal.
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_medium) // Memberikan ruang antar tombol.
            )
        ) {
            // Iterasi atas setiap item dalam daftar opsi jumlah kue.
            quantityOptions.forEach { item ->
                // Menampilkan tombol untuk memilih jumlah kue.
                SelectQuantityButton(
                    labelResourceId = item.first, // Menampilkan jumlah kue pada tombol.
                    onClick = { onNextButtonClicked(item.second) }, // Memanggil lambda saat tombol diklik.
                    modifier = Modifier.fillMaxWidth(), // Tombol memenuhi lebar.
                )
            }
        }
    }
}

/**
 * Custom button composable untuk menampilkan jumlah kue yang dipilih.
 * [labelResourceId] adalah ID string resource untuk label tombol.
 * [onClick] adalah lambda yang dipanggil saat tombol diklik.
 */
@Composable
fun SelectQuantityButton(
    @StringRes labelResourceId: Int, // ID untuk string resource pada tombol.
    onClick: () -> Unit, // Lambda yang dipanggil saat tombol diklik.
    modifier: Modifier = Modifier // Modifier untuk penyesuaian tampilan tombol.
) {
    Button(
        onClick = onClick, // Fungsi yang dipanggil saat tombol diklik.
        modifier = modifier.widthIn(min = 250.dp) // Tombol memiliki lebar minimal 250dp.
    ) {
        // Menampilkan teks pada tombol berdasarkan string resource.
        Text(stringResource(labelResourceId))
    }
}

// Preview untuk melihat tampilan StartOrderScreen.
@Preview
@Composable
fun StartOrderPreview() {
    CupcakeTheme { // Menggunakan tema aplikasi untuk preview.
        StartOrderScreen(
            quantityOptions = DataSource.quantityOptions, // Mengambil opsi jumlah kue dari data sumber.
            onNextButtonClicked = {}, // Fungsi kosong untuk onNextButtonClicked.
            modifier = Modifier
                .fillMaxSize() // Mengisi seluruh ukuran layar.
                .padding(dimensionResource(R.dimen.padding_medium)) // Memberikan padding.
        )
    }
}
