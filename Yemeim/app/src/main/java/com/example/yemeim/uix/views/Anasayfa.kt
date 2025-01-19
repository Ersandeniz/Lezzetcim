
package com.example.yemeim.uix.views
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material3.Text

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text


import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextField

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.yemeim.R
import com.example.yemeim.data.entity.YemekRes
import com.example.yemeim.data.entity.Yemekler
import com.example.yemeim.uix.viewModel.AnasayfaViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Anasayfa(navController: NavController, anasayfaViewModel: AnasayfaViewModel) {
    val aramayapiliyorMu = remember { mutableStateOf(false) }
    val tf = remember { mutableStateOf("") }

    val yemeklerListesi = anasayfaViewModel.yemeklerListesi.observeAsState(listOf())
    var filteredYemeklerListesi by remember { mutableStateOf(listOf<Yemekler>()) }

    // İlk değer olarak tüm listeyi ata
    LaunchedEffect(tf.value) {
        filteredYemeklerListesi = if (tf.value.isEmpty()) {
            yemeklerListesi.value
        } else {
            yemeklerListesi.value.filter {
                it.yemek_adi.startsWith(tf.value, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (aramayapiliyorMu.value) {
                        TextField(
                            value = tf.value,
                            onValueChange = { newValue ->
                                tf.value = newValue
                                // Arama sorgusuna göre listeyi filtrele
                                filteredYemeklerListesi = if (tf.value.isEmpty()) {
                                    yemeklerListesi.value
                                } else {
                                    yemeklerListesi.value.filter {
                                        it.yemek_adi.contains(tf.value, ignoreCase = true)
                                    }
                                }
                            },
                            label = { Text(text = "Ara") },
                            colors = TextFieldDefaults.textFieldColors(

                                focusedIndicatorColor = Color.Red,
                                unfocusedIndicatorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                    } else {
                        Text(text = "Lezzetçim")
                    }
                },
                actions = {
                    if (aramayapiliyorMu.value) {
                        IconButton(onClick = {
                            aramayapiliyorMu.value = false
                            tf.value = ""
                            filteredYemeklerListesi = yemeklerListesi.value
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.kapat_resim),
                                contentDescription = "Kapat"
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            aramayapiliyorMu.value = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ara_resim),
                                contentDescription = "Ara"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            MyBottomBar()
        },
        floatingActionButton = {
            FloatingActionButton(

                onClick = {
                    val yemekJson = Gson().toJson(anasayfaViewModel.YemekleriGetir())
                    navController.navigate("SepetimSayfa/$yemekJson")
                },
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.shopping_cart),
                    contentDescription = "add",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(12.dp)
                .padding(paddingValues)
        ) {
            items(filteredYemeklerListesi.size) { index ->
                val yemek = filteredYemeklerListesi[index]
                Card(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth()
                        .clickable {
                            val yemekJson = Gson().toJson(yemek)
                            navController.navigate("detaySayfa/$yemekJson")
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val resimUrl =
                            "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
                        GlideImage(
                            imageModel = resimUrl,
                            modifier = Modifier.size(140.dp, 160.dp),
                            failure = { Text(text = "Görsel yüklenemedi") }
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "${yemek.yemek_adi}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "${yemek.yemek_fiyat} TL",
                            color = Color.Blue,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

/*fun onCartClick(navController: NavController , anasayfaViewModel:AnasayfaViewModel) {
    val yemekJson = Gson().toJson(anasayfaViewModel.YemekleriGetir())
    navController.navigate("SepetimSayfa/$yemekJson")
} */

@Composable
fun MyBottomBar(){

    val bottomMenuItemsList = prepareBottomMenu()
    val contextForToast = LocalContext.current.applicationContext
    var selectedItem by remember {

        mutableStateOf("Home")
    }
    BottomAppBar(
        cutoutShape = CircleShape,
        backgroundColor = Color(android.graphics.Color.parseColor("#f8f8f8")),
        elevation = 3.dp
    ) {
        bottomMenuItemsList.forEachIndexed{index, bottomMenuItem ->
            if (index ==2){
                BottomNavigationItem(selected = false
                    , onClick = { /*TODO*/ },
                    icon = { /*TODO*/ },
                    enabled = false
                )
            }
            BottomNavigationItem(selected =(selectedItem==bottomMenuItem.label) , onClick = {
                selectedItem=bottomMenuItem.label
                Toast.makeText(contextForToast , bottomMenuItem.label , Toast.LENGTH_SHORT).show()
            }, icon = {
                Icon(painter = bottomMenuItem.icon, contentDescription = bottomMenuItem.label,
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                )
            },
                label = {
                    Text(
                        text = bottomMenuItem.label,
                        modifier = Modifier.padding(top = 14.dp)

                    )
                }, alwaysShowLabel = true ,
                enabled = true
            )
        }

    }

}

data class BottomMenuItem(
    val label: String,
    val icon: Painter,
    val onClick: () -> Unit
)
@Composable
fun prepareBottomMenu( ): List<BottomMenuItem> {
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Home",
            icon = painterResource(id = R.drawable.bottom_btn1),
            onClick = { }
        )
    )

    /*bottomMenuItemList.add(
        BottomMenuItem(
            label = "Support",
            icon = painterResource(id = R.drawable.bottom_btn3),
            onClick = { /* Support butonu için yapılacak işlem */ }
        )
    )*/
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Settings",
            icon = painterResource(id = R.drawable.bottom_btn4),
            onClick = { /* Settings butonu için yapılacak işlem */ }
        )
    )
    return bottomMenuItemList
}