package com.example.nclient.ui.productDetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.nclient.R
import com.example.nclient.databinding.FragmentProductDetailsBinding
import com.example.nclient.ui.signup.SignUpViewModel
import com.example.nclient.ui.util.Constants
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


class ProductDetailsFragment : Fragment() {

    lateinit var binding :FragmentProductDetailsBinding
    val args: ProductDetailsFragmentArgs by navArgs()
    val model: ProductDetailsViewModel by viewModels()
    lateinit var sharedPreferences : SharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater)
        binding.viewModel = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences  = requireActivity().getSharedPreferences(Constants.SHARED_APP_NAME, Context.MODE_PRIVATE)
        binding.titleId.text = args.title
        model.productName.value = args.title
        binding.priceId.text = args.price
        model.productPrice.value = args.price
        binding.descId.text = args.desc
        model.productDescription.value = args.desc
        Picasso.get().load(args.img).into(binding.imgId)
        model.productImage.value = args.img
        model.productId.value = args.proId
        model.UID.value = sharedPreferences.getString(Constants.UID,"").toString()
        handleObserver()
        handleClick()
    }

    private fun handleClick() {
        binding.addToCartBtn.setOnClickListener {
            model.addToCart(requireContext())
        }
    }

    private fun handleObserver() {
        model.addFavoriteStatus.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                Snackbar.make(binding.rootId, it.toString(), Snackbar.LENGTH_SHORT).show()
            }
        })
        model.addCartStatus.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                Snackbar.make(binding.rootId, it.toString(), Snackbar.LENGTH_SHORT).show()
            }
        })
    }


}