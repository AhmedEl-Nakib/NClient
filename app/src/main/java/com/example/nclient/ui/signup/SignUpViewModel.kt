package com.example.nclient.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpViewModel : ViewModel() {

    var name = MutableLiveData<String>("")
    var email = MutableLiveData<String>("")
    var password = MutableLiveData<String>("")
    var phone = MutableLiveData<String>("")
    var address = MutableLiveData<String>("")

    var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var usersRef : DatabaseReference = database.getReference("Users")
    var showProgress = MutableLiveData<Boolean>(false)

    var signUpState = MutableLiveData<String>("")
    var checkDataValidation = MutableLiveData<String>("")

    fun signUp(){

        if(checkDate()) {
            showProgress.value = true
            mAuth.createUserWithEmailAndPassword(
                    email.value.toString(),
                    password.value.toString()
            )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            var map = HashMap<String, String>()
                            map["Name"] = name.value.toString()
                            map["Email"] = email.value.toString()
                            map["Phone"] = phone.value.toString()
                            map["Address"] = address.value.toString()
                            map["UID"] = mAuth.currentUser!!.uid.toString()
                            usersRef.push().setValue(map)
                            signUpState.value = "Account Created Successfully"
                            showProgress.value = false

                        } else {
                            signUpState.value = it.exception!!.message.toString()
                            showProgress.value = false
                        }
                    }
        }
    }

    private fun checkDate() : Boolean{
        if(name.value!!.isEmpty()){
            checkDataValidation.value = "Please enter name"
            return false
        }
        if(email.value!!.isEmpty()){
            checkDataValidation.value = "Please enter email"
            return false
        }
        if(password.value!!.isEmpty()){
            checkDataValidation.value = "Please enter password"
            return false
        }
        if(phone.value!!.isEmpty()){
            checkDataValidation.value = "Please enter phone"
            return false
        }
        if(address.value!!.isEmpty()){
            checkDataValidation.value = "Please enter address"
            return false
        }else{
            return true
        }
    }
}