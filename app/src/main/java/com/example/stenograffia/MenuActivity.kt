package com.example.stenograffia

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import com.example.stenograffia.ui.data.Models.User
import com.example.stenograffia.ui.data.firebase.*
import de.hdodenhof.circleimageview.CircleImageView

class MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
//        val userImg: CircleImageView = findViewById(R.id.user_img_menu)
        val userImg = navView.getHeaderView(0).findViewById<CircleImageView>(R.id.user_img_menu)
        val userName = navView.getHeaderView(0).findViewById<TextView>(R.id.user_name)

        initFirebase()
        REF_DATABASE_ROOT.child(NODE_USERS).child(AUTH.currentUser!!.uid)
            .addListenerForSingleValueEvent(
                AppValueEventListener {
                    val userFromFirebase = it.getValue(User::class.java)
                    userName.text = userFromFirebase!!.name
                    Log.d("UP/IN/OUT", "uri:" + userFromFirebase.imgUri)
                    userImg.downloadAndSetImage(userFromFirebase.imgUri)
                }
            )
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profile, R.id.nav_map, R.id.nav_routes,
                R.id.nav_surface_exchange, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}