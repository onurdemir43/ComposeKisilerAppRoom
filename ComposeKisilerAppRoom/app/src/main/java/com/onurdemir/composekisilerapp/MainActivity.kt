package com.onurdemir.composekisilerapp

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.onurdemir.composekisilerapp.entity.Kisiler
import com.onurdemir.composekisilerapp.ui.theme.ComposeKisilerAppTheme
import com.onurdemir.composekisilerapp.viewmodel.AnasayfaViewModel
import com.onurdemir.composekisilerapp.viewmodel.KisiDetaySayfaViewModel
import com.onurdemir.composekisilerapp.viewmodelfactory.AnasayfaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeKisilerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SayfaGecisleri()
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeKisilerAppTheme {

    }
}

@Composable
fun SayfaGecisleri() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "anasayfa") {
        composable("anasayfa") {
            Anasayfa(navController = navController)
        }
        composable("kisi_kayit_sayfa") {
            KisiKayitSayfa()
        }
        composable("kisi_detay_sayfa/{kisi}", arguments = listOf(

            navArgument("kisi") {type = NavType.StringType}

        )) {
            val json = it.arguments?.getString("kisi")
            val nesne = Gson().fromJson(json, Kisiler::class.java)
            KisiDetaySayfa(gelenKisi = nesne)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Anasayfa(navController: NavController) {

    val aramaYapiliyorMu = remember { mutableStateOf(false) }
    val tf = remember { mutableStateOf("") }
    val context = LocalContext.current
    val viewModel : AnasayfaViewModel = viewModel(
        factory = AnasayfaViewModelFactory(context.applicationContext as Application)
    )
    val kisilerListesi = viewModel.kisilerListesi.observeAsState(listOf())

    LaunchedEffect(key1 = true) {
        viewModel.kisileriYukle()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                        if (aramaYapiliyorMu.value) {
                            TextField(
                                value = tf.value,
                                onValueChange = {
                                    tf.value = it
                                    viewModel.ara(it) },
                                label = { Text(text = "Ara") },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    focusedLabelColor = Color.White,
                                    focusedIndicatorColor = Color.White,
                                    unfocusedIndicatorColor = Color.White,
                                    textColor = Color.Black
                                )
                                )
                        }else {
                            Text(text = "Kişiler")
                        }

                        },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.teal_200),
                    titleContentColor = colorResource(id = R.color.white)
                ),
                actions = {
                    if (aramaYapiliyorMu.value) {
                        IconButton(onClick = {
                            aramaYapiliyorMu.value = false
                            tf.value = ""
                        }) {
                            Icon(painter = painterResource(
                                id = R.drawable.kapat_resim),
                                contentDescription = "", tint = Color.White)
                        }
                    } else {
                        IconButton(onClick = {
                            aramaYapiliyorMu.value = true
                        }) {
                            Icon(painter = painterResource(
                                id = R.drawable.arama_resim),
                                contentDescription = "", tint = Color.White)
                        }
                    }
                }
            )


        },
        content = {
            LazyColumn {
                items(
                    count = kisilerListesi.value!!.count(),
                    itemContent = {
                        val kisi = kisilerListesi.value[it]
                        Card(modifier = Modifier
                            .padding(all = 5.dp)
                            .fillMaxWidth()) {
                            Row(modifier = Modifier.clickable {
                                val kisiJson = Gson().toJson(kisi)
                                navController.navigate("kisi_detay_sayfa/${kisiJson}")
                            }) {
                                Row(
                                    modifier = Modifier
                                        .padding(all = 10.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "${kisi.kisi_ad} - ${kisi.kisi_tel}")

                                    IconButton(onClick = {
                                        viewModel.sil(kisi.kisi_id)
                                    }) {
                                        Icon(painter = painterResource(
                                            id = R.drawable.sil_resim),
                                            contentDescription = "", tint = Color.Gray)
                                    }

                                }
                            }
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("kisi_kayit_sayfa") },
            containerColor = colorResource(id = R.color.teal_200),
            content = {
                Icon(painter = painterResource(
                    id = R.drawable.ekle_resim),
                    contentDescription = "", tint = Color.White)
            }
                )


        }
    )



}


