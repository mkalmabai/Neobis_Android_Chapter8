package com.example.mobimarket.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.mobimarket.api.Repository
import com.example.mobimarket.model.Product
import com.example.mobimarket.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(val repository: Repository): ViewModel() {
    private val _products: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val products: LiveData<Resource<List<Product>>>
    get() = _products

    private fun saveProducts(response: List<Product>) {
        _products.postValue(Resource.Success(response))
    }

    fun getProducts() {
        viewModelScope.launch {
            try {
                val response = repository.getAllProduct()
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    productResponse?.let { saveProducts(it) }
                    Log.d("getMyProducts", "Successful: $productResponse")

                }else{
                    _products.postValue(Resource.Error("Ошибка загрузки моих товаров"))
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Ошибка загрузки: ${e.message}")
                _products.postValue(Resource.Error(e.message ?: "Ошибка загрузки"))
            }
        }
    }
}
class ViewModelProviderFactoryProducts (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



