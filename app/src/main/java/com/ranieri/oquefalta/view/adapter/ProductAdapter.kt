package com.ranieri.oquefalta.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ranieri.oquefalta.R
import com.ranieri.oquefalta.entity.Product
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(
    private val products: List<Product>,
    private val callback: (Product) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        val vh = VH(v)
        vh.itemView.setOnClickListener {
            val beer = products[vh.adapterPosition]
            callback(beer)
        }
        return vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val product = products[position]
        holder.txtProductName.text = product.desc
    }

    override fun getItemCount(): Int = products.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtProductName: TextView = itemView.txtProductName
    }
}