package com.example.yemeim.uix.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yemeim.data.entity.YemekRes
import com.example.yemeim.data.entity.Yemekler
import com.example.yemeim.data.repo.YemekRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AnasayfaViewModel  @Inject constructor(var yrepo : YemekRepo) : ViewModel(){
    private val _yemeklerListesi = MutableLiveData<List<YemekRes>>()
    private val _filteredYemeklerListesi = MutableLiveData<List<YemekRes>>()
    val filteredYemeklerListesi: LiveData<List<YemekRes>> get() = _filteredYemeklerListesi

    var yemeklerListesi = MutableLiveData<List<Yemekler>>()

    init {
        YemekleriGetir()
    }
    fun YemekleriGetir(){

        CoroutineScope(Dispatchers.Main).launch {
            yemeklerListesi.value =  yrepo.yemekleriGetir()

        }
    }
    fun filterYemekler(query: String) {
        _filteredYemeklerListesi.value = _yemeklerListesi.value?.filter {
            it.yemek_adi.contains(query, ignoreCase = true)
        }
    }

    fun resetYemekler() {
        _filteredYemeklerListesi.value = _yemeklerListesi.value
    }
}