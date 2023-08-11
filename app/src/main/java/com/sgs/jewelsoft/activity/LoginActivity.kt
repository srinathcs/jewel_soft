package com.sgs.jewelsoft.activity

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sgs.jewelsoft.Location.Constant
import com.sgs.jewelsoft.Location.FusedLocationService
import com.sgs.jewelsoft.Location.MyReceiver
import com.sgs.jewelsoft.Location.displayLocationSettingsRequest
import com.sgs.jewelsoft.Location.getLocationStatus
import com.sgs.jewelsoft.R
import com.sgs.jewelsoft.Resources
import com.sgs.jewelsoft.databinding.ActivityLoginBinding
import com.sgs.jewelsoft.MVVM.JewelSoftRepository
import com.sgs.jewelsoft.MVVM.JewelSoftViewModel
import com.sgs.jewelsoft.MVVM.JewelViewModelFactory
import com.sgs.jewelsoft.MainPreference

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var jewelSoftVM: JewelSoftViewModel
    private lateinit var mainPreference: MainPreference
    private var pendingNotifications = 0
    private lateinit var myReceiver: MyReceiver
    private var hasLocationPermission = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var lt =""
    private var ln =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setStatusBarColor(R.color.theme)

        mainPreference = MainPreference(this@LoginActivity)

        FusedLocationService.latitudeFlow.observe(this) {
            lt = it.latitude.toString()
            ln = it.longitude.toString()
            Log.i("TAG", "onCreateL:$lt")
            Log.i("TAG", "onCreateLo:$ln")

        }

        getLocation()

        myReceiver = MyReceiver()

        binding.btnRegister.setOnClickListener {
           /* val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)*/
        }
        binding.btnLogin.setOnClickListener {
            login()
        }
        val repos = JewelSoftRepository()
        val factory = JewelViewModelFactory(repos)
        jewelSoftVM = ViewModelProvider(this, factory)[JewelSoftViewModel::class.java]
    }

    private fun login() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.data(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString(),
                "1",
                "21472147"
            )
            validationStatus()
        }
    }

    private fun validationStatus() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.testLoginFlow.collect {
                when (it) {
                    is Resources.Error -> {
                        Log.i("TAG", "validate_for_error: ${it.message.toString()}")

                    }

                    is Resources.Loading -> {

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "validationStatus: ${it.data}")
                        if (it.data!!.error == false) {
                            if (it.data!!.error_msg == "Login Successfull") {

                                mainPreference.saveLogin(true)

                                val role = it.data.role_id
                                val name = it.data.uname
                                val phone = it.data.com.phone
                                val cid = it.data.cid
                                val uid = it.data.uid

                                mainPreference.saveUserName(name)
                                mainPreference.saveRoleId(role)
                                mainPreference.savePhoneMobileNo(phone)
                                mainPreference.saveCId(cid)
                                mainPreference.saveUserId(uid)

                                val intent =
                                    Intent(this@LoginActivity, DashBoardActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Toast.makeText(
                                this@LoginActivity, "Your login is wrong", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setStatusBarColor(colorResId: Int) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Set the status bar color
            window.statusBarColor = resources.getColor(colorResId, theme)
        }
    }


    private fun getLocation() {

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->

                hasLocationPermission =
                    permission[Manifest.permission.ACCESS_FINE_LOCATION] ?: hasLocationPermission

                if (!hasLocationPermission) {
                    finish()

                } else {
                    showLocationServicePermission()
                    startLocationService()
                }

            }

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
            )
        )

    }

    private fun startLocationService() {

        Intent(this, FusedLocationService::class.java).also {
            it.action = Constant.ACTION_START_FUSED_SERVICE
            startService(it)
        }

    }

    private fun stopLocationServices() {

        Intent(this, FusedLocationService::class.java).also {
            it.action = Constant.ACTION_STOP_FUSED_SERVICE
            startService(it)
        }

    }

    private fun showLocationServicePermission() {
        if (!getLocationStatus(this)) {
            displayLocationSettingsRequest(this, this)
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationService()

    }


    override fun onPause() {
        super.onPause()
        stopLocationServices()
    }

    override fun onStart() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(myReceiver, intentFilter)
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
    }
    companion object {
        private const val FLEXIBLE_APP_UPDATE_REQ_CODE = 123
    }


}