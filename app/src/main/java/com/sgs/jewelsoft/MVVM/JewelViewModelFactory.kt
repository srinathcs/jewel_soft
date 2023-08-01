package com.sgs.jewelsoft.MVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JewelViewModelFactory(private val jewelSoftRepository: JewelSoftRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JewelSoftViewModel(jewelSoftRepository) as T
    }
}