package com.example.nclient.ui.signin

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nclient.ui.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SignInViewModel : ViewModel() {
    var email = MutableLiveData<String>("")
    var password = MutableLiveData<String>("")
    var checkDataValidation = MutableLiveData<String>("")
    var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    var showProgress = MutableLiveData<Boolean>(false)
    var signInState = MutableLiveData<String>("")
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var usersRef : DatabaseReference = database.getReference("Users")

    var userName = MutableLiveData<String>("")
    var userPhone = MutableLiveData<String>("")
    var userAddress = MutableLiveData<String>("")
    fun signIn(){
        if(checkDate()){
            showProgress.value = true
            mAuth.signInWithEmailAndPassword(email.value.toString(),password.value.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    usersRef.orderByChild("Email").equalTo(email.value.toString()).addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {

                            for(ds in snapshot.children){
                                userName.value = ds.child("Name").value.toString()
                                userPhone.value = ds.child("Phone").value.toString()
                                userAddress.value = ds.child("Address").value.toString()

                            }
                            signInState.value = "success"
                            showProgress.value = false
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })

                }else{
                    signInState.value = it.exception!!.message.toString()
                    showProgress.value = false
                }
            }
        }
    }

    private fun checkDate() : Boolean{

        if(email.value!!.isEmpty()){
            checkDataValidation.value = "Please enter email"
            return false
        }
        if(password.value!!.isEmpty()){
            checkDataValidation.value = "Please enter password"
            return false
        } else{
            return true
        }
    }
}