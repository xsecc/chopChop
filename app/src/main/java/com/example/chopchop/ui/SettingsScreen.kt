package com.example.chopchop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController, appViewModel: AppViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "¡Hola, Juan!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 16.dp, start = 24.dp),
        )
        Divider()
        SettingOption(icon = Icons.Default.Brightness4, text = "Activar modo oscuro")
        SettingOption(icon = Icons.Default.Lock, text = "Cambiar contraseña")
        SettingOption(icon = Icons.Default.Info, text = "Ver créditos")
        SettingOption(icon = Icons.Default.MailOutline, text = "Enviar comentarios")
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /* Acción de cerrar sesión */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(48.dp)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar Sesión", color = Color.White, fontSize = 18.sp)
        }
        BottomNavBar(selected = 2, navController = navController)
    }
}

@Composable
fun SettingOption(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Acción de la opción */ }
            .padding(horizontal = 24.dp, vertical = 18.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, color = Color.Black, fontSize = 18.sp)
    }
} 