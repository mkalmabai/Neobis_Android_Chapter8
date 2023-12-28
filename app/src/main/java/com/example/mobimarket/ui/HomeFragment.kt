package com.example.mobimarket.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobimarket.R
import com.example.mobimarket.adapters.AdapterAddProduct
import com.example.mobimarket.api.Repository
import com.example.mobimarket.api.RetrofitInstance
import com.example.mobimarket.databinding.FragmentHomeBinding
import com.example.mobimarket.utils.Resource
import com.example.mobimarket.viewModel.HomeViewModel
import com.example.mobimarket.viewModel.ViewModelProviderFactoryProducts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterProduct: AdapterAddProduct
    lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        val repository = Repository()
        val viewModelFactory = ViewModelProviderFactoryProducts(repository)
        homeViewModel = ViewModelProvider( this, viewModelFactory).get(HomeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        adapterProduct = AdapterAddProduct()

        // Set the adapter on the RecyclerView
        binding.recyclerViewProduct.adapter = adapterProduct
        binding.recyclerViewProduct.layoutManager = GridLayoutManager(requireContext(), 2)
        homeViewModel.getProducts()
        homeViewModel .products.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { products ->
                        adapterProduct.differ.submitList(products)
                    }
                }

                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(requireContext(), "Не удалось загрузить товары", Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading -> {

                }
            }
        })
    }
}