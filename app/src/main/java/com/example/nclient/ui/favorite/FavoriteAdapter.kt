package com.example.nclient.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nclient.R
import com.example.nclient.ui.home.HomeModel
import com.example.nclient.ui.home.OnHomeItemClick
import com.squareup.picasso.Picasso

class FavoriteAdapter(private var dataSet: ArrayList<FavoriteModel>,var onFavoriteItemClick: OnFavoriteItemClick) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var title: TextView
        lateinit var price: TextView
        lateinit var img: ImageView
        lateinit var unCheck: ImageView
        init {
            title = view.findViewById(R.id.titleId)
            price = view.findViewById(R.id.priceId)
            img = view.findViewById(R.id.imgId)
            unCheck = view.findViewById(R.id.unCheckId)
        }
    }

    fun filterData( newList :ArrayList<FavoriteModel> ){
        this.dataSet = newList
        notifyDataSetChanged() // refresh
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.favorite_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = dataSet[position].title
        viewHolder.price.text = dataSet[position].price
        Picasso.get().load(dataSet[position].img).into(viewHolder.img)
        viewHolder.itemView.setOnClickListener {
            onFavoriteItemClick.onItemClicked(dataSet[position])
        }
        viewHolder.unCheck.setOnClickListener {
            onFavoriteItemClick.onFavoriteUnCheckClicked(
                    dataSet[position].productId
            )
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}