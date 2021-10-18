package com.example.nclient.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.nclient.R
import com.example.nclient.databinding.FragmentCartBinding


class CartFragment : Fragment() {

    lateinit var binding : FragmentCartBinding

    val model: CartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater)
        binding.viewModel = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
        handleObserver()
        handleClick()

    }

    private fun handleView() {
        model.getAllCartData(requireContext())
    }

    private fun handleClick() {
        binding.placeOrderBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_cartNavId_to_placeOrderFragment)
        }
    }

    private fun handleObserver() {
        model.cartData.observe(viewLifecycleOwner,{
//            if(it.isNotEmpty()){
            binding.recyclerId.adapter = CartAdapter(it,requireContext(),model)
        })
    }

}