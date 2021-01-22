package com.yashanand.time2achieve.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yashanand.time2achieve.R
import com.yashanand.time2achieve.activities.ListOfexam


class BranchAdapter(
    var context: Context,
    var branchlist: ArrayList<String>
) :
    RecyclerView.Adapter<BranchAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.branch_item_layout, parent, false)
        return ItemHolder(
            itemHolder
        )
    }

    override fun getItemCount(): Int {
        return branchlist.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val items = branchlist[position]
        holder.br_name.text = items + " "
        holder.cardList.setOnClickListener {
            context.startActivity(
                Intent(context, ListOfexam::class.java).putExtra(
                    "position",
                    items
                )
            )
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var br_name = itemView.findViewById<TextView>(R.id.br_title)
        val cardList = itemView.findViewById<CardView>(R.id.cardlist)

    }
}