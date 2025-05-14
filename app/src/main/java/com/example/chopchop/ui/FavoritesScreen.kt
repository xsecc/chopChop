import androidx.compose.foundation.background
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

@Composable
fun FavoritesScreen(navController: NavController) {
    var favoriteLists by remember {
        mutableStateOf(
            listOf(
                // Ejemplo de listas favoritas
                FavoriteListItem("Navidad", false),
                FavoriteListItem("Lista familiar", true)
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "LISTAS FAVORITAS",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 16.dp),
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
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        elevation = 4.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        if (list.active) Color(0xFF4CAF50) else Color(0xFFFFA726),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = if (list.active) Color.White else Color.White.copy(alpha = 0.7f),
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
                                    text = if (list.active) "Activa" else "Inactiva",
                                    color = if (list.active) Color(0xFF4CAF50) else Color.Gray,
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
            onClick = { /* Navegar a Listas */ },
            selectedContentColor = Color(0xFF4CAF50),
            unselectedContentColor = Color.Gray
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            selected = selected == 1,
            onClick = { /* Navegar a Favoritos */ },
            selectedContentColor = Color(0xFF4CAF50),
            unselectedContentColor = Color.Gray
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
            label = { Text("Ajustes") },
            selected = selected == 2,
            onClick = { /* Navegar a Ajustes */ },
            selectedContentColor = Color(0xFF4CAF50),
            unselectedContentColor = Color.Gray
        )
    }
} 