package com.onurdemir.composekisilerapp.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onurdemir.composekisilerapp.viewmodel.AnasayfaViewModel
import com.onurdemir.composekisilerapp.viewmodel.KisiDetaySayfaViewModel

class KisiDetaySayfaViewModelFactory(var application: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return KisiDetaySayfaViewModel(application) as T
    }
}
