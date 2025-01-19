package com.example.yemeim.data.datasource

import android.util.Log
import com.example.yemeim.data.entity.SepetteYemekler
import com.example.yemeim.data.repo.SepetRepo
import com.example.yemeim.data.retrofit.SepetDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SepetDataSource(var sdao: SepetDao) {
suspend fun yemekleriGetir(kullanici_adi :String):List<SepetteYemekler> = withContext(Dispatchers.IO){
    try {
        val response = sdao.yemekleriGetir(kullanici_adi)


        val cartFoods = response.sepet_yemekler


        return@withContext cartFoods
    } catch (e: Exception) {
        return@withContext emptyList<SepetteYemekler>()
    }

}

    suspend fun  YemekEkle(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ){
        sdao.YemekEkle(yemek_adi ,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }

    suspend fun sepettenYemegiSil(sepet_yemek_id: Int, kullanici_adi: String){
        sdao.sepettenYemegiSil(sepet_yemek_id, kullanici_adi)
    }

}