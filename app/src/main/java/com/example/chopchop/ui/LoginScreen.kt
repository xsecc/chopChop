package com.example.chopchop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.chopchop.R
import androidx.compose.material3.TextButton

@Composable
fun LoginScreen(appViewModel: AppViewModel, onLogin: () -> Unit = {}, onRegister: () -> Unit = {}) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginError = appViewModel.loginError.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de login (puedes cambiar el recurso por uno propio)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.login_illustration),
            contentDescription = "Ilustración de inicio de sesión",
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
            text = "INICIO DE SESION",
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
        if (loginError != null) {
            Text(loginError, color = Color.Red, modifier = Modifier.padding(8.dp))
        }
        Button(
            onClick = {
                if (appViewModel.login(email, password)) {
                    onLogin()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(32.dp)
        ) {
            Text("Iniciar sesión")
        }
        TextButton(onClick = onRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
} 