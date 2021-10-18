package com.example.nclient.ui.placeOrder

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.nclient.databinding.FragmentPlaceOrderBinding
import com.example.nclient.ui.util.Constants
import com.google.android.material.snackbar.Snackbar


class PlaceOrderFragment : Fragment() {

    lateinit var binding : FragmentPlaceOrderBinding
    val model: PlaceOrderViewModel by viewModels()
    var allProductsPrice : Int = 0
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPlaceOrderBinding.inflate(inflater)
        binding.viewModel = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.getAllCartData(requireContext())
        sharedPreferences  = requireActivity().getSharedPreferences(Constants.SHARED_APP_NAME, Context.MODE_PRIVATE)
        handleObserver()
        handleClick()
    }

    private fun handleClick() {
        binding.confirmOrderId.setOnClickListener {
            model.placeOrder(requireContext(),sharedPreferences.getString(Constants.CLIENT_NAME,"").toString(),sharedPreferences.getString(Constants.CLIENT_PHONE,"").toString(),sharedPreferences.getString(Constants.CLIENT_ADDRESS,"").toString(),sharedPreferences.getString(Constants.UID,"").toString())
        }
    }

    private fun handleObserver() {
        model.cartData.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                for (product in it){
                    allProductsPrice += (product.productPrice.toInt() * product.productQuantity.toInt())
                }
                binding.priceId.text = "Your Order Price is :\n $allProductsPrice \$"
            }else{
                binding.priceId.text = "No Products"
            }
        })
        model.submitOrderStatus.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                Snackbar.make(binding.rootId, it.toString(), Snackbar.LENGTH_SHORT).show()
            }
        })
    }


}