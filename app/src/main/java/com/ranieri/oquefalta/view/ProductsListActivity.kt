package com.ranieri.oquefalta.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.ranieri.oquefalta.R
import com.ranieri.oquefalta.entity.Category
import com.ranieri.oquefalta.entity.Product
import com.ranieri.oquefalta.view.adapter.ProductAdapter
import kotlinx.android.synthetic.main.activity_products_list.*

class ProductsListActivity : AppCompatActivity() {
    private val TAG = this::class.java.name

    private var products = mutableListOf<Product>()
    private var category: Category? = null
    private var adapter = ProductAdapter(products, this::onProductItemClick)
    private var database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_list)

        val intentCategory = intent.getSerializableExtra("category") as Category?
        if (intentCategory != null) {
            category = intentCategory
            initRecyclerView()
            configFirestore()
        }
    }

    private fun configFirestore() {
        database.collection("produtos").whereEqualTo("cat", category!!.desc)
            .addSnapshotListener(EventListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@EventListener
                }
                for (productDocumentChange in snapshots?.documentChanges!!) {
                    when (productDocumentChange.type) {
                        DocumentChange.Type.ADDED -> {
                            val product = productDocumentChange.document.toObject(Product::class.java)
                            addItem(product)
                        }
                        DocumentChange.Type.MODIFIED -> Log.d(TAG, "Modified product: " + productDocumentChange.document.data)
                        DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed product: " + productDocumentChange.document.data)
                    }
                }
            })
    }

    private fun addItem(product: Product) {
        products.add(product)
        adapter.notifyItemInserted(products.lastIndex)
    }

    private fun initRecyclerView() {
        rvProduct.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        rvProduct.layoutManager = layoutManager
    }

    private fun onProductItemClick(product: Product) {
        // TODO:
    }
}