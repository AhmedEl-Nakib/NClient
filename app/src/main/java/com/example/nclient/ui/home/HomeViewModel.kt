package com.example.nclient.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class HomeViewModel : ViewModel() {
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var productsRef : DatabaseReference = database.getReference("Products")
    var list = ArrayList<HomeModel>()
    var productsList = MutableLiveData<ArrayList<HomeModel>>()

    var searchWord = MutableLiveData<String>("")

    fun getAllProducts(){
        list.clear()
        productsRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    var productName = ds.child("ProductName").value.toString()
                    var productPrice = ds.child("ProductPrice").value.toString()
                    var downloadURL = ds.child("downloadURL").value.toString()
                    var productDescription = ds.child("ProductDescription").value.toString()
                    var productID = ds.child("ProductID").value.toString()
                    list.add(HomeModel(downloadURL,productName,productPrice,productDescription,productID))
                }
                productsList.value = list
            }

        })
    }
}