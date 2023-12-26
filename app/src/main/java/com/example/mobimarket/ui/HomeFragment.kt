package com.example.mobimarket.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobimarket.R
import com.example.mobimarket.adapters.AdapterAddProduct
import com.example.mobimarket.api.RetrofitInstance
import com.example.mobimarket.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterProduct: AdapterAddProduct
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Initialize the adapter
        adapterProduct = AdapterAddProduct()

        // Set the adapter on the RecyclerView
        binding.recyclerViewProduct.adapter = adapterProduct
        binding.recyclerViewProduct.layoutManager = GridLayoutManager(requireContext(), 2)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getAllProducts()
                if (response.isSuccessful) {
                    val yourData = response.body()
                    Log.d("YourActivity", "Received data: $yourData")
                    // Submit the list to the adapter on the main thread
                    withContext(Dispatchers.Main) {
                        adapterProduct.differ.submitList(yourData)
                    }
                } else {
                    Log.e("YourActivity", "Request failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("YourActivity", "Error: ${e.message}", e)
            }
        }
    }

}