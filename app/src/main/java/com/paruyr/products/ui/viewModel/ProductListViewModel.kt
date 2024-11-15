package com.paruyr.products.ui.viewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paruyr.products.model.Product
import com.paruyr.products.repository.IProductRepository
import com.paruyr.products.utils.JsonUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductListViewModel(
    application: Application,
    private val repository: IProductRepository
) : AndroidViewModel(application) {
    val showDialog = mutableStateOf(false)
    val newProductName = mutableStateOf("")
    val newProductDescription = mutableStateOf("")
    val newProductQuantity = mutableStateOf("")
    val newProductPrice = mutableStateOf("")

    val allProducts: Flow<List<Product>> = repository.allProducts

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
        resetInputFields()
    }

    fun loadProducts() = viewModelScope.launch {
        val products = JsonUtils.loadProductsFromJson(getApplication())
        repository.insertProducts(products)
    }

    fun updateProduct(product: Product) = viewModelScope.launch {
        repository.updateProduct(product)
    }

    private fun resetInputFields() {
        newProductName.value = ""
        newProductDescription.value = ""
        newProductQuantity.value = ""
        newProductPrice.value = ""
    }
}
