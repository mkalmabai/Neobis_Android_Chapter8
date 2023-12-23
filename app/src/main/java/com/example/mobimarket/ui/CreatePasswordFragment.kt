package com.example.mobimarket.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.mobimarket.MainActivity
import com.example.mobimarket.R
import com.example.mobimarket.api.Repository
import com.example.mobimarket.databinding.FragmentCreatePasswordBinding
import com.example.mobimarket.utils.Resource
import com.example.mobimarket.utils.ShowSnackBar
import com.example.mobimarket.viewModel.RegViewModelProviderFactory
import com.example.mobimarket.viewModel.RegistrationViewModel


class CreatePasswordFragment : Fragment() {
    private lateinit var binding: FragmentCreatePasswordBinding
    private var originalFontFamily: Typeface? = null
    private var originalFontFamily2: Typeface? = null
    lateinit var registrationViewModel: RegistrationViewModel
    private val textWatcher: TextWatcher = textWatcher()
    private var isPasswordValid = false
    private var isPasswordRepeatValid = false
    private val args: CreatePasswordFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePasswordBinding.inflate(layoutInflater,container,false)
        val repository = Repository()
        val viewModelFactory = RegViewModelProviderFactory(repository)
        registrationViewModel = ViewModelProvider(this, viewModelFactory).get(RegistrationViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.passwordField.addTextChangedListener(textWatcher)
        binding.repeatPasswordField.addTextChangedListener(textWatcher)

        binding.furtherButton.setOnClickListener {
//            val intent = Intent(activity, MainActivity::class.java)
//            startActivity(intent)
//            binding.passwordField.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            registrationViewModel.newUser(args.username,args.email,binding.passwordField.text.toString(), binding.repeatPasswordField.text.toString().trim())
            observe()
        }
        hideTogglePassword()
    }
    private fun observe() {
        registrationViewModel.userSaved.observe(viewLifecycleOwner) { userSaved ->
            when (userSaved) {
                is Resource.Success -> {
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
                is Resource.Error -> {
                    ShowSnackBar.showCustomSnackbar(requireContext(), binding.root, "error")
                }

                is Resource.Loading -> {
                    binding.furtherButton.text = "Loading..."
                }
            }
        }
    }
    private fun textWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val passwordInput = binding.passwordField.text.toString().trim()
                val passwordRepeatInput = binding.repeatPasswordField.text.toString().trim()
                validatePassword(passwordInput)
                validatePasswordRepeat(passwordRepeatInput)
                binding.furtherButton.isEnabled = isPasswordRepeatValid&&isPasswordValid
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }
    }
    private fun hideTogglePassword() {
        originalFontFamily = binding.passwordField.typeface
        originalFontFamily2 = binding.repeatPasswordField.typeface

        binding.togglePasswordButton.setOnCheckedChangeListener { _, isChecked ->
            binding.passwordField.inputType =
                if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.repeatPasswordField.inputType =
                if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.passwordField.setSelection(binding.passwordField.text?.length ?: 0)
            binding.repeatPasswordField.setSelection(binding.repeatPasswordField.text?.length ?: 0)
            if (!isChecked) {
                binding.passwordField.typeface = originalFontFamily
                binding.repeatPasswordField.typeface = originalFontFamily2
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
            binding.passwordField.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }else {
            isPasswordValid = true
            binding.passwordField.setTextColor(ContextCompat.getColor(requireContext(), R.color.black2))
        }

    }
    private fun validatePasswordRepeat(passwordRepeatInput: String) {
        val isPasswordEmpty = passwordRepeatInput.isEmpty()
        val passwordInput = binding.passwordField.text.toString().trim()

        if (passwordRepeatInput != passwordInput) {
            binding.repeatPasswordField.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        } else{
            isPasswordRepeatValid = true
            binding.repeatPasswordField.setTextColor(ContextCompat.getColor(requireContext(), R.color.black2))
        }

    }
}