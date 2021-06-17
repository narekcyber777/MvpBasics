package com.narcyber.mvpbasics.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.narcyber.mvpbasics.R
import com.narcyber.mvpbasics.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {
    private lateinit var root: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        root = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(root.root)
        inIt()
    }

    private fun inIt() {
        setNavGraph()

    }

    private fun setNavGraph() {
        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph_weather, intent.extras)
    }

}