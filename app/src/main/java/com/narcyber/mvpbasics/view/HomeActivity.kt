package com.narcyber.mvpbasics.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.narcyber.mvpbasics.R
import com.narcyber.mvpbasics.databinding.ActivityHomeBinding
import com.narcyber.mvpbasics.model.User

class HomeActivity : AppCompatActivity() {
    companion object {
        var user: User? = null
    }

    lateinit var root: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        root = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(root.root)
        inIt()
    }

    private fun inIt() {
        if (intent.hasExtra(getString(R.string.key_id))) {
            user = intent.getSerializableExtra(getString(R.string.key_id)) as User?
            setUpNavigation()
        }
    }

    private fun setUpNavigation() {
        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupWithNavController(root.navBarBottom, navController)
    }
}