package com.example.chopchop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.chopchop.R

@Composable
fun RegisterScreen(appViewModel: AppViewModel, onRegister: () -> Unit = {}) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val registerError = appViewModel.registerError.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.register_illustration),
            contentDescription = "Ilustración de registro",
            modifier = Modifier.size(160.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "CHOP CHOP",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "REGISTRO",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        if (registerError != null) {
            Text(registerError, color = Color.Red, modifier = Modifier.padding(8.dp))
        }
        Button(
            onClick = {
                if (password == confirmPassword && password.isNotEmpty()) {
                    if (appViewModel.register(email, password)) {
                        onRegister()
                    }
                } else {
                    appViewModel.registerError.value = "Las contraseñas no coinciden"
                }
            },
            modifier = Modifier.fillMaxWidth().padding(32.dp)
        ) {
            Text("Registrarse")
        }
    }
} 