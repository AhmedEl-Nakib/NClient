package com.example.nclient.ui.productDetails

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.nclient.db.AppDatabase
import com.example.nclient.db.ProductEntity
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ProductDetailsViewModel : ViewModel() {
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var favoritesRef : DatabaseReference = database.getReference("Favorites")

    var UID = MutableLiveData<String>("")
    var productId = MutableLiveData<String>("")
    var addFavoriteStatus = MutableLiveData<String>("")
    var productName =MutableLiveData<String>("")
    var productPrice =MutableLiveData<String>("")
    var productImage =MutableLiveData<String>("")
    var productDescription =MutableLiveData<String>("")
    var addCartStatus = MutableLiveData<String>("")

    fun addToFavorite(){
        favoritesRef.child(UID.value!!).orderByChild("ProductId").equalTo(productId.value!!)
                .addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        addFavoriteStatus.value = "Product already in your favorites"
                    }else{
                        var map = HashMap<String, String>()
                        map["ProductName"] = productName.value!!
                        map["ProductPrice"] = productPrice.value!!
                        map["ProductImage"] = productImage.value!!
                        map["ProductId"] = productId.value!!
                        map["ProductDescription"] = productDescription.value!!
                        favoritesRef.child(UID.value!!).push().setValue(map)
                        addFavoriteStatus.value = "Product Added Successfully"
                    }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addToCart(context: Context){
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "productsDB").build()
        try {
            CoroutineScope(Dispatchers.IO).launch {

                if(db.productDao().getProductData(productId.value!!).size == 1){
                    withContext(Dispatchers.Main) {
                        addCartStatus.value = "Product Already Added To Cart"
                    }
                }else {
                    db.productDao().insertProduct(
                        ProductEntity(
                            productId.value!!,
                            productName.value!!,
                            productImage.value!!,
                            productPrice.value!!,
                            "1"
                        )
                    )
                    withContext(Dispatchers.Main) {
                        addCartStatus.value = "Product Added Successfully"
                    }
                }
            }
        }catch (e: Exception){
            addCartStatus.value = "Exception ${e.message.toString()}"

        }
    }
}