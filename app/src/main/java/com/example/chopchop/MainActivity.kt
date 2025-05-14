package com.example.chopchop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chopchop.ui.theme.ChopChopTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chopchop.ui.LoginScreen
import com.example.chopchop.ui.RegisterScreen
import com.example.chopchop.ui.ListsScreen
import com.example.chopchop.ui.FavoritesScreen
import com.example.chopchop.ui.SettingsScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chopchop.ui.AppViewModel
import com.example.chopchop.ui.ProductsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChopChopTheme {
                val navController = rememberNavController()
                val appViewModel: AppViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginScreen(
                                appViewModel = appViewModel,
                                onLogin = { navController.navigate("listas") },
                                onRegister = { navController.navigate("register") }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                appViewModel = appViewModel,
                                onRegister = { navController.navigate("listas") }
                            )
                        }
                        composable("listas") {
                            ListsScreen(navController, appViewModel)
                        }
                        composable("favoritos") {
                            FavoritesScreen(navController, appViewModel)
                        }
                        composable("ajustes") {
                            SettingsScreen(navController, appViewModel)
                        }
                        composable("productos/{listId}") { backStackEntry ->
                            val listId = backStackEntry.arguments?.getString("listId")?.toIntOrNull() ?: return@composable
                            ProductsScreen(listId = listId, navController = navController, appViewModel = appViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChopChopTheme {
        Greeting("Android")
    }
}