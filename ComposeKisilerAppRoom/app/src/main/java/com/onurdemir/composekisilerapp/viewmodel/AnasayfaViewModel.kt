package com.onurdemir.composekisilerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onurdemir.composekisilerapp.entity.Kisiler
import com.onurdemir.composekisilerapp.repository.KisilerDaoRepository

class AnasayfaViewModel(application: Application) : AndroidViewModel(application) {
    var krepo = KisilerDaoRepository(application)
    var kisilerListesi = MutableLiveData<List<Kisiler>>()

    init {
        kisileriYukle()
        kisilerListesi = krepo.kisilerGetir()

    }

    fun kisileriYukle() {
        krepo.tumKisileriAl()
    }

    fun ara(aramaKelimesi:String) {
        krepo.kisiAra(aramaKelimesi)
    }

    fun sil(kisi_id:Int) {
        krepo.kisiSil(kisi_id)
    }
}