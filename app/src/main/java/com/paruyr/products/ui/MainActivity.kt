package com.paruyr.products.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.paruyr.products.ui.view.ProductListScreen
import com.paruyr.products.ui.theme.ProductsListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductsListApp()
        }
    }

    @Composable
    fun ProductsListApp() {
        ProductsListTheme {
            ProductListScreen()
        }
    }
}
