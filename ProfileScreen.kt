package com.example.blinkitclone

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onLogout: () -> Unit, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Account", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // User Info
            Text(text = ProfileManager.userName, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
            Text(text = "IT Student | Rajesh Gaikwad", color = Color.Gray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Recent Orders", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            // Order History List
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(ProfileManager.orderHistory) { orderItems ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Delivered", color = BlinkitGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text(text = orderItems.joinToString(", "), fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                }
            }

            // Logout Button
            Button(
                onClick = {
                    ProfileManager.logout()
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFECEC)),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Text(text = "Logout", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
    }
}