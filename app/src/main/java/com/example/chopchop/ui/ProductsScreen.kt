package com.example.chopchop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProductsScreen(listId: Int, navController: NavController, appViewModel: AppViewModel) {
    val list = appViewModel.lists.find { it.id == listId }
    var productName by remember { mutableStateOf(TextFieldValue("")) }
    var editingProduct by remember { mutableStateOf<Product?>(null) }
    var editName by remember { mutableStateOf(TextFieldValue("")) }
    var editQuantity by remember { mutableStateOf(TextFieldValue("1")) }
    var showDialog by remember { mutableStateOf(false) }

    fun openEditDialog(product: Product) {
        editingProduct = product
        editName = TextFieldValue(product.name)
        editQuantity = TextFieldValue(product.quantity.toString())
        showDialog = true
    }

    if (showDialog && editingProduct != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Editar producto", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(
                        "Hecho",
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.clickable {
                            val newName = editName.text.trim()
                            val newQuantity = editQuantity.text.toIntOrNull() ?: 1
                            if (newName.isNotEmpty() && list != null) {
                                appViewModel.editProductInList(list.id, editingProduct!!.name, newName, newQuantity)
                                showDialog = false
                            }
                        }
                    )
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editQuantity,
                        onValueChange = {
                            if (it.text.all { c -> c.isDigit() } && it.text.isNotEmpty()) editQuantity = it
                        },
                        label = { Text("Cantidad") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {},
            dismissButton = {
                Button(
                    onClick = {
                        if (list != null) {
                            appViewModel.removeProductFromList(list.id, editingProduct!!.name)
                        }
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Eliminar", color = Color.White)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF222222)).padding(16.dp)
    ) {
        Text(
            text = list?.name ?: "Lista",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Agregar producto") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    val name = productName.text.trim()
                    if (name.isNotEmpty() && list != null && list.products.none { it.name.equals(name, true) }) {
                        appViewModel.addProductToList(list.id, Product(name))
                        productName = TextFieldValue("")
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(36.dp)
            ) {
                Text("+", color = Color.White, fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (list == null || list.products.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Sin productos",
                    modifier = Modifier.size(120.dp),
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "¡No hay productos!\nAgrega uno para empezar.",
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(list.products) { product ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        // Botón círculo para check
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    if (product.checked) Color(0xFF4CAF50) else Color(0xFFFFA726),
                                    shape = CircleShape
                                )
                                .clickable {
                                    appViewModel.toggleProductChecked(list.id, product.name)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (product.checked) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Checked",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        // Nombre del producto
                        Text(
                            product.name,
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f),
                            textDecoration = if (product.checked) TextDecoration.LineThrough else null
                        )
                        // Cantidad
                        if (product.quantity > 1) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(Color(0xFFB2FF59), shape = CircleShape)
                                    .clickable {
                                        appViewModel.editProductInList(list.id, product.name, product.name, product.quantity - 1)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(product.quantity.toString(), color = Color.Black, fontSize = 16.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        // Botón editar
                        IconButton(onClick = { openEditDialog(product) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Editar/Eliminar",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(top = 16.dp)
        ) {
            Text("✔ HECHO", color = Color.White, fontSize = 20.sp)
        }
    }
} 