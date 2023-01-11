package com.example.bloodpressurechecker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bloodpressurechecker.R
import com.example.bloodpressurechecker.adsConfig.home_native_id
import com.example.bloodpressurechecker.databinding.ActivityMainBinding
import com.example.bloodpressurechecker.databinding.SmallAdUnifiedBinding
import com.example.bloodpressurechecker.utils.Common
import com.example.bloodpressurechecker.utils.exitDialog
import com.example.bloodpressurechecker.utils.getConnectionType
import com.example.bloodpressurechecker.utils.showToast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    var backpressed = 0
    private lateinit var navController: NavController
    var currentNativeAd: NativeAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (getConnectionType() != 0){
            //adToast("Native Ad Request")
            loadNativeAd(home_native_id)
        }
        val bottomNavigationView = binding.bottomNavigation
        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.findNavController()
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onBackPressed() {

        try {
            if (navController.currentDestination!!.id == R.id.homeFragment) {
                exitDialog()
//                    if (backpressed == 0) {
//                        showToast("Press again to exit")
//                        backpressed += 1
//                        CoroutineScope(Dispatchers.Main).launch {
//                            delay(2000)
//                            backpressed = 0
//                        }
//                    } else {
//                        finishAffinity()
//
//                    }

            } else {
                super.onBackPressed()
            }
        } catch (ex: Exception) {}
    }


    private fun loadNativeAd(id:String) {
        val builder = AdLoader.Builder(this@MainActivity,id)
        builder.forNativeAd { nativeAd ->

            Log.d(Common.TAG,"loaded native")
            if (isDestroyed) {
                nativeAd.destroy()
                return@forNativeAd
            }

            currentNativeAd = nativeAd
            val unifiedAdBinding = SmallAdUnifiedBinding.inflate(layoutInflater)
            populateNativeAdView(nativeAd, unifiedAdBinding)
            binding.apply {
                adFrameHome.removeAllViews()
                adFrameHome.addView(unifiedAdBinding.root)
            }
        }
        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                val error = """domain: ${loadAdError.domain}, code: ${loadAdError.code}, message: ${loadAdError.message}""""
                Log.d(Common.TAG, "Native ad failed Home :${error}")
            }
            override fun onAdLoaded() {
                super.onAdLoaded()
                //   adToast("Native Ad Loaded")
            }

            override fun onAdImpression() {
                super.onAdImpression()
              //  addAnalytics("Home Native ")
            }
        }
        ).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun populateNativeAdView(nativeAd: NativeAd, unifiedAdBinding: SmallAdUnifiedBinding) {
        try {

            var nativeAdView = unifiedAdBinding.root
            nativeAdView.headlineView = unifiedAdBinding.adHeadline
            nativeAdView.bodyView = unifiedAdBinding.adBody
            nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
            nativeAdView.iconView = unifiedAdBinding.adAppIcon
            unifiedAdBinding.adHeadline.text = nativeAd.headline

            if (nativeAd.body == null) {
                unifiedAdBinding.adBody.visibility = View.INVISIBLE
            } else {
                unifiedAdBinding.adBody.visibility = View.VISIBLE
                unifiedAdBinding.adBody.text = nativeAd.body
            }

            if (nativeAd.callToAction == null) {
                unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
            } else {
                unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
                unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
            }

            if (nativeAd.icon == null) {
                unifiedAdBinding.adAppIcon.visibility = View.GONE
            } else {
                unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
                unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
            }

            nativeAdView.setNativeAd(nativeAd)
        }catch (ex:Exception){}

    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (currentNativeAd != null) {
                currentNativeAd?.destroy()
                Log.d(Common.TAG,"onDestroy :Home Native")
            }
        }catch (ex:Exception){}

    }

}