package com.micheladrien.fresquerappel


import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.micheladrien.fresquerappel.manager.MainDataManager
import com.micheladrien.fresquerappel.manager.SettingsManager
import com.micheladrien.fresquerappel.tools.JsonReader
import java.io.IOException
import java.io.InputStream


class Main_activity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val dataManager = MainDataManager(this)

        settingsManager = SettingsManager(this, dataManager)
        //Nous definissons le ViewModel
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        settingsManager.addWaitingVM(mainViewModel)

        //On définit le menu latéral
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_navigation_activity)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        /* Défini menu lateral -- Debut */
        /* 1.3 : Retour arrière, la navigation latéralle ne sert désormais plus à changer le nom de la fresque
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_main //Cet Id doit obligatoirement se trouver dans le fichier mobile_navigation.xml
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController) */

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_main, R.id.nav_single, R.id.nav_timer, R.id.nav_notes
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /* 1.3 retour arrière
        navView.setNavigationItemSelectedListener { menuItem ->
            mainViewModel.changeCollage(menuItem.title.toString())
            drawerLayout.closeDrawers()
            true
        } */
        /* Défini menu lateral -- Fin */

        //La VM change le nom de la fresque active
        mainViewModel.name.observe(this, Observer {
            val actionBar: ActionBar? = supportActionBar
            actionBar?.setTitle(it.toString())
        })

    }

    //Creation menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    //Affichage du menu
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}