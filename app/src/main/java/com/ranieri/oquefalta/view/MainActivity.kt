package com.ranieri.oquefalta.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.ranieri.oquefalta.R
import com.ranieri.oquefalta.entity.Category
import com.ranieri.oquefalta.view.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.java.name

    private var categorys = mutableListOf<Category>()
    private var adapter = CategoryAdapter(categorys, this::onCategoryItemClick)
    private var database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        configFirestore()
    }

    private fun configFirestore() {
        database.collection("categoria")
            .addSnapshotListener(EventListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@EventListener
                }
                for (categoryDocumentChange in snapshots?.documentChanges!!) {
                    when (categoryDocumentChange.type) {
                        DocumentChange.Type.ADDED -> {
                            val category = categoryDocumentChange.document.toObject(Category::class.java)
                            addItem(category)
                        }
                        DocumentChange.Type.MODIFIED -> Log.d(TAG, "Modified category: " + categoryDocumentChange.document.data)
                        DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed category: " + categoryDocumentChange.document.data)
                    }
                }
            })
    }

    private fun addItem(category: Category) {
        categorys.add(category)
        adapter.notifyItemInserted(categorys.lastIndex)
    }

    private fun initRecyclerView() {
        rvCategory.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        rvCategory.layoutManager = layoutManager
    }

    private fun onCategoryItemClick(category: Category) {
        val intent = Intent(this, ProductsListActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}
