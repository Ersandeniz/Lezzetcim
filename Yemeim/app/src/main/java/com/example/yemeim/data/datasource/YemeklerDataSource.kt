package com.example.yemeim.data.datasource

import com.example.yemeim.data.entity.SepetteYemekler
import com.example.yemeim.data.entity.Yemekler
import com.example.yemeim.data.retrofit.SepetDao
import com.example.yemeim.data.retrofit.YemeklerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YemeklerDataSource (var ydao: YemeklerDao){
    suspend fun yemeklerigetir():List<Yemekler> = withContext(Dispatchers.IO){
        return@withContext ydao.yemeklerigetir().yemekler

    }

}