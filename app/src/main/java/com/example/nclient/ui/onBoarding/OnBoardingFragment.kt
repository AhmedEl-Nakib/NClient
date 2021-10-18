package com.example.nclient.ui.onBoarding

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.nclient.R
import com.example.nclient.databinding.FragmentOnBoardingBinding
import com.example.nclient.ui.util.Constants


class OnBoardingFragment : Fragment() {

    lateinit var binding:FragmentOnBoardingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_onBoardingFragment_to_signInFragment)
        }
        binding.signUpBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_onBoardingFragment_to_signUpFragment)
        }
        val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences(Constants.SHARED_APP_NAME, Context.MODE_PRIVATE)
        if(sharedPreferences.getString(Constants.UID,"").toString().isNotEmpty()){
            Navigation.findNavController(requireView()).navigate(R.id.action_onBoardingFragment_to_homeNavId)
        }

    }


}