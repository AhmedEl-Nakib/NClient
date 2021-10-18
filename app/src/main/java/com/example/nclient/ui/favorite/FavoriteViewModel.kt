package com.example.nclient.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nclient.ui.home.HomeModel
import com.google.firebase.database.*

class FavoriteViewModel : ViewModel() {

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var favoritesRef : DatabaseReference = database.getReference("Favorites")
    var list = ArrayList<FavoriteModel>()
    var favoritesList = MutableLiveData<ArrayList<FavoriteModel>>()

    var searchWord = MutableLiveData<String>("")

    fun getAllFavorites(UID:String){
        list.clear()
        favoritesRef.child(UID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    var productName = ds.child("ProductName").value.toString()
                    var productPrice = ds.child("ProductPrice").value.toString()
                    var downloadURL = ds.child("ProductImage").value.toString()
                    var productID = ds.child("ProductId").value.toString()
                    var productDescription = ds.child("ProductDescription").value.toString()
                    list.add(FavoriteModel(productName,downloadURL,productPrice,productID,productDescription))
                }
                favoritesList.value = list
            }

        })
    }

    fun deleteFromFavorite(productId:String , UID:String){
        favoritesRef.child(UID).orderByChild("ProductId").equalTo(productId).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    ds.ref.removeValue()
                }
                getAllFavorites(UID)
            }

        })
    }

}