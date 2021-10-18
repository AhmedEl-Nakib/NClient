package com.example.nclient.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.nclient.R
import com.example.nclient.db.AppDatabase
import com.example.nclient.db.ProductEntity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartAdapter(var dataSet: List<ProductEntity>, var context: Context,var model: CartViewModel) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    val db = Room.databaseBuilder(context, AppDatabase::class.java, "productsDB").build()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var proName : TextView
        lateinit var proPrice : TextView
        lateinit var proQuantity : TextView
        lateinit var proImage : ImageView
        lateinit var increase : ImageView
        lateinit var decrease : ImageView
        lateinit var delete : ImageView
        init {
            proName = view.findViewById(R.id.proNameId)
            proPrice = view.findViewById(R.id.proPriceId)
            proQuantity = view.findViewById(R.id.proQuantityId)
            proImage = view.findViewById(R.id.proImageId)
            increase = view.findViewById(R.id.increaseId)
            decrease = view.findViewById(R.id.decreaseId)
            delete = view.findViewById(R.id.deleteId)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cart_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.proName.text = dataSet[position].productName
        viewHolder.proPrice.text = dataSet[position].productPrice
        viewHolder.proQuantity.text = dataSet[position].productQuantity
        Picasso.get().load(dataSet[position].productImage).into(viewHolder.proImage)

        viewHolder.increase.setOnClickListener {
            viewHolder.proQuantity.text = viewHolder.proQuantity.text.toString().toInt().plus(1).toString()
            CoroutineScope(Dispatchers.IO).launch {
                db.productDao().updateProduct(
                    dataSet[position].productId,
                    viewHolder.proQuantity.text.toString()
                )
            }
        }

        viewHolder.decrease.setOnClickListener {
            var currentQuantity = viewHolder.proQuantity.text.toString().toInt()
            if(currentQuantity > 1) {
                viewHolder.proQuantity.text = currentQuantity.minus(1).toString()
                CoroutineScope(Dispatchers.IO).launch {
                    db.productDao().updateProduct(
                        dataSet[position].productId,
                        viewHolder.proQuantity.text.toString()
                    )
                }
            }
        }
        viewHolder.delete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.productDao().deleteById(dataSet[position].productId.toString())
            }
            model.getAllCartData(context)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}