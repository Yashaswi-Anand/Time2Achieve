package com.yashanand.time2achieve.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yashanand.time2achieve.R
import com.yashanand.time2achieve.activities.ScrollingActivity

class YearListAdapter(
    var context: Context,
    var yearlist: ArrayList<String>,
    var image: String,
    var branchType: String,
    var examtype: String
) :
    RecyclerView.Adapter<YearListAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.yearlist_item_layout, parent, false)
        return ItemHolder(
            itemHolder
        )
    }

    override fun getItemCount(): Int {
        return yearlist.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val items = yearlist[position]
        holder.p_name.text = items + " "
        Picasso.get().load(image).into(holder.Image_icon)

        holder.relative_item.setOnClickListener {
            context.startActivity(
                Intent(context, ScrollingActivity::class.java)
                    .putExtra("YearNumber", items)
                    .putExtra("examtype", examtype)
                    .putExtra("branchtype", branchType)
            )
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var Image_icon = itemView.findViewById<ImageView>(R.id.img)
        var p_name = itemView.findViewById<TextView>(R.id.tv_year)
        val relative_item = itemView.findViewById<LinearLayout>(R.id.lin_item)

    }


}