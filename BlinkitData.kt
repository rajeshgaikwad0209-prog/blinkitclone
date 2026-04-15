package com.example.blinkitclone

import androidx.compose.ui.graphics.Color

// Centralized Colors - This prevents the "Conflicting Declarations" error
val BlinkitYellow = Color(0xFFFFE141)
val BlinkitGreen = Color(0xFF318616)

data class Product(
    val id: Int,
    val name: String,
    val weight: String,
    val discountPrice: Int,
    val originalPrice: Int,
    val time: String,      // UI uses product.time
    val imageRes: String   // UI uses product.imageRes
)

object BlinkitData {
    val productList = listOf(
        // Dairy & Bread
        Product(1, "Amul Taaza Milk", "500 ml", 28, 30, "8 mins", "https://images.unsplash.com/photo-1550583724-125581cc258b?w=400"),
        Product(2, "Harvest Gold Bread", "400 g", 45, 50, "10 mins", "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400"),

        // Vegetables
        Product(3, "Fresh Potato (Aloo)", "1 kg", 35, 45, "12 mins", "https://images.unsplash.com/photo-1518977676601-b53f02ac6d5d?w=400"),
        Product(4, "Red Onions", "1 kg", 40, 60, "11 mins", "https://images.unsplash.com/photo-1508747703725-719777637510?w=400"),

        // Fruits
        Product(5, "Banana (Robusta)", "6 pcs", 30, 40, "9 mins", "https://images.unsplash.com/photo-1571771894821-ad9b5886479b?w=400"),
        Product(6, "Royal Gala Apple", "4 pcs", 120, 150, "8 mins", "https://images.unsplash.com/photo-1560806887-1e4cd0b6bcd6?w=400"),

        // Snacks & Munchies
        Product(7, "Lay's Classic Salted", "50 g", 20, 20, "10 mins", "https://images.unsplash.com/photo-1566478989037-eec170784d0b?w=400"),
        Product(8, "Maggi Masala Noodles", "70 g", 14, 15, "12 mins", "https://images.unsplash.com/photo-1612927601601-6638404737ce?w=400"),

        // Added 2 more for a full grid
        Product(9, "Coca-Cola Zero", "500 ml", 40, 40, "7 mins", "https://images.unsplash.com/photo-1554866585-cd94860890b7?w=400"),
        Product(10, "Britannia Marie Gold", "250 g", 30, 35, "10 mins", "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=400")
    )
}