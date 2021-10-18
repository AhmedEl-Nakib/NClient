package com.example.nclient.ui.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nclient.ui.favorite.FavoriteModel
import com.google.firebase.database.*

class NotificationViewModel : ViewModel() {


    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var notificationsRef : DatabaseReference = database.getReference("Notifications")
    var list = ArrayList<NotificationModel>()
    var notificationsList = MutableLiveData<ArrayList<NotificationModel>>()

    fun getMyNotifications(UID:String){
        list.clear()
        notificationsRef.child(UID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    var statues = ds.child("statues").value.toString()
                    var orderProductNames = ds.child("orderProductNames").value.toString()
                    list.add(NotificationModel(statues,orderProductNames))
                }
                notificationsList.value = list
            }

        })
    }

}