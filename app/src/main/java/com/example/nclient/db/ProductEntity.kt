package com.example.nclient.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(@PrimaryKey var productId : String,
                         @ColumnInfo(name = "productName") var productName:String,
                         @ColumnInfo(name = "productImage") var productImage:String,
                         @ColumnInfo(name = "productPrice") var productPrice:String,
                         @ColumnInfo(name = "productQuantity") var productQuantity:String)