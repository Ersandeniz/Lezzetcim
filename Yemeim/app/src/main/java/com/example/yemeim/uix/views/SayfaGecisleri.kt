package com.example.yemeim.uix.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yemeim.data.entity.SepetteYemekler
import com.example.yemeim.uix.viewModel.AnasayfaViewModel
import com.example.yemeim.uix.viewModel.DetayViewModel
import com.example.yemeim.uix.viewModel.SepetimViewModel
import com.google.gson.Gson

@Composable
fun SayfaGecisleri(anasayfaViewModel: AnasayfaViewModel,
                   yemeklerDetayViewModel: DetayViewModel,
                   yemeklerSepetimViewModel: SepetimViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "anasayfa") {
        composable("anasayfa") {
            Anasayfa(navController, anasayfaViewModel)
        }
        composable("detaySayfa/{yemekJson}",
            arguments = listOf(
                navArgument("yemekJson") { type = NavType.StringType }
            )
        ) {
            val json = it.arguments?.getString("yemekJson")
            val nesne = Gson().fromJson(json, SepetteYemekler::class.java)
            if (nesne != null) {
                // Yemek bilgilerini ekrana yazdır
                DetaySayfa(nesne, navController, yemeklerDetayViewModel)
            } else {

            }

        }
        composable("SepetimSayfa/{yemek}",
            arguments = listOf(
                navArgument("yemek") { type = NavType.StringType }
            )
        ) {
            val json2 = it.arguments?.getString("yemek")
            val nesne2 = Gson().fromJson(json2, SepetteYemekler::class.java)

            if (nesne2 != null) {
                SepetimSayfa(nesne2, yemeklerSepetimViewModel)
            } else {


            }
        }


    }
}