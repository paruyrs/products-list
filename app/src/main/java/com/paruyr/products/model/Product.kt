package com.paruyr.products.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    var name: String,
    var description: String,
    var price: Double,
    var quantity: Int
)
