package com.sgs.jewelsoft.activity

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.sgs.jewelsoft.Location.Constant
import com.sgs.jewelsoft.Location.FusedLocationService
import com.sgs.jewelsoft.Location.MyReceiver
import com.sgs.jewelsoft.Location.displayLocationSettingsRequest
import com.sgs.jewelsoft.Location.getLocationStatus
import com.sgs.jewelsoft.MVVM.JewelSoftViewModel
import com.sgs.jewelsoft.MainPreference
import com.sgs.jewelsoft.R
import com.sgs.jewelsoft.databinding.ActivityDashBoardBinding

class DashBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashBoardBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var jewelSoftVM: JewelSoftViewModel
    private lateinit var mainPreference: MainPreference
    private var pendingNotifications = 0
    private lateinit var myReceiver: MyReceiver
    private var hasLocationPermission = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.materialToolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment),
            binding.myDrawerLayout
        )

        myReceiver = MyReceiver()

        binding.materialToolbar.setNavigationIconTint(ContextCompat.getColor(this, R.color.white))

        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
        }

        getLocation()

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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