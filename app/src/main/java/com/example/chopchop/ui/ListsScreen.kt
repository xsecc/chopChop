package com.example.chopchop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.chopchop.R
import androidx.navigation.NavController

@Composable
fun ListsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "LISTAS",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color(0xFF222222)
        )
        Spacer(modifier = Modifier.height(64.dp))
        // Estado vacío
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_empty_list),
                contentDescription = "Lista vacía",
                tint = Color(0xFFBDBDBD),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Aún no hay listas\npara mostrar.\n¡Crea una!",
                color = Color(0xFFBDBDBD),
                fontSize = 16.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        // Botón flotante para agregar lista
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {},
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar lista")
            }
        }
        // Barra de navegación inferior
        BottomNavBar(selected = 0, navController = navController)
    }
} 