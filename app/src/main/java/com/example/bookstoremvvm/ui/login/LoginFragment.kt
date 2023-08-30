package com.example.bookstoremvvm.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bookstoremvvm.R
import com.example.bookstoremvvm.common.viewBinding
import com.example.bookstoremvvm.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    private lateinit var sharedPref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireActivity().getSharedPreferences("AppSetting", Context.MODE_PRIVATE)

        val isFirstOpen = sharedPref.getBoolean("isFirstOpen", true)

        if (isFirstOpen) Toast.makeText(context, "first", Toast.LENGTH_SHORT).show()
        else findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToBooksFragment())

        with(sharedPref.edit()) {
            putBoolean("isFirstOpen", false)
            apply()
        }

        with(binding) {
            btnLogin.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToBooksFragment())
            }
        }
    }
}