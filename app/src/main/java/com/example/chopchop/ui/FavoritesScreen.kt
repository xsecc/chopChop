package com.example.chopchop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chopchop.ui.BottomNavBar

@Composable
fun FavoritesScreen(navController: NavController, appViewModel: AppViewModel) {
    val favoriteLists = appViewModel.lists.filter { it.favorite }
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Text(
            text = "LISTAS FAVORITAS",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        if (favoriteLists.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Aún no hay favoritas listas para mostrar. ¡Añade una!",
                        color = Color.LightGray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Column(modifier = Modifier.weight(1f)) {
                favoriteLists.forEach { list ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { navController.navigate("productos/${list.id}") },
                        elevation = 4.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(Color(0xFF4CAF50), shape = CircleShape)
                                    .clickable { appViewModel.toggleFavorite(list.id) },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = list.name,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Favorita",
                                    color = Color(0xFF4CAF50),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
        BottomNavBar(selected = 1, navController = navController)
    }
}

data class FavoriteListItem(val name: String, val active: Boolean) 