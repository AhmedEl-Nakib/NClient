package com.example.nclient.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDAO {

    @Insert
    suspend fun insertProduct(product:ProductEntity)

    @Query("SELECT * FROM productentity WHERE productId = :productId")
    suspend fun getProductData(productId : String) : List<ProductEntity>

    @Query("SELECT * FROM productentity")
    suspend fun getAllProductsData() : List<ProductEntity>

    @Query("UPDATE productentity SET productQuantity = :productQuantity WHERE productId = :productId")
    suspend fun updateProduct(productId: String , productQuantity :String)

    @Query("DELETE FROM productentity")
    fun deleteAll()

    @Query("DELETE FROM productentity WHERE productId = :productId")
    fun deleteById(productId:String)

//    @Query("SELECT * FROM user")
//    suspend fun getAllData() : List<User>
//

//

//
//    //UPDATE tableName , SET , WHERE
//    @Query("UPDATE user SET userName = :name , userAddress = :address WHERE id = :id")
//    fun updateUser(id:Int , name:String , address:String)
}