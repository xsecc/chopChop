import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Icon

@Composable
fun AddProductScreen(navController: NavController) {
    var productName by remember { mutableStateOf(TextFieldValue("")) }
    var products by remember {
        mutableStateOf(
            listOf(
                ProductItem("Azúcar", 1),
                ProductItem("Atún", 1),
                ProductItem("Mayonesa", 1),
                ProductItem("Huevos", 2),
                ProductItem("Papas", 1),
                ProductItem("Leche", 1),
                ProductItem("Mostaza", 1),
                ProductItem("Aceite", 1)
            )
        )
    }
    var editingProduct by remember { mutableStateOf<ProductItem?>(null) }
    var editName by remember { mutableStateOf(TextFieldValue("")) }
    var editQuantity by remember { mutableStateOf(TextFieldValue("1")) }
    var showDialog by remember { mutableStateOf(false) }

    fun openEditDialog(product: ProductItem) {
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
                    Text("Información", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(
                        "Hecho",
                        color = Color(0xFF4CAF50),
                        modifier = Modifier
                            .clickable {
                                val newName = editName.text.trim()
                                val newQuantity = editQuantity.text.toIntOrNull() ?: 1
                                if (newName.isNotEmpty()) {
                                    products = products.map {
                                        if (it == editingProduct) it.copy(name = newName, quantity = newQuantity) else it
                                    }
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
                        products = products.filter { it != editingProduct }
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Eliminar", color = Color.White)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222222))
            .padding(16.dp)
    ) {
        // Barra superior con campo para agregar producto
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4CAF50), shape = CircleShape)
                .padding(8.dp)
        ) {
            TextField(
                value = productName,
                onValueChange = { productName = it },
                placeholder = { Text("Agregar producto") },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    val name = productName.text.trim()
                    if (name.isNotEmpty() && products.none { it.name.equals(name, true) }) {
                        products = products + ProductItem(name, 1)
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
        // Mostrar imagen y mensaje si no hay productos
        if (products.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
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
                items(products) { product ->
                    ProductRow(
                        product = product,
                        onAdd = {
                            products = products.map {
                                if (it.name == product.name) it.copy(quantity = it.quantity + 1) else it
                            }
                        },
                        onRemove = {
                            if (product.quantity > 1) {
                                products = products.map {
                                    if (it.name == product.name) it.copy(quantity = it.quantity - 1) else it
                                }
                            }
                        },
                        onDelete = {
                            products = products.filter { it.name != product.name }
                        },
                        onEdit = {
                            openEditDialog(product)
                        },
                        onCheck = {
                            products = products.map {
                                if (it.name == product.name) it.copy(checked = !it.checked) else it
                            }
                        }
                    )
                }
            }
        }
        // Botón HECHO
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(top = 16.dp)
        ) {
            Text("✔ HECHO", color = Color.White, fontSize = 20.sp)
        }
    }
}

data class ProductItem(val name: String, val quantity: Int, val checked: Boolean = false)

@Composable
fun ProductRow(
    product: ProductItem,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onCheck: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onEdit() }
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
                    onCheck()
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
                    .clickable { onRemove() },
                contentAlignment = Alignment.Center
            ) {
                Text(product.quantity.toString(), color = Color.Black, fontSize = 16.sp)
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        // Botón eliminar
        IconButton(onClick = { onDelete() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Eliminar",
                tint = Color.Red
            )
        }
    }
} 