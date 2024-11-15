package com.paruyr.products.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paruyr.products.model.Product
import com.paruyr.products.ui.viewModel.ProductListViewModel

@Composable
fun ProductListItem(product: Product, viewModel: ProductListViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = product.name,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Qty: ${product.quantity}"
        )
        Button(
            onClick = { viewModel.updateProduct(product.copy(quantity = product.quantity + 1)) }
        ) {
            Text("Add")
        }
    }
}