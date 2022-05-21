package com.geekbrains.nasaexplorer.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.geekbrains.nasaexplorer.R
import com.geekbrains.nasaexplorer.common.EMPTY_INT
import com.geekbrains.nasaexplorer.common.THEME_ID
import com.geekbrains.nasaexplorer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //clearPreferences()
        setCurrentTheme(getPreferences(Context.MODE_PRIVATE).getInt(THEME_ID, EMPTY_INT))
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_activity_fragment_container) as NavHostFragment
        setupActionBarWithNavController(
            navHostFragment.navController,
            AppBarConfiguration(
                setOf(
                    R.id.navigation_home,
                    R.id.navigation_asteroids,
                    R.id.navigation_settings
                )
            )
        )
        binding.mainActivityBottomNavigation
            .setupWithNavController(navHostFragment.navController)
    }

    private fun setCurrentTheme(themeId: Int) {
        if (themeId != EMPTY_INT) setTheme(themeId)
    }

    private fun clearPreferences() {
        getPreferences(Context.MODE_PRIVATE).edit().clear().apply()
    }

}