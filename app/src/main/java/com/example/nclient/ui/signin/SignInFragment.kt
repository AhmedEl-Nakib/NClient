package com.example.nclient.ui.signin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.nclient.R
import com.example.nclient.databinding.FragmentSignInBinding
import com.example.nclient.ui.util.Constants
import com.google.android.material.snackbar.Snackbar


class SignInFragment : Fragment() {

    lateinit var binding : FragmentSignInBinding
    val model: SignInViewModel by viewModels()
    lateinit var sharedPreferences : SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater)
        binding.viewModel = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.forgetPasswordId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_signInFragment_to_forgetPasswordFragment)
        }
        sharedPreferences  = requireActivity().getSharedPreferences(Constants.SHARED_APP_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        handleObserver()
    }

    private fun handleObserver() {
        model.signInState.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                if(it.equals("success")){
                    editor.putString(Constants.UID,model.mAuth.currentUser!!.uid.toString())
                    editor.putString(Constants.CLIENT_NAME,model.userName.value)
                    editor.putString(Constants.CLIENT_PHONE,model.userPhone.value)
                    editor.putString(Constants.CLIENT_ADDRESS,model.userAddress.value)
                    editor.apply()
                    editor.commit()
                    Log.i("UID",model.userName.value.toString() +" - "+sharedPreferences.getString(Constants.CLIENT_NAME,"").toString())
                    Navigation.findNavController(requireView()).navigate(R.id.action_signInFragment_to_homeNavId)
                    model.signInState.value = ""
                }else {
                    Snackbar.make(binding.rootId, it.toString(), Snackbar.LENGTH_SHORT).show()
                    model.signInState.value = ""
                }
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