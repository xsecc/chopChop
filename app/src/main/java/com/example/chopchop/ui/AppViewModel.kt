package com.example.chopchop.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// Modelo de usuario simple
data class User(val username: String, val password: String)

// Modelo de producto
data class Product(var name: String, var quantity: Int = 1, var checked: Boolean = false)

// Modelo de lista
data class ShoppingList(
    val id: Int,
    var name: String,
    var favorite: Boolean = false,
    val products: MutableList<Product> = mutableListOf()
)

class AppViewModel : ViewModel() {
    // Usuario actual (null si no ha iniciado sesión)
    var currentUser = mutableStateOf<User?>(null)
        private set

    // Listas de compras
    var lists = mutableStateListOf<ShoppingList>()
        private set

    // Estado de login/registro
    var loginError = mutableStateOf<String?>(null)
    var registerError = mutableStateOf<String?>(null)

    // Simulación de usuarios registrados
    private val users = mutableListOf(User("test", "1234"))

    // --- Lógica de usuario ---
    fun login(username: String, password: String): Boolean {
        val user = users.find { it.username == username && it.password == password }
        return if (user != null) {
            currentUser.value = user
            loginError.value = null
            true
        } else {
            loginError.value = "Usuario o contraseña incorrectos"
            false
        }
    }

    fun register(username: String, password: String): Boolean {
        if (users.any { it.username == username }) {
            registerError.value = "El usuario ya existe"
            return false
        }
        val user = User(username, password)
        users.add(user)
        currentUser.value = user
        registerError.value = null
        return true
    }

    fun logout() {
        currentUser.value = null
    }

    // --- Lógica de listas ---
    fun addList(name: String) {
        val id = (lists.maxOfOrNull { it.id } ?: 0) + 1
        lists.add(ShoppingList(id, name))
    }

    fun removeList(id: Int) {
        lists.removeAll { it.id == id }
    }

    fun toggleFavorite(id: Int) {
        lists.find { it.id == id }?.let { it.favorite = !it.favorite }
    }

    fun editListName(id: Int, newName: String) {
        lists.find { it.id == id }?.name = newName
    }

    // --- Lógica de productos por lista ---
    fun addProductToList(listId: Int, product: Product) {
        lists.find { it.id == listId }?.products?.add(product)
    }

    fun removeProductFromList(listId: Int, productName: String) {
        lists.find { it.id == listId }?.products?.removeAll { it.name == productName }
    }

    fun editProductInList(listId: Int, oldName: String, newName: String, quantity: Int) {
        lists.find { it.id == listId }?.products?.find { it.name == oldName }?.apply {
            name = newName
            this.quantity = quantity
        }
    }

    fun toggleProductChecked(listId: Int, productName: String) {
        lists.find { it.id == listId }?.products?.find { it.name == productName }?.apply {
            checked = !checked
        }
    }
} 