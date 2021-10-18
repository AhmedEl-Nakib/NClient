package com.example.nclient.ui.placeOrder

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.nclient.db.AppDatabase
import com.example.nclient.db.ProductEntity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PlaceOrderViewModel : ViewModel() {

    var cartData = MutableLiveData<List<ProductEntity>>()

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var orderUsersRef : DatabaseReference = database.getReference("OrderUsers")
    var orderDetailsRef : DatabaseReference = database.getReference("OrderDetails")
    var submitOrderStatus = MutableLiveData<String>("")

    fun getAllCartData(context: Context){
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "productsDB").build()
        viewModelScope.async {
            cartData.value = db.productDao().getAllProductsData()
        }

    }

    fun placeOrder(
        context: Context,
        userName: String,
        userPhone: String,
        userAddress: String,
        UID: String
    ){
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "productsDB").build()
        if(cartData.value!!.isNotEmpty()){
            var ID = orderDetailsRef.push().key.toString()
            var userMap = HashMap<String ,String>()
            userMap["OrderUserName"] = userName
            userMap["OrderUserPhone"] = userPhone
            userMap["OrderUserAddress"] = userAddress
            userMap["OrderID"] = ID
            userMap["UID"] = UID
            orderUsersRef.push().setValue(userMap).addOnCompleteListener {
                if(it.isSuccessful){
                    var productsMap = HashMap<String ,String>()

                    CoroutineScope(Dispatchers.IO).async {
                        for (item in db.productDao().getAllProductsData()) {
                            productsMap["ProductName"] = item.productName
                            productsMap["ProductPrice"] = item.productPrice
                            productsMap["ProductQuantity"] = item.productQuantity
                            orderDetailsRef.child(ID).push().setValue(productsMap)
                        }
                        withContext(Dispatchers.Main){
                            submitOrderStatus.value = "Order Requested Successfully"
                            withContext(Dispatchers.IO){
                                db.productDao().deleteAll()
                            }
                        }

                    }
                }
            }
        }
    }
}