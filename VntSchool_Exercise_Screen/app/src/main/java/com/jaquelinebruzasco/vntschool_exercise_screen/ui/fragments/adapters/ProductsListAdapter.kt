package com.jaquelinebruzasco.vntschool_exercise_screen.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaquelinebruzasco.vntschool_exercise_screen.databinding.ItemListBinding
import com.jaquelinebruzasco.vntschool_exercise_screen.model.ProductModel
import com.jaquelinebruzasco.vntschool_exercise_screen.ui.toBrl

class ProductsListAdapter(
    val action: (ProductModel) -> Unit
) : RecyclerView.Adapter<ProductsListAdapter.ProductsListViewHolder>() {

    var list: MutableList<ProductModel> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductsListViewHolder(
        ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(
        holder: ProductsListAdapter.ProductsListViewHolder,
        position: Int
    ) {
        list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount() = list.size

    inner class ProductsListViewHolder(private val binding: ItemListBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(data: ProductModel) {
                    binding.apply {
                        tvProductName.text = data.name
                        tvPriceInfo.text = data.price.toBrl()
                    }
                    binding.root.setOnClickListener { action(data) }
                }
            }
}