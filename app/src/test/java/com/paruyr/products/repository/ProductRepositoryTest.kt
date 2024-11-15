package com.paruyr.products.repository

import com.paruyr.products.model.Product
import com.paruyr.products.room.ProductDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class ProductRepositoryTest {
    private lateinit var productDao: ProductDao
    private lateinit var productRepository: ProductRepository

    @Before
    fun setup() {
        productDao = mockk() // Create a mock DAO
        val fakeProducts = listOf(Product(1, "Product 1", "Description 1", 10.0, 5))
        val fakeFlow = flowOf(fakeProducts)

        // Correctly mock the behavior of getAllProducts()
        coEvery { productDao.getAllProducts() } returns fakeFlow

        productRepository =
            ProductRepository(productDao) // Inject the mocked DAO into the repository
    }

    @Test
    fun `getAllProducts returns flow of products`() = runTest {
        // Arrange
        val fakeProducts = listOf(Product(1, "Product 1", "Description 1", 10.0, 5))

        // Act
        val result = productRepository.allProducts.firstOrNull()

        // Assert
        assertNotNull("Result from the Flow should not be null", result)
        assertEquals(
            "Should return flow of products from the DAO",
            fakeProducts,
            result
        )

        // Verify that getAllProducts was called
        coVerify { productDao.getAllProducts() }
    }


    @Test
    fun `insert calls insert on DAO`() = runTest {
        val product = Product(1, "Product 1", "Description 1", 10.0, 5)
        coEvery { productDao.insert(product) } returns Unit

        productRepository.insert(product)

        coVerify(exactly = 1) { productDao.insert(product) }
    }

    @Test
    fun `updateProduct calls update on DAO`() = runTest {
        val product = Product(1, "Product 1", "Description 1", 10.0, 5)
        coEvery { productDao.updateProduct(product) } returns Unit

        productRepository.updateProduct(product)

        coVerify(exactly = 1) { productDao.updateProduct(product) }
    }

    @Test
    fun `insertProducts calls insertAll on DAO with list of products`() = runTest {
        val products = listOf(
            Product(1, "Product 1", "Description 1", 10.0, 5),
            Product(2, "Product 2", "Description 2", 20.0, 10)
        )
        coEvery { productDao.insertAll(products) } returns Unit

        productRepository.insertProducts(products)

        coVerify(exactly = 1) { productDao.insertAll(products) }
    }
}
