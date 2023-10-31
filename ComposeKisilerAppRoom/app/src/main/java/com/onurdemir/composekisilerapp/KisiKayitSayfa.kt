package com.onurdemir.composekisilerapp

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.onurdemir.composekisilerapp.viewmodel.KisiKayitSayfaViewModel
import com.onurdemir.composekisilerapp.viewmodelfactory.KisiKayitSayfaViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KisiKayitSayfa() {

    val tfKisiAd = remember { mutableStateOf("") }
    val tfKisiTel = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current
    val viewModel : KisiKayitSayfaViewModel = viewModel(
        factory = KisiKayitSayfaViewModelFactory(context.applicationContext as Application)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Kişi Kayıt") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.teal_200),
                    titleContentColor = colorResource(id = R.color.white)
                )
            )


        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextField(
                    value = tfKisiAd.value,
                    onValueChange = { tfKisiAd.value = it },
                    label = { Text(text = "Kişi Ad")})

                TextField(
                    value = tfKisiTel.value,
                    onValueChange = { tfKisiTel.value = it },
                    label = { Text(text = "Kişi Tel")})

                Button(onClick = {

                    val kisi_ad = tfKisiAd.value
                    val kisi_tel = tfKisiTel.value
                    viewModel.kayit(kisi_ad, kisi_tel)

                    localFocusManager.clearFocus()

                }, modifier = Modifier.size(250.dp, 50.dp)) {
                    Text(text = "KAYDET")
                }

            }
        }
    )
}