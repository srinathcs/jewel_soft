package com.sgs.jewelsoft.fragment.logout

import com.sgs.jewelsoft.MainPreference

class Logoutrepositary (private val mainPreference: MainPreference) {

    suspend fun clearValues() = mainPreference.clearValues()

}