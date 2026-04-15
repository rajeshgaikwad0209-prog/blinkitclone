package com.example.blinkitclone

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ProfileManager {
    var isLoggedIn by mutableStateOf(false)
    var userName by mutableStateOf("Rajesh Gaikwad")

    // Stores list of previous orders (each order is a list of product names)
    val orderHistory = mutableStateListOf<List<String>>()

    fun logout() {
        isLoggedIn = false
    }
}