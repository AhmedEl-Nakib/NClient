package com.example.nclient.ui.favorite

interface OnFavoriteItemClick {
    fun onItemClicked(item : FavoriteModel)
    fun onFavoriteUnCheckClicked(productId : String)
}