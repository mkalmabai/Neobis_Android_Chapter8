package com.example.mobimarket.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.MainActivity
import com.example.mobimarket.R
import com.example.mobimarket.api.Repository
import com.example.mobimarket.databinding.FragmentLoginBinding
import com.example.mobimarket.databinding.FragmentRegistrationBinding
import com.example.mobimarket.utils.Resource
import com.example.mobimarket.utils.showSnackBar
import com.example.mobimarket.viewModel.RegViewModelProviderFactory
import com.example.mobimarket.viewModel.RegistrationViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    lateinit var registrationViewModel: RegistrationViewModel
    private val textWatcher: TextWatcher = createTextWatcher()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(layoutInflater,container,false)
        val repository = Repository()
        val viewModelFactory = RegViewModelProviderFactory(repository)
        registrationViewModel = ViewModelProvider(this, viewModelFactory).get(RegistrationViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createTextWatcher()
        binding.nameField.addTextChangedListener(textWatcher)
        binding.emailField.addTextChangedListener(textWatcher)

        binding.furtherButton.setOnClickListener {
//            findNavController().navigate(R.id.action_registrationFragment_to_createPasswordFragment)
//            showSnackBar.showCustomSnackbar(
//                requireContext(),
//                binding.root,
//                "Неверный логин или пароль"

//            )
            registrationViewModel.newUser(binding.nameField.text.toString(), binding.emailField.text.toString().trim(),
                "","")
            observe()

        }

    }
    private fun observe() {
        registrationViewModel.userSaved.observe(viewLifecycleOwner) { userSaved ->
            when (userSaved) {
                is Resource.Success -> {

                }
                is Resource.Error -> {
                    snackbar("Неверный логин или пароль")
                }

                is Resource.Loading -> {
                }
            }
        }
    }
    private fun checkFieldsAndEnableButton() {
        val username = binding.nameField.text.toString()
        val email = binding.emailField.text.toString()
        binding.furtherButton.isEnabled = username.isNotEmpty() && email.isNotEmpty()
    }
    private fun createTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                checkFieldsAndEnableButton()
                val mailInput = binding.emailField.text.toString().trim()
                validateEmail(mailInput)
                val nameInput = binding.nameField.text.toString().trim()
                validateName(nameInput)
                resetTextFieldsColors()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        }
    }
    private fun validateEmail(mailInput: String) {
        var isMailEmpty = mailInput.isEmpty()
        var isMailMatches = mailInput.matches(Regex("[A-Z a-z@.]*"))
        var isMailContains = mailInput.contains('@') && mailInput.contains('.')

        if (isMailEmpty || !isMailMatches || !isMailContains) {
            colorTextFields(R.color.red, R.color.red, binding.emailField, binding.inputLayoutEmail)
        }  else  {
            resetTextFieldsColors()
        }
    }
    private fun validateName(nameInput: String) {

        val isNameEmpty = nameInput.isEmpty()
        val isNameMatches = nameInput.matches(Regex("[A-Za-z]*"))
        if (isNameEmpty || !isNameMatches ) {
            colorTextFields(R.color.red, R.color.red, binding.nameField, binding.inputLayoutName)
        }  else {
            resetTextFieldsColors()
        }
    }
    private fun snackbar(message: String) {
        showSnackBar.showCustomSnackbar(requireContext(), binding.root, message)
    }
    private fun colorTextFields(hintColor: Int, textColor: Int, textfield: TextInputEditText, layoutField: TextInputLayout){
        layoutField.defaultHintTextColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), hintColor))
        textfield.setTextColor(ContextCompat.getColor(requireContext(), textColor))
    }
    private fun resetTextFieldsColors() {
        colorTextFields(R.color.grey, R.color.black, binding.emailField, binding.inputLayoutEmail)
    }


}