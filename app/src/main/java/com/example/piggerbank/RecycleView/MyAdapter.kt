package com.example.piggerbank.RecycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.piggerbank.R

class MyAdapter(private val moneyList : ArrayList<MoneyRV>) :
RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = moneyList[position]
        holder.itemName.text = currentItem.name
        holder.itemValue.text = currentItem.value.toString()
        holder.itemCat.text = currentItem.cat
        holder.itemDate.text = currentItem.date
    }

    override fun getItemCount(): Int {
        return moneyList.size
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val itemName : TextView = itemView.findViewById(R.id.cart_item_name)
        val itemValue : TextView = itemView.findViewById(R.id.cart_item_value)
        val itemCat : TextView = itemView.findViewById(R.id.cart_item_category)
        val itemDate : TextView = itemView.findViewById(R.id.cart_item_date)

    }

}