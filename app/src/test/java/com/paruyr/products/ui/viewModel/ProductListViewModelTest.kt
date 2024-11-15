package com.paruyr.products.ui.viewModel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.paruyr.products.model.Product
import com.paruyr.products.repository.IProductRepository
import com.paruyr.products.utils.JsonUtils
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var application: Application
    private lateinit var repository: IProductRepository
    private lateinit var viewModel: ProductListViewModel
    private lateinit var fakeProducts: List<Product>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        application = mockk()
        repository = mockk()
        fakeProducts = listOf(Product(1, "Product 1", "Description 1", 10.0, 5))
        coEvery { repository.allProducts } returns flowOf(fakeProducts)
        viewModel = ProductListViewModel(application, repository)
    }

    @Test
    fun `allProducts returns flow of products`() = runTest {
        // Act
        val result = viewModel.allProducts.firstOrNull()

        // Assert
        assertEquals("Should return the flow of products from the repository", fakeProducts, result)
        coVerify { repository.allProducts }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `insert calls repository insert and resets input fields`() = runTest {
        // Arrange
        val product = Product(1, "Product 1", "Description 1", 10.0, 5)
        coEvery { repository.insert(product) } just Runs

        // Act
        viewModel.insert(product)
        advanceUntilIdle()
        // Assert
        coVerify(exactly = 1) { repository.insert(product) }
        assertTrue(
            "Input fields should be reset after insertion",
            viewModel.newProductName.value.isEmpty() &&
                    viewModel.newProductDescription.value.isEmpty() &&
                    viewModel.newProductQuantity.value.isEmpty() &&
                    viewModel.newProductPrice.value.isEmpty()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loadProducts calls JsonUtils and repository insertProducts`() = runTest {
        // Arrange
        val fakeProducts = listOf(Product(1, "Product 1", "Description 1", 10.0, 5))
        mockkObject(JsonUtils)
        every { JsonUtils.loadProductsFromJson(application) } returns fakeProducts
        coEvery { repository.insertProducts(fakeProducts) } just Runs

        // Act
        viewModel.loadProducts()
        advanceUntilIdle()

        // Assert
        coVerify { JsonUtils.loadProductsFromJson(application) }
        coVerify { repository.insertProducts(fakeProducts) }
        unmockkObject(JsonUtils)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `updateProduct calls repository updateProduct`() = runTest {
        // Arrange
        val product = Product(1, "Product 1", "Description 1", 10.0, 5)
        coEvery { repository.updateProduct(product) } just Runs

        // Act
        viewModel.updateProduct(product)
        advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) { repository.updateProduct(product) }
    }
}
