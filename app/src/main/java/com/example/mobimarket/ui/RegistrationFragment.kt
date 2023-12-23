package com.example.mobimarket.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentRegistrationBinding
import com.example.mobimarket.utils.ShowSnackBar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val textWatcher: TextWatcher = createTextWatcher()
    private var isEmailValid = false
    private var isNameValid = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        createTextWatcher()
        binding.nameField.addTextChangedListener(textWatcher)
        binding.emailField.addTextChangedListener(textWatcher)

        binding.furtherButton.setOnClickListener {
            var email = binding.emailField.text.toString().trim()
            var username = binding.nameField.text.toString().trim()
            val action = RegistrationFragmentDirections.actionRegistrationFragmentToCreatePasswordFragment(email,username)
            findNavController().navigate(action)
//


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

                val mailInput = binding.emailField.text.toString().trim()
                val nameInput = binding.nameField.text.toString().trim()
                validateEmail(mailInput)
                validateName(nameInput)
                binding.furtherButton.isEnabled = isEmailValid&&isNameValid
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

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
            colorTextFields(R.color.grey, R.color.black, binding.emailField, binding.inputLayoutEmail)
             isNameValid = true

        }
    }
    private fun validateName(nameInput: String) {

        val isNameEmpty = nameInput.isEmpty()
        val isNameMatches = nameInput.matches(Regex("[A-Za-z]*"))
        if (isNameEmpty || !isNameMatches ) {
            colorTextFields(R.color.red, R.color.red, binding.nameField, binding.inputLayoutName)
        }  else {
            colorTextFields(R.color.grey, R.color.black, binding.nameField, binding.inputLayoutName)
            isEmailValid = true
        }
    }
    private fun snackbar(message: String) {
        ShowSnackBar.showCustomSnackbar(requireContext(), binding.root, message)
    }
    private fun colorTextFields(hintColor: Int, textColor: Int, textfield: TextInputEditText, layoutField: TextInputLayout){
        layoutField.defaultHintTextColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), hintColor))
        textfield.setTextColor(ContextCompat.getColor(requireContext(), textColor))
    }



}