package com.example.piggerbank.RecycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.piggerbank.R

class MyAdapterCategory(private var categoryList : ArrayList<CategoryRV>) :
RecyclerView.Adapter<MyAdapterCategory.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterCategory.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view_category,
        parent, false)
        return MyAdapterCategory.MyViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun updateData(newData: ArrayList<CategoryRV>){
        categoryList = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyAdapterCategory.MyViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.itemName.text = currentItem.name
        holder.itemUpper.text = currentItem.upper
    }


    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView)
    {
        val itemName : TextView = itemView.findViewById(R.id.cart_item_catName)
        val itemUpper : TextView = itemView.findViewById(R.id.cart_item_catUpper)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}