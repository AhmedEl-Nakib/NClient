package com.example.nclient.ui.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nclient.R
import com.example.nclient.ui.favorite.FavoriteModel

class NotificationAdapter(private var dataSet: ArrayList<NotificationModel>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var statues: TextView
        lateinit var orderProductNames: TextView
        init {
            statues = view.findViewById(R.id.statuesId)
            orderProductNames = view.findViewById(R.id.orderProductNamesId)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.notification_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.statues.text = dataSet[position].statues
        viewHolder.orderProductNames.text = dataSet[position].orderProductNames

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() =dataSet.size

}