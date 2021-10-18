package com.example.nclient.ui.notification

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.nclient.R
import com.example.nclient.databinding.FragmentNotificationBinding
import com.example.nclient.ui.home.HomeViewModel
import com.example.nclient.ui.util.Constants


class NotificationFragment : Fragment() {


    lateinit var binding : FragmentNotificationBinding
    val model: NotificationViewModel by viewModels()
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater)
        binding.viewModel = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences  = requireActivity().getSharedPreferences(Constants.SHARED_APP_NAME, Context.MODE_PRIVATE)
        model.getMyNotifications(sharedPreferences.getString(Constants.UID,"").toString())
        handleObserver()
    }

    private fun handleObserver() {
        model.notificationsList.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                binding.recyclerId.adapter = NotificationAdapter(it)
            }
        })
    }

}