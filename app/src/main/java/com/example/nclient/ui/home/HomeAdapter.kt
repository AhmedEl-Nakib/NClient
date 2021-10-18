package com.example.nclient.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nclient.R
import com.squareup.picasso.Picasso

class HomeAdapter(private var dataSet: ArrayList<HomeModel> ,var onHomeItemClick: OnHomeItemClick) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var title: TextView
        lateinit var price: TextView
        lateinit var img: ImageView
        init {
            title = view.findViewById(R.id.titleId)
            price = view.findViewById(R.id.priceId)
            img = view.findViewById(R.id.proImage)
        }
    }

    fun filterData( newList :ArrayList<HomeModel> ){
        this.dataSet = newList
        notifyDataSetChanged() // refresh
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.home_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.setOnClickListener {
            onHomeItemClick.onItemClicked(dataSet[position])
        }
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.title.text = dataSet[position].title
        viewHolder.price.text = dataSet[position].price
        Picasso.get().load(dataSet[position].img).into(viewHolder.img)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}