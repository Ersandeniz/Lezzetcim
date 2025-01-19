package com.example.yemeim.uix.viewModel

import androidx.lifecycle.ViewModel
import com.example.yemeim.data.repo.SepetRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetayViewModel @Inject constructor (var srepo : SepetRepo) : ViewModel() {

    fun yemekEkle(yemek_adi: String, yemek_resim_adi :String,yemek_fiyat : Int,yemek_siparis_adet : Int,kullanici_adi:String){
        CoroutineScope(Dispatchers.Main).launch {
            srepo.YemekEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)

        }
    }
}