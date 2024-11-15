package com.paruyr.products.repository

import androidx.annotation.WorkerThread
import com.paruyr.products.model.Product
import com.paruyr.products.room.ProductDao
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    val allProducts: Flow<List<Product>>
    suspend fun insert(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun insertProducts(products: List<Product>)
}

class ProductRepository(private val productDao: ProductDao) : IProductRepository {
    override val allProducts: Flow<List<Product>> = productDao.getAllProducts()

    @WorkerThread
    override suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    @WorkerThread
    override suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    @WorkerThread
    override suspend fun insertProducts(products: List<Product>) {
        productDao.insertAll(products) // Correct method to insert a list of products
    }
}

