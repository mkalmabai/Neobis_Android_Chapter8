package com.example.mobimarket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobimarket.databinding.FragmentCreatePasswordBinding
import com.example.mobimarket.databinding.FragmentLoginBinding


class CreatePasswordFragment : Fragment() {
    private lateinit var binding: FragmentCreatePasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePasswordBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}