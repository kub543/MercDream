package com.baszczyk.mercpiggibank3.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.TextItemViewHolder
import com.baszczyk.mercpiggibank3.database.PiggyBank

class PiggyBankAdapter: RecyclerView.Adapter<TextItemViewHolder>() {

    override fun getItemCount() = data.size

    var data = listOf<PiggyBank>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.textView.text = item.piggyId.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_view, parent, false) as TextView
        return  TextItemViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namePiggy: TextView = itemView.findViewById(R.id.surname_mercedes)
        val pointPiggy: TextView = itemView.findViewById(R.id.piggy_list)
    }
}