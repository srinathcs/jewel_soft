package com.sgs.jewelsoft.fragment.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LogoutFactory(private val repositary: Logoutrepositary): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LogoutVM(repositary) as T
    }
}