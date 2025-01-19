package com.example.yemeim.data.repo

import com.example.yemeim.data.datasource.SepetDataSource

class SepetRepo(var sds :SepetDataSource) {


    suspend fun YemekEkle(yemek_adi: String,
                            yemek_resim_adi: String,
                            yemek_fiyat: Int,
                            yemek_siparis_adet: Int,
                            kullanici_adi: String) = sds.YemekEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)

    suspend fun yemekleriGetir(kullaniciadi:String) = sds.yemekleriGetir(kullaniciadi)
    suspend fun sepettenYemegiSil(sepet_yemek_id: Int, kullanici_adi: String) = sds.sepettenYemegiSil(sepet_yemek_id, kullanici_adi)
}