package com.example.blinkitclone

import androidx.compose.runtime.mutableStateMapOf

object CartManager {
    // This tracks Product ID and Quantity (e.g., ID 1 -> 3 packets)
    val cartItems = mutableStateMapOf<Int, Int>()

    fun addToCart(productId: Int) {
        val currentCount = cartItems[productId] ?: 0
        cartItems[productId] = currentCount + 1
    }

    fun removeFromCart(productId: Int) {
        val currentCount = cartItems[productId] ?: 0
        if (currentCount > 1) {
            cartItems[productId] = currentCount - 1
        } else {
            cartItems.remove(productId)
        }
    }

    fun getTotalPrice(): Int {
        var total = 0
        cartItems.forEach { (id, qty) ->
            val product = BlinkitData.productList.find { it.id == id }
            total += (product?.discountPrice ?: 0) * qty
        }
        return total
    }
}