package com.example.blinkitclone

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// NOTE: Colors removed from here to fix "Conflicting Declarations" error.
// They are now being read from your BlinkitData.kt file.

@Composable
fun BlinkitHomeScreen(onViewCart: () -> Unit, onProfileClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column {
            HeaderSection(onProfileClick = onProfileClick)

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { PromoBanner() }
                item { CategorySection() }
                item {
                    Text(
                        "Dairy, Bread & Eggs",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                items(BlinkitData.productList.chunked(2)) { pair ->
                    Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                        pair.forEach { product ->
                            ProductCard(product, Modifier.weight(1f))
                        }
                        if (pair.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }

        // Floating Cart Bar
        val totalCount = CartManager.cartItems.values.sum()
        if (totalCount > 0) {
            CartBottomBar(totalCount, onClick = onViewCart)
        }
    }
}

@Composable
fun HeaderSection(onProfileClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().background(BlinkitYellow).padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
            Text("Blinkit in 8 mins", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            IconButton(onClick = onProfileClick) {
                Icon(Icons.Default.Person, contentDescription = "Profile", modifier = Modifier.size(28.dp))
            }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth().height(50.dp).background(Color.White, RoundedCornerShape(12.dp)),
            placeholder = { Text("Search 'milk'", fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.Transparent, focusedBorderColor = Color.Transparent)
        )
    }
}

@Composable
fun PromoBanner() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE3F2FD)),
        contentAlignment = Alignment.Center
    ) {
        Text("Get 50% OFF on Fresh Veggies", color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun CategorySection() {
    val categoryList = listOf(
        "Vegetables" to "https://cdn-icons-png.flaticon.com/128/2329/2329865.png",
        "Dairy" to "https://cdn-icons-png.flaticon.com/128/3050/3050158.png",
        "Munchies" to "https://cdn-icons-png.flaticon.com/128/2553/2553691.png",
        "Drinks" to "https://cdn-icons-png.flaticon.com/128/2405/2405479.png"
    )

    Column(Modifier.padding(16.dp)) {
        Text("Shop by Category", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            categoryList.forEach { (name, icon) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(Modifier.size(70.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFF3F9FB)), Alignment.Center) {
                        AsyncImage(model = icon, contentDescription = null, modifier = Modifier.size(45.dp))
                    }
                    Text(name, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp), fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, modifier: Modifier) {
    val quantity = CartManager.cartItems[product.id] ?: 0

    Card(
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(0.5.dp, Color(0xFFF1F1F1))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box {
                AsyncImage(
                    model = product.imageRes,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxWidth().height(110.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
                Text(
                    product.time,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomStart).background(Color.White).padding(horizontal = 4.dp)
                )
            }
            Text(product.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, maxLines = 2)
            Text(product.weight, fontSize = 12.sp, color = Color.Gray)

            Row(Modifier.fillMaxWidth().padding(top = 8.dp), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Column {
                    Text("₹${product.discountPrice}", fontWeight = FontWeight.Bold)
                    Text("₹${product.originalPrice}", fontSize = 11.sp, textDecoration = TextDecoration.LineThrough, color = Color.Gray)
                }

                if (quantity == 0) {
                    Button(
                        onClick = { CartManager.addToCart(product.id) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE8E8E8)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("ADD", color = BlinkitGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(BlinkitGreen)
                    ) {
                        Text("-", color = Color.White, modifier = Modifier.clickable { CartManager.removeFromCart(product.id) }.padding(horizontal = 12.dp, vertical = 6.dp))
                        Text("$quantity", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("+", color = Color.White, modifier = Modifier.clickable { CartManager.addToCart(product.id) }.padding(horizontal = 12.dp, vertical = 6.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CartBottomBar(totalCount: Int, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(BlinkitGreen)
                .clickable { onClick() }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically, // Use named arguments to be safe
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("$totalCount ITEMS", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text("₹${CartManager.getTotalPrice()}", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("View Cart", color = Color.White, fontWeight = FontWeight.Bold)
                Icon(Icons.Default.ShoppingCart, null, tint = Color.White, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}