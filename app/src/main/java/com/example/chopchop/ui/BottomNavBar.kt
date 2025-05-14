package com.example.chopchop.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavBar(selected: Int, navController: NavController) {
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 8.dp
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Listas") },
            label = { Text("Listas") },
            selected = selected == 0,
            onClick = {
                if (selected != 0) {
                    navController.navigate("listas") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            selectedContentColor = Color(0xFF4CAF50),
            unselectedContentColor = Color.Gray
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            selected = selected == 1,
            onClick = {
                if (selected != 1) {
                    navController.navigate("favoritos") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            selectedContentColor = Color(0xFF4CAF50),
            unselectedContentColor = Color.Gray
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
            label = { Text("Ajustes") },
            selected = selected == 2,
            onClick = {
                if (selected != 2) {
                    navController.navigate("ajustes") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            selectedContentColor = Color(0xFF4CAF50),
            unselectedContentColor = Color.Gray
        )
    }
} 