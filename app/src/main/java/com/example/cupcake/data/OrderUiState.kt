package com.example.cupcake.data // Menentukan package tempat file ini berada, digunakan untuk pengorganisasian kode.

data class OrderUiState( // Mendeklarasikan data class `OrderUiState`, digunakan untuk merepresentasikan state UI terkait pesanan.
    /** Selected cupcake quantity (1, 6, 12) */ // Komentar untuk mendeskripsikan properti berikutnya.
    val quantity: Int = 0, // Menyimpan jumlah cupcake yang dipilih, default-nya adalah 0.
    /** Flavor of the cupcakes in the order (such as "Chocolate", "Vanilla", etc..) */ // Komentar untuk mendeskripsikan properti berikutnya.
    val flavor: String = "", // Menyimpan rasa cupcake yang dipilih, default-nya adalah string kosong.
    /** Selected date for pickup (such as "Jan 1") */ // Komentar untuk mendeskripsikan properti berikutnya.
    val date: String = "", // Menyimpan tanggal pengambilan pesanan, default-nya adalah string kosong.
    /** Total price for the order */ // Komentar untuk mendeskripsikan properti berikutnya.
    val price: String = "", // Menyimpan harga total pesanan, default-nya adalah string kosong.
    /** Available pickup dates for the order */ // Komentar untuk mendeskripsikan properti berikutnya.
    val pickupOptions: List<String> = listOf() // Menyimpan daftar opsi tanggal pengambilan yang tersedia, default-nya adalah daftar kosong.
)
