package com.example.yemeim.uix.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yemeim.data.entity.SepetteYemekler
import com.example.yemeim.uix.viewModel.DetayViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetaySayfa(gelenYemek: SepetteYemekler, navController: NavController, yemeklerDetayViewModel: DetayViewModel) {
    var adet by remember { mutableStateOf(1) }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = gelenYemek.yemek_adi, color = Color.Gray) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF00BFA5) // Turkuaz
            )
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF8E1)) // Hafif krem arka plan
                .padding(4.dp)
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(18.dp))

            val resimUrl =
                "http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}"
            GlideImage(imageModel = resimUrl,
                modifier = Modifier.size(200.dp, 250.dp),
                failure = {
                    Text("Görsel Yüklenemedi")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${gelenYemek.yemek_fiyat}₺", fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFFFC107) // Altın Sarısı
            )
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = gelenYemek.yemek_adi,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00BFA5) // Turkuaz
            )
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { if (adet > 1) adet-- },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00BFA5) // Turkuaz
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("-", color = Color.White, fontSize = 24.sp)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(text = "$adet", fontSize = 20.sp)

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { adet++ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00BFA5) // Turkuaz
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("+", color = Color.White, fontSize = 24.sp)
                }
            }
            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
            )

            val toplamFiyat = gelenYemek.yemek_fiyat * adet
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                // İlk açıklama kartı
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF797977) // Yeşil
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.White)
                ) {
                    Text(
                        text = "20-25 dk Teslimat",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // İkinci açıklama kartı
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF797977) // Sarı
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.Gray)
                ) {
                    Text(
                        text = "Ücretsiz Teslimat",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }

            Row(

                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$toplamFiyat ₺",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFC107) // Altın Sarısı
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        yemeklerDetayViewModel.yemekEkle(
                            yemek_adi = gelenYemek.yemek_adi,
                            yemek_resim_adi = gelenYemek.yemek_resim_adi,
                            yemek_fiyat = gelenYemek.yemek_fiyat,
                            yemek_siparis_adet = adet,
                            kullanici_adi = "ersandeniz",
                        )


                        Toast.makeText(navController.context, "Sepete Eklendi", Toast.LENGTH_SHORT).show()


                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00BFA5), // Turkuaz
                        contentColor = Color.White // Yazı rengi
                    ),
                    shape = RoundedCornerShape(30.dp), // Butonun köşe yumuşatması
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    Text(
                        text = "Sepete Ekle",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

        }
    }
}
