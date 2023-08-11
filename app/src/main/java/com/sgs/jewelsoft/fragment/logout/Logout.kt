package com.sgs.jewelsoft.fragment.logout

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sgs.jewelsoft.MainPreference
import com.sgs.jewelsoft.R
import com.sgs.jewelsoft.activity.LoginActivity
import com.sgs.jewelsoft.databinding.FragmentLogoutBinding


import java.io.File

class Logout : Fragment(R.layout.fragment_logout) {

    private lateinit var binding: FragmentLogoutBinding
    private lateinit var logoutVM: LogoutVM
    private lateinit var mainPreference: MainPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLogoutBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainPreference = MainPreference(requireContext())
        val repo = Logoutrepositary(mainPreference)

        val factory = LogoutFactory(repo)
        logoutVM = ViewModelProvider(this, factory)[LogoutVM::class.java]

        binding.btnLogout.setOnClickListener {

            logoutVM.clearValues()
            Log.i("TAG", "Logout:${logoutVM.clearValues()}")

            requireActivity().finish()

            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

    private fun deleteCache() {
        try {
            val dir: File = requireContext().cacheDir
            deleteDir(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children: Array<String> = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }

    private fun getStatusFromPosition(position: Boolean): Int {

        return if (position) {
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        } else {
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        }
    }


}