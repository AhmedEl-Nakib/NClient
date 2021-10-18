package com.example.nclient.ui.cart

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.nclient.db.AppDatabase
import com.example.nclient.db.ProductEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    var cartData = MutableLiveData<List<ProductEntity>>()

    fun getAllCartData(context:Context){
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "productsDB").build()
        viewModelScope.async {
            cartData.value = db.productDao().getAllProductsData()
        }

    }

}