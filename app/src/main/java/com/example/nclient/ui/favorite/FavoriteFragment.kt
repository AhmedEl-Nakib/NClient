package com.example.nclient.ui.favorite

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.nclient.R
import com.example.nclient.databinding.FragmentFavoriteBinding
import com.example.nclient.ui.home.HomeAdapter
import com.example.nclient.ui.home.HomeFragmentDirections
import com.example.nclient.ui.home.HomeModel
import com.example.nclient.ui.home.HomeViewModel
import com.example.nclient.ui.util.Constants


class FavoriteFragment : Fragment(), OnFavoriteItemClick {

    lateinit var binding : FragmentFavoriteBinding
    val model: FavoriteViewModel by viewModels()
    lateinit var sharedPreferences : SharedPreferences
    lateinit var adapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater)
        binding.viewModel = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences  = requireActivity().getSharedPreferences(Constants.SHARED_APP_NAME, Context.MODE_PRIVATE)
        adapter = FavoriteAdapter(ArrayList(),this)
        model.getAllFavorites(sharedPreferences.getString(Constants.UID,"").toString())
        handleObserver()
    }

    private fun handleObserver() {
        model.favoritesList.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                adapter.filterData(it)
                binding.recyclerId.adapter = adapter
            }else{
                adapter.filterData(model.list)
            }
        })

        model.searchWord.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                filter(it)
            }else{
                adapter.filterData(model.list)
            }

        })
    }

    private fun filter(searchWord: String) {
        var filterdList = ArrayList<FavoriteModel>() // result
        for(name in model.list){
            if(name.title.toLowerCase().contains(searchWord)){
                filterdList.add(name)
            }
        }
        adapter.filterData(filterdList)
    }

    override fun onItemClicked(item: FavoriteModel) {
        Navigation.findNavController(requireView()).navigate(FavoriteFragmentDirections.actionFavoriteNavIdToProductDetailsFragment(item.img,item.title,item.descirption,item.price,item.productId))

    }

    override fun onFavoriteUnCheckClicked(productId: String) {
        model.deleteFromFavorite(productId,sharedPreferences.getString(Constants.UID,"").toString())
    }


}