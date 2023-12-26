package com.example.mobimarket.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.MainActivity
import com.example.mobimarket.R
import com.example.mobimarket.api.Repository
import com.example.mobimarket.databinding.FragmentLoginBinding
import com.example.mobimarket.utils.Resource
import com.example.mobimarket.utils.ShowSnackBar

import com.example.mobimarket.viewModel.LoginViewModel
import com.example.mobimarket.viewModel.LoginViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val textWatcher: TextWatcher = createTextWatcher()
    private var isPasswordValid = false
    private var isNamesValid = false
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        val repository = Repository()
        val viewModelFactory = LoginViewModelProviderFactory(repository)
        loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.passwordField.addTextChangedListener(textWatcher)
        binding.nameField.addTextChangedListener(textWatcher)
        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        binding.loginButton.setOnClickListener {
//            val intent = Intent(activity, MainActivity::class.java)
//            startActivity(intent)
            loginViewModel.login(binding.nameField.text.toString().trim(), binding.passwordField.text.toString().trim())
            observe()
        }
    }
    private fun observe() {
        loginViewModel.token.observe(viewLifecycleOwner) { token ->
            when (token) {
                is Resource.Success -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    }, 600)
                    ShowSnackBar.showTrueCustomSnackbar(requireContext(), binding.root, "Successfully")
                }
                is Resource.Error -> {
                    ShowSnackBar.showCustomSnackbar(requireContext(), binding.root, "error")
                }

                is Resource.Loading -> {
                    binding.loginButton.text = "Loading..."
                }else -> {}
            }
        }
    }
    private fun createTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                val passwordInput = binding.passwordField.text.toString().trim()
                val nameInput = binding.nameField.text.toString().trim()
                validatePassword(passwordInput)
                validateName(nameInput)
                binding.loginButton.isEnabled = isPasswordValid&&isNamesValid
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        }
    }
    private fun validatePassword(passwordInput: String) {
        val isValidPassword = passwordInput.isNotEmpty() &&
                passwordInput.contains(Regex("[A-Z]")) &&
                passwordInput.contains(Regex("[a-z]")) &&
                passwordInput.matches(Regex(".*[!\"#\$%&'()*+,-./:;<=>?@\\[\\\\\\]^_`{|}~].*")) &&
                (passwordInput.length in 8..15) &&
                passwordInput.any { it.isDigit() }


        if (!isValidPassword){
            colorTextFields(R.color.red, R.color.red, binding.passwordField, binding.inputLayoutPassword)
        }else {
            colorTextFields(R.color.grey, R.color.black, binding.passwordField, binding.inputLayoutPassword)
            isPasswordValid = true
        }

    }
    private fun validateName(nameInput: String) {

        val isNameValid = nameInput.isNotEmpty()
                &&nameInput.matches(Regex("[A-Za-z]*"))
        if ( !isNameValid ) {
            colorTextFields(R.color.red, R.color.red, binding.nameField, binding.inputLayoutName)
        }  else {
            colorTextFields(R.color.grey, R.color.black, binding.nameField, binding.inputLayoutName)
            isNamesValid =true
        }
    }
    private fun colorTextFields(hintColor: Int, textColor: Int, textfield: TextInputEditText, layoutField: TextInputLayout){
        layoutField.defaultHintTextColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), hintColor))
        textfield.setTextColor(ContextCompat.getColor(requireContext(), textColor))
    }
}