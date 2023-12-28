package com.example.mobimarket.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobimarket.R
import com.example.mobimarket.databinding.ItemAddProductBinding
import com.example.mobimarket.model.Product


class AdapterAddProduct :RecyclerView.Adapter<AdapterAddProduct.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAddProductBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAddProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount()  = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val products = differ.currentList[position]
        with(holder.binding) {
            val productImageUrl = products.product_image
            if (productImageUrl != null) {
                Glide.with(image)
                    .load(productImageUrl)
                    .into(image)
            } else {
                Glide.with(image)
                    .load(R.drawable.emptybox)
                    .into(image)
            }

            title.text = products.title
            price.text= products.price.toString()
            likesNumber.text = products.likes.toString()
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return  oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem== newItem
        }     

    }
    val differ = AsyncListDiffer(this, differCallback)
}