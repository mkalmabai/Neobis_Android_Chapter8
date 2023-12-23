package com.example.mobimarket.viewModel

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.*
import com.example.mobimarket.api.Repository
import com.example.mobimarket.model.LoginRequest
import com.example.mobimarket.utils.Resource
import com.example.mobimarket.utils.Utils
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: Repository): ViewModel(){
    private val _token: MutableLiveData<Resource<String>> = MutableLiveData()
    val token: LiveData<Resource<String>>
        get() = _token

    private fun saveToken(response: String) {
        _token.postValue(Resource.Success(response))
    }
    fun login(username: String,email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(username,email, password)
                val response = repository.login(loginRequest)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.access_token?.let { saveToken(it) }
                    if (loginResponse != null) {
                        Utils.access_token_empty = loginResponse.access_token
                    }
                        Log.d("Registration", "Successful: $loginResponse")

                    } else {
                        _token.postValue(Resource.Error("Ошибка авторизации"))
                    }

            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка авторизации: ${e.message}")
                _token.postValue(Resource.Error(e.message ?: "Ошибка авторизации"))
            }
        }
    }


}
class LoginViewModelProviderFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown LoginViewModel class")
    }
}