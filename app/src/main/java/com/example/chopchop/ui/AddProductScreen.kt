import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

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
                Image(
                    painter = painterResource(id = R.drawable.ic_empty_products),
                    contentDescription = "Sin productos",
                    modifier = Modifier.size(120.dp)
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

data class ProductItem(val name: String, val quantity: Int)

@Composable
fun ProductRow(
    product: ProductItem,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Botón +
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(Color(0xFF4CAF50), shape = CircleShape)
                .clickable { onAdd() },
            contentAlignment = Alignment.Center
        ) {
            Text("+", color = Color.White, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        // Nombre del producto
        Text(product.name, color = Color.White, fontSize = 18.sp, modifier = Modifier.weight(1f))
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