package com.radicalninja.unscrambler

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.radicalninja.unscrambler.data.network.ApiWiktionaryService
import com.radicalninja.unscrambler.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_history, R.id.navigation_settings))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Testing API call
        GlobalScope.launch {
            val result = ApiWiktionaryService().getWords("word|notaword")
            if (result != null) {
                Log.d("API Query Response", result.body().toString())
                result.body()?.result?.pages?.forEach { (s, pagesEntry) ->
                    Log.d("API Query Response", "Title ${pagesEntry.title} - Is a valid word? ${pagesEntry.isValidWord()}")
                }
            } else {
                Log.d("API Query Response", "result is null!")
            }
        }
    }

}