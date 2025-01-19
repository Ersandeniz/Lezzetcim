package com.example.yemeim.data.repo

import com.example.yemeim.data.datasource.YemeklerDataSource

class YemekRepo (var yds : YemeklerDataSource){

    suspend fun yemekleriGetir() =yds.yemeklerigetir()
}