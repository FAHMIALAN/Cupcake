// Package declaration untuk file ini
package com.example.cupcake.ui.theme

// Import yang diperlukan untuk menggunakan berbagai kelas dan fungsionalitas di dalam aplikasi
import androidx.lifecycle.ViewModel // Untuk membuat ViewModel yang menyimpan data UI dan logika terkait.
import com.example.cupcake.data.OrderUiState // Model data yang menyimpan status UI untuk pesanan cupcake.
import kotlinx.coroutines.flow.MutableStateFlow // Untuk membuat aliran status UI yang bisa dimutasi.
import kotlinx.coroutines.flow.StateFlow // Untuk mengakses status UI yang immutable.
import kotlinx.coroutines.flow.asStateFlow // Untuk mengonversi MutableStateFlow menjadi StateFlow.
import kotlinx.coroutines.flow.update // Untuk memperbarui nilai MutableStateFlow.
import java.text.NumberFormat // Untuk memformat harga menjadi format mata uang.
import java.text.SimpleDateFormat // Untuk memformat tanggal.
import java.util.Calendar // Untuk mendapatkan tanggal dan waktu saat ini.
import java.util.Locale // Untuk mendapatkan pengaturan lokal perangkat.

private const val PRICE_PER_CUPCAKE = 2.00 // Harga untuk satu cupcake
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00 // Biaya tambahan jika pengambilan dilakukan di hari yang sama

/**
 * [OrderViewModel] menyimpan informasi tentang pesanan cupcake, termasuk jumlah, rasa, dan
 * tanggal pengambilan. ViewModel ini juga menghitung total harga berdasarkan detail pesanan.
 */
class OrderViewModel : ViewModel() {

    // State untuk menyimpan data UI pesanan cupcake, diinisialisasi dengan opsi pickup
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))

    // Ekspose UI state sebagai StateFlow yang hanya bisa dibaca dari luar
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    /**
     * Mengatur jumlah [numberCupcakes] dalam pesanan dan memperbarui harga
     */
    fun setQuantity(numberCupcakes: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberCupcakes, // Memperbarui jumlah cupcake
                price = calculatePrice(quantity = numberCupcakes) // Menghitung ulang harga
            )
        }
    }

    /**
     * Mengatur [desiredFlavor] rasa cupcake untuk pesanan ini.
     * Hanya satu rasa yang dapat dipilih untuk satu pesanan.
     */
    fun setFlavor(desiredFlavor: String) {
        _uiState.update { currentState ->
            currentState.copy(flavor = desiredFlavor) // Memperbarui rasa cupcake
        }
    }

    /**
     * Mengatur [pickupDate] untuk pesanan ini dan memperbarui harga berdasarkan tanggal pengambilan
     */
    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                date = pickupDate, // Memperbarui tanggal pengambilan
                price = calculatePrice(pickupDate = pickupDate) // Menghitung ulang harga
            )
        }
    }

    /**
     * Mereset status pesanan ke keadaan awal (kosong)
     */
    fun resetOrder() {
        _uiState.value = OrderUiState(pickupOptions = pickupOptions()) // Mengatur ulang status pesanan
    }

    /**
     * Menghitung harga berdasarkan detail pesanan: jumlah cupcakes dan tanggal pengambilan
     */
    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity, // Menggunakan jumlah yang ada di state jika tidak diberikan parameter
        pickupDate: String = _uiState.value.date // Menggunakan tanggal pengambilan yang ada di state
    ): String {
        var calculatedPrice = quantity * PRICE_PER_CUPCAKE // Harga dasar berdasarkan jumlah cupcakes
        // Jika pengambilan dilakukan hari yang sama (first option), tambahkan biaya tambahan
        if (pickupOptions()[0] == pickupDate) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice) // Format harga menjadi mata uang
        return formattedPrice // Mengembalikan harga yang sudah diformat
    }

    /**
     * Menghasilkan daftar opsi tanggal pengambilan dimulai dari tanggal saat ini dan 3 tanggal berikutnya
     */
    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>() // Daftar opsi tanggal
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault()) // Format tanggal
        val calendar = Calendar.getInstance() // Mendapatkan kalender saat ini
        // Menambahkan tanggal saat ini dan 3 tanggal berikutnya
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time)) // Format dan tambahkan tanggal ke daftar
            calendar.add(Calendar.DATE, 1) // Tambahkan satu hari ke tanggal kalender
        }
        return dateOptions // Mengembalikan daftar tanggal
    }
}
