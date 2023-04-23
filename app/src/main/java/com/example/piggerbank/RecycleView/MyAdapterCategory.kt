package com.example.piggerbank.RecycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.piggerbank.R

class MyAdapterCategory(private val categoryList : ArrayList<CategoryRV>) :
RecyclerView.Adapter<MyAdapterCategory.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterCategory.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view_category,
        parent, false)
        return MyAdapterCategory.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyAdapterCategory.MyViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.itemName.text = currentItem.name
        holder.itemUpper.text = currentItem.upper
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val itemName : TextView = itemView.findViewById(R.id.cart_item_catName)
        val itemUpper : TextView = itemView.findViewById(R.id.cart_item_catUpper)
    }
}