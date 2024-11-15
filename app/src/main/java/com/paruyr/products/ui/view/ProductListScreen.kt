package com.paruyr.products.ui.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.paruyr.products.model.Product
import com.paruyr.products.ui.viewModel.ProductListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductListScreen(viewModel: ProductListViewModel = koinViewModel()) {
    val products by viewModel.allProducts.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        items(items = products) { product ->
            ProductListItem(product, viewModel)
        }
        item {
            Button(onClick = { viewModel.showDialog.value = true }) {
                Text("Add New Product")
            }
            LoadDataButton(viewModel)
        }
    }

    if (viewModel.showDialog.value) {
        NewProductDialog(
            onDismiss = { viewModel.showDialog.value = false },
            onSubmit = { name, description, quantity, price ->
                viewModel.insert(Product(0L, name, description, price.toDouble(), quantity.toInt()))
            },
            productName = viewModel.newProductName.value,
            onProductNameChange = { viewModel.newProductName.value = it },
            productDescription = viewModel.newProductDescription.value,
            onProductDescriptionChange = { viewModel.newProductDescription.value = it },
            productQuantity = viewModel.newProductQuantity.value,
            onProductQuantityChange = { viewModel.newProductQuantity.value = it },
            productPrice = viewModel.newProductPrice.value,
            onProductPriceChange = { viewModel.newProductPrice.value = it }
        )
    }
}

@Composable
fun LoadDataButton(viewModel: ProductListViewModel = koinViewModel()) {
    Button(onClick = {
        viewModel.loadProducts()
    }) {
        Text("Load Products")
    }
}

@Composable
fun NewProductDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String, String, Double) -> Unit,
    productName: String,
    onProductNameChange: (String) -> Unit,
    productDescription: String,
    onProductDescriptionChange: (String) -> Unit,
    productQuantity: String,
    onProductQuantityChange: (String) -> Unit,
    productPrice: String,
    onProductPriceChange: (String) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Product") },
        text = {
            Column {
                OutlinedTextField(
                    value = productName,
                    onValueChange = onProductNameChange,
                    label = { Text("Product Name") }
                )
                OutlinedTextField(
                    value = productDescription,
                    onValueChange = onProductDescriptionChange,
                    label = { Text("Description") }
                )
                OutlinedTextField(
                    value = productQuantity,
                    onValueChange = onProductQuantityChange,
                    label = { Text("Quantity") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = productPrice,
                    onValueChange = onProductPriceChange,
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (productName.isNotBlank() && productDescription.isNotBlank() && productQuantity.isNotBlank() && productPrice.isNotBlank()) {
                    val quantity = productQuantity.toIntOrNull() ?: 0
                    val price = productPrice.toDoubleOrNull() ?: 0.0
                    if (quantity > 0 && price > 0.0) {
                        onSubmit(
                            productName,
                            productDescription,
                            productQuantity,
                            productPrice.toDouble()
                        )
                    }
                    onDismiss()
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
