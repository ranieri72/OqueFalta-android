package com.ranieri.oquefalta.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ranieri.oquefalta.R
import com.ranieri.oquefalta.entity.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(
    private val categorys: List<Category>,
    private val callback: (Category) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.VH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.VH {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
            val vh = VH(v)
            vh.itemView.setOnClickListener {
                val beer = categorys[vh.adapterPosition]
                callback(beer)
            }
            return vh
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val category = categorys[position]
            holder.txtCategoryName.text = category.desc
        }

        override fun getItemCount(): Int = categorys.size

        class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtCategoryName: TextView = itemView.txtCategoryName
        }
}