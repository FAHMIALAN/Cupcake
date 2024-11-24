package com.example.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.theme.OrderSummaryScreen
import com.example.cupcake.ui.theme.SelectOptionScreen
import com.example.cupcake.ui.theme.StartOrderScreen
import com.example.cupcake.ui.theme.OrderViewModel

/**
 * enum values that represent the screens in the app
 */
enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name), // Layar awal aplikasi
    Flavor(title = R.string.choose_flavor), // Layar pemilihan rasa
    Pickup(title = R.string.choose_pickup_date), // Layar pemilihan tanggal pengambilan
    Summary(title = R.string.order_summary) // Layar ringkasan pesanan
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen, // Layar saat ini
    canNavigateBack: Boolean, // Apakah dapat melakukan navigasi kembali
    navigateUp: () -> Unit, // Fungsi untuk menavigasi ke layar sebelumnya
    modifier: Modifier = Modifier // Modifikasi tampilan
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) }, // Menampilkan judul layar
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer // Warna latar belakang
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) { // Jika bisa navigasi kembali
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, // Ikon panah kembali
                        contentDescription = stringResource(R.string.back_button) // Deskripsi konten
                    )
                }
            }
        }
    )
}

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(), // Mendapatkan ViewModel untuk pesanan
    navController: NavHostController = rememberNavController() // Mengingat kontrol navigasi
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState() // Mengambil entri stack kembali saat ini
    // Get the name of the current screen
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name // Menentukan layar saat ini
    )

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen, // Menyediakan layar saat ini
                canNavigateBack = navController.previousBackStackEntry != null, // Cek apakah bisa kembali
                navigateUp = { navController.navigateUp() } // Fungsi untuk kembali
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState() // Mengambil state UI dari ViewModel

        NavHost(
            navController = navController, // Kontrol navigasi
            startDestination = CupcakeScreen.Start.name, // Menentukan layar awal
            modifier = Modifier
                .fillMaxSize() // Mengisi ukuran maksimum
                .verticalScroll(rememberScrollState()) // Mengizinkan scroll vertikal
                .padding(innerPadding) // Menambahkan padding
        ) {
            composable(route = CupcakeScreen.Start.name) { // Layar awal
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions, // Opsi kuantitas
                    onNextButtonClicked = {
                        viewModel.setQuantity(it) // Mengatur kuantitas di ViewModel
                        navController.navigate(CupcakeScreen.Flavor.name) // Navigasi ke layar rasa
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)) // Padding medium
                )
            }
            composable(route = CupcakeScreen.Flavor.name) { // Layar pemilihan rasa
                val context = LocalContext.current // Mendapatkan konteks lokal
                SelectOptionScreen(
                    subtotal = uiState.price, // Menyediakan subtotal
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name) }, // Navigasi ke layar pengambilan
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController) // Membatalkan pesanan
                    },
                    options = DataSource.flavors.map { id -> context.resources.getString(id) }, // Opsi rasa
                    onSelectionChanged = { viewModel.setFlavor(it) }, // Mengatur rasa di ViewModel
                    modifier = Modifier.fillMaxHeight() // Mengisi tinggi maksimum
                )
            }
            composable(route = CupcakeScreen.Pickup.name) { // Layar pemilihan tanggal pengambilan
                SelectOptionScreen(
                    subtotal = uiState.price, // Menyediakan subtotal
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name) }, // Navigasi ke layar ringkasan
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController) // Membatalkan pesanan
                    },
                    options = uiState.pickupOptions, // Opsi tanggal pengambilan
                    onSelectionChanged = { viewModel.setDate(it) }, // Mengatur tanggal di ViewModel
                    modifier = Modifier.fillMaxHeight() // Mengisi tinggi maksimum
                )
            }
            composable(route = CupcakeScreen.Summary.name) { // Layar ringkasan pesanan
                val context = LocalContext.current // Mendapatkan konteks lokal
                OrderSummaryScreen(
                    orderUiState = uiState, // Menyediakan state pesanan
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController) // Membatalkan pesanan
                    },
                    onSendButtonClicked = { subject: String, summary: String -> // Fungsi untuk mengirim pesanan
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    modifier = Modifier.fillMaxHeight() // Mengisi tinggi maksimum
                )
            }
        }
    }
}

/**
 * Resets the [OrderUiState] and pops up to [CupcakeScreen.Start]
 */
private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel, // ViewModel pesanan
    navController: NavHostController // Kontrol navigasi
) {
    viewModel.resetOrder() // Mengatur ulang state pesanan
    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false) // Kembali ke layar awal
}

/**
 * Creates an intent to share order details
 */
private fun shareOrder(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain" // Tipe data yang akan dibagikan
        putExtra(Intent.EXTRA_SUBJECT, subject) // Subjek pesan
        putExtra(Intent.EXTRA_TEXT, summary) // Isi pesan
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order) // Judul untuk chooser
        )
    )
}