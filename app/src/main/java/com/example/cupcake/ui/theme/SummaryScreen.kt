// Package declaration
package com.example.cupcake.ui.theme

// Mengimpor komponen yang diperlukan dari Compose UI, pengelolaan teks, dan konteks.
import androidx.compose.foundation.layout.* // Untuk layout dan modifier
import androidx.compose.material3.* // Untuk komponen Material3 seperti Button, Divider, OutlinedButton
import androidx.compose.runtime.Composable // Untuk menandai fungsi sebagai composable
import androidx.compose.ui.Alignment // Untuk penyelarasan elemen dalam layout
import androidx.compose.ui.Modifier // Untuk modifikasi elemen UI
import androidx.compose.ui.platform.LocalContext // Untuk mengambil konteks saat ini
import androidx.compose.ui.res.dimensionResource // Untuk mengambil sumber daya dimensi
import androidx.compose.ui.res.stringResource // Untuk mengambil sumber daya string
import androidx.compose.ui.text.font.FontWeight // Untuk pengaturan bobot font
import androidx.compose.ui.tooling.preview.Preview // Untuk menampilkan preview UI di Android Studio
import com.example.cupcake.R // Untuk mengakses sumber daya aplikasi
import com.example.cupcake.data.OrderUiState // Model data untuk menyimpan status pesanan
import com.example.cupcake.ui.theme.CupcakeTheme // Tema aplikasi untuk digunakan dalam preview

// Composable untuk menampilkan ringkasan pesanan
@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState, // Data pesanan yang ditampilkan
    onCancelButtonClicked: () -> Unit, // Fungsi untuk menangani klik tombol cancel
    onSendButtonClicked: (String, String) -> Unit, // Fungsi untuk menangani klik tombol send
    modifier: Modifier = Modifier // Modifier untuk penyesuaian tampilan
) {
    // Mengambil konteks untuk mengakses sumber daya
    val resources = LocalContext.current.resources

    // Menampilkan jumlah cupcake yang dipilih dengan string yang diformat
    val numberOfCupcakes = resources.getQuantityString(
        R.plurals.cupcakes, // String resource untuk jumlah cupcake
        orderUiState.quantity, // Jumlah cupcake yang dipilih
        orderUiState.quantity // Parameter jumlah untuk dipakai di plural resource
    )

    // Menyusun string ringkasan pesanan menggunakan string resource yang diformat
    val orderSummary = stringResource(
        R.string.order_details,
        numberOfCupcakes,
        orderUiState.flavor,
        orderUiState.date,
        orderUiState.quantity
    )

    // String resource untuk "New Cupcake Order"
    val newOrder = stringResource(R.string.new_cupcake_order)

    // Daftar pasangan (label, nilai) untuk ringkasan pesanan
    val items = listOf(
        Pair(stringResource(R.string.quantity), numberOfCupcakes), // Jumlah kue
        Pair(stringResource(R.string.flavor), orderUiState.flavor), // Rasa kue
        Pair(stringResource(R.string.pickup_date), orderUiState.date) // Tanggal ambil
    )

    // Layout utama menggunakan Column untuk menampilkan informasi pesanan
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween // Menyusun elemen secara vertikal dengan jarak
    ) {
        // Kolom pertama untuk menampilkan detail pesanan
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)), // Padding di sekitar konten
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)) // Jarak antar elemen
        ) {
            // Menampilkan setiap item dalam ringkasan pesanan
            items.forEach { item ->
                Text(item.first.uppercase()) // Menampilkan label dalam huruf kapital
                Text(text = item.second, fontWeight = FontWeight.Bold) // Menampilkan nilai dengan teks tebal
                Divider(thickness = dimensionResource(R.dimen.thickness_divider)) // Garis pemisah
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small))) // Menambahkan ruang kosong
            // Menampilkan label harga total
            FormattedPriceLabel(
                subtotal = orderUiState.price,
                modifier = Modifier.align(Alignment.End) // Menyelaraskan label harga ke kanan
            )
        }
        // Kolom kedua untuk menampilkan tombol-tombol tindakan
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)) // Padding di sekitar tombol
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)) // Jarak antar tombol
            ) {
                // Tombol untuk mengirim pesanan
                Button(
                    modifier = Modifier.fillMaxWidth(), // Tombol memenuhi lebar layar
                    onClick = { onSendButtonClicked(newOrder, orderSummary) } // Memanggil fungsi saat tombol diklik
                ) {
                    Text(stringResource(R.string.send)) // Menampilkan teks pada tombol
                }
                // Tombol untuk membatalkan pesanan
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(), // Tombol memenuhi lebar layar
                    onClick = onCancelButtonClicked // Memanggil fungsi pembatalan saat tombol diklik
                ) {
                    Text(stringResource(R.string.cancel)) // Menampilkan teks pada tombol
                }
            }
        }
    }
}

// Preview untuk melihat tampilan OrderSummaryScreen
@Preview
@Composable
fun OrderSummaryPreview() {
    CupcakeTheme {
        OrderSummaryScreen(
            orderUiState = OrderUiState(0, "Test", "Test", "$300.00"), // Data pesanan contoh
            onSendButtonClicked = { subject: String, summary: String -> }, // Fungsi kosong untuk preview
            onCancelButtonClicked = {}, // Fungsi kosong untuk preview
            modifier = Modifier.fillMaxHeight() // Mengisi seluruh tinggi layar
        )
    }
}
