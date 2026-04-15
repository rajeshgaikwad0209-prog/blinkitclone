package com.example.blinkitclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                // Logic: If user is logged in, skip Login and go to Home
                val startScreen = if (ProfileManager.isLoggedIn) "home" else "login"

                NavHost(
                    navController = navController,
                    startDestination = startScreen
                ) {
                    // 1. Login Screen
                    composable("login") {
                        LoginScreen(onLoginSuccess = {
                            navController.navigate("home") {
                                // Clears login screen from the backstack
                                popUpTo("login") { inclusive = true }
                            }
                        })
                    }

                    // 2. Home Screen
                    composable("home") {
                        BlinkitHomeScreen(
                            onViewCart = {
                                navController.navigate("cart") {
                                    launchSingleTop = true
                                }
                            },
                            onProfileClick = {
                                navController.navigate("profile") {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    // 3. Cart Screen (Checkout)
                    composable("cart") {
                        ViewCartScreen(onBack = {
                            navController.popBackStack()
                        })
                    }

                    // 4. Profile & History Screen
                    composable("profile") {
                        ProfileScreen(
                            onLogout = {
                                navController.navigate("login") {
                                    // Clears home and profile when logging out
                                    popUpTo("home") { inclusive = true }
                                }
                            },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}