package com.example.blinkitclone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewCartScreen(onBack: () -> Unit) {
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
                Text("Review Items", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                Spacer(Modifier.height(10.dp))

                LazyColumn(Modifier.weight(1f)) {
                    val itemsInCart = CartManager.cartItems.keys.toList()
                    items(itemsInCart) { id ->
                        val product = BlinkitData.productList.find { it.id == id }
                        val qty = CartManager.cartItems[id] ?: 0
                        product?.let {
                            Row(
                                Modifier.fillMaxWidth().padding(vertical = 12.dp),
                                Arrangement.SpaceBetween,
                                Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(it.name, fontWeight = FontWeight.Medium)
                                    Text("${it.weight} x $qty", fontSize = 12.sp, color = Color.Gray)
                                }
                                Text("₹${it.discountPrice * qty}", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

                Row(
                    Modifier.fillMaxWidth().padding(vertical = 20.dp),
                    Arrangement.SpaceBetween
                ) {
                    Text("Total Amount", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                    Text("₹${CartManager.getTotalPrice()}", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = BlinkitGreen)
                }

                Button(
                    onClick = {
                        // 1. Capture names for history
                        val names = CartManager.cartItems.keys.map { id ->
                            BlinkitData.productList.find { it.id == id }?.name ?: "Unknown"
                        }
                        // 2. Add to history before clearing
                        ProfileManager.orderHistory.add(names)
                        // 3. Trigger popup
                        showSuccessDialog = true
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BlinkitGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Place Order", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            if (showSuccessDialog) {
                Dialog(onDismissRequest = {
                    showSuccessDialog = false
                    CartManager.cartItems.clear()
                    onBack()
                }) {
                    Card(
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.CheckCircle, null, tint = BlinkitGreen, modifier = Modifier.size(80.dp))
                            Spacer(Modifier.height(16.dp))
                            Text("Order Placed!", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                            Text("Arriving in 8 minutes", color = Color.Gray)
                            Spacer(Modifier.height(24.dp))
                            Button(
                                onClick = {
                                    showSuccessDialog = false
                                    CartManager.cartItems.clear()
                                    onBack()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                            ) {
                                Text("Yay!")
                            }
                        }
                    }
                }
            }
        }
    }
}