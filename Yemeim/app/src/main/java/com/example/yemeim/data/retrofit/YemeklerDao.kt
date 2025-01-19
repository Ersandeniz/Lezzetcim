package com.example.yemeim.data.retrofit

import com.example.yemeim.data.entity.YemeklerCevap
import retrofit2.http.GET

interface YemeklerDao {
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun yemeklerigetir(): YemeklerCevap

}