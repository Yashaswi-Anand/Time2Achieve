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
import com.yashanand.time2achieve.activities.YearList
import com.yashanand.time2achieve.model.ExamList


class LIstOfExamAdapter(
    var context: Context,
    var examlist: ArrayList<ExamList>,
    var branch_type: String
) :
    RecyclerView.Adapter<LIstOfExamAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.examoflist_item_layout, parent, false)
        return ItemHolder(
            itemHolder
        )
    }

    override fun getItemCount(): Int {
        return examlist.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val items = examlist[position]
        if (items.ExamImage == null) {
            holder.p_name.text = items.ExamName?.toUpperCase() + " "
        } else {
            holder.p_name.visibility = View.INVISIBLE
            Picasso.get().load(items.ExamImage).into(holder.Image_icon)
        }
        holder.relative_item.setOnClickListener {
            context.startActivity(
                Intent(context, YearList::class.java).putExtra("position", items.ExamName)
                    .putExtra("branch_type", branch_type)
            )
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var Image_icon = itemView.findViewById<ImageView>(R.id.img_icon)
        var p_name = itemView.findViewById<TextView>(R.id.tv_eol)
        val relative_item = itemView.findViewById<LinearLayout>(R.id.rel_item)

    }
}