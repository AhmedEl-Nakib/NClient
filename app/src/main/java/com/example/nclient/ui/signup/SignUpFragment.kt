package com.example.nclient.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.nclient.R
import com.example.nclient.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar


class SignUpFragment : Fragment() {

    lateinit var binding : FragmentSignUpBinding
    val model: SignUpViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater)
        binding.viewModel = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleObserver()
    }

    private fun handleObserver() {
        model.signUpState.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                Snackbar.make(binding.rootId, it.toString(), Snackbar.LENGTH_SHORT)
                    .show()
            }
        })

        model.checkDataValidation.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                Snackbar.make(binding.rootId, it.toString(), Snackbar.LENGTH_SHORT)
                    .show()
            }
        })
    }
}