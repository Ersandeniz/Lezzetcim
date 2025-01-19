package com.example.yemeim.uix.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yemeim.data.entity.SepetteYemekler
import com.example.yemeim.data.repo.SepetRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SepetimViewModel @Inject constructor (var srepo : SepetRepo) : ViewModel() {
    var sListe = MutableLiveData<List<SepetteYemekler>>()
    var toplamFiyat = MutableLiveData<Int>()
    init {
        yemekleriGetir("ersandeniz")
    }






    fun yemekleriGetir(kullaniciadi:String){
        CoroutineScope(Dispatchers.Main).launch {
            sListe.value = srepo.yemekleriGetir(kullaniciadi)
            toplamTutar()
        }
    }



    fun toplamTutar(){
        val toplam = sListe.value?.sumOf { it.yemek_fiyat * it.yemek_siparis_adet }
        toplamFiyat.value = toplam?:0
    }
    fun sepettenYemegiSil(sepet_yemek_id : Int, kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch{
            srepo.sepettenYemegiSil(sepet_yemek_id, kullanici_adi)
            yemekleriGetir("ersandeniz")
            toplamTutar()
        }
    }


}