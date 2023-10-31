package com.onurdemir.composekisilerapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.onurdemir.composekisilerapp.repository.KisilerDaoRepository

class KisiDetaySayfaViewModel(application: Application) : AndroidViewModel(application) {
    var krepo = KisilerDaoRepository(application)

    fun guncelle(kisi_id:Int, kisi_ad:String, kisi_tel:String) {
        krepo.kisiGuncelle(kisi_id, kisi_ad, kisi_tel)
    }
}