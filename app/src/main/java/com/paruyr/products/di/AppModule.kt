package com.paruyr.products.di

import com.paruyr.products.repository.IProductRepository
import com.paruyr.products.repository.ProductRepository
import com.paruyr.products.room.ProductDatabase
import com.paruyr.products.ui.viewModel.ProductListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Provide a single instance of ProductDatabase
    single {
        ProductDatabase.getDatabase(androidApplication())
    }

    // Provide a single instance of ProductDao
    single {
        get<ProductDatabase>().productDao()
    }

    // Bind the interface to its implementation
    single<IProductRepository> {
        ProductRepository(productDao = get())
    }

    // ViewModel injection
    viewModel {
        ProductListViewModel(application = androidApplication(), repository = get())
    }
}

