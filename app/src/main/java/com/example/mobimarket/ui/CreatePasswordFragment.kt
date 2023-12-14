package com.example.mobimarket.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobimarket.MainActivity
import com.example.mobimarket.databinding.FragmentCreatePasswordBinding
import com.example.mobimarket.databinding.FragmentLoginBinding


class CreatePasswordFragment : Fragment() {
    private lateinit var binding: FragmentCreatePasswordBinding
    private var originalFontFamily: Typeface? = null
    private var originalFontFamily2: Typeface? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePasswordBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.furtherButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        originalFontFamily = binding.passwordField.typeface
        originalFontFamily2 = binding.repeatPasswordField.typeface

        binding.togglePasswordButton.setOnCheckedChangeListener { _, isChecked ->
            // Set the input type based on whether the password should be visible for the first password field
            binding.passwordField.inputType =
                if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            // Set the input type based on whether the password should be visible for the second password field
            binding.repeatPasswordField.inputType =
                if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            // Move the cursor to the end of the text after changing the input type for the first password field
            binding.passwordField.setSelection(binding.passwordField.text?.length ?: 0)

            // Move the cursor to the end of the text after changing the input type for the second password field
            binding.repeatPasswordField.setSelection(binding.repeatPasswordField.text?.length ?: 0)

            // Reset the font family when hiding the password for both password fields
            if (!isChecked) {
                binding.passwordField.typeface = originalFontFamily
                binding.repeatPasswordField.typeface = originalFontFamily2
            }
        }
    }
}