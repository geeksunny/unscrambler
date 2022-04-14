package com.radicalninja.unscrambler

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.radicalninja.unscrambler.data.network.ApiWiktionaryService
import com.radicalninja.unscrambler.data.network.ConnectivityInterceptorImpl
import com.radicalninja.unscrambler.data.network.DictionaryDataSourceImpl
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
        val apiService = ApiWiktionaryService(ConnectivityInterceptorImpl(this))
        val dictionaryDataSource = DictionaryDataSourceImpl(apiService)
        dictionaryDataSource.lastFetchedWordData.observe(this, Observer {
            Log.d("API Query Response", it.toString())
            it.result.pages.forEach { (s, pagesEntry) ->
                Log.d("API Query Response", "Title ${pagesEntry.title} - Is a valid word? ${pagesEntry.isValidWord()}")
            }
        })
        GlobalScope.launch {
            dictionaryDataSource.fetchWords("word|notaword")
        }
    }

}