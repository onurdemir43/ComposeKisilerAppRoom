package com.onurdemir.composekisilerapp.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onurdemir.composekisilerapp.viewmodel.AnasayfaViewModel
import com.onurdemir.composekisilerapp.viewmodel.KisiKayitSayfaViewModel

class KisiKayitSayfaViewModelFactory(var application: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return KisiKayitSayfaViewModel(application) as T
    }
}
