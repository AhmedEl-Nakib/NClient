package com.example.nclient.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.nclient.R
import com.example.nclient.databinding.FragmentHomeBinding
import com.example.nclient.ui.signin.SignInViewModel


class HomeFragment : Fragment(), OnHomeItemClick {

    lateinit var binding : FragmentHomeBinding
    val model: HomeViewModel by viewModels()
    lateinit var adapter: HomeAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.getAllProducts()
        handleObserver()
        adapter = HomeAdapter(ArrayList(),this)
    }

    private fun handleObserver() {
        model.productsList.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                adapter.filterData(it)
                binding.recyclerId.adapter = adapter
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
        var filterdList = ArrayList<HomeModel>() // result
        for(name in model.list){
            if(name.title.toLowerCase().contains(searchWord)){
                filterdList.add(name)
            }
        }
        adapter.filterData(filterdList)
    }

    override fun onItemClicked(item: HomeModel) {
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeNavIdToProductDetailsFragment(item.img,item.title,item.description,item.price,item.proId))
    }


}