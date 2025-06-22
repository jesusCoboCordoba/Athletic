package com.politecnico.athleticapp

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.politecnico.athleticapp.databinding.ActivityMainBinding
import com.politecnico.athleticapp.ui.menu.NavItem
import com.politecnico.athleticapp.ui.menu.NavigationRVAdapter
import androidx.navigation.NavOptions
import com.google.firebase.auth.FirebaseAuth
import com.politecnico.athleticapp.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navViewAdapter: NavigationRVAdapter
    private lateinit var navController: NavController // Hacerlo miembro de la clase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return // Evita que el resto del onCreate se ejecute
        }

        // Modern way to handle edge-to-edge and status bar appearance
        WindowCompat.setDecorFitsSystemWindows(window, false) // Enable edge-to-edge

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color and icons using modern API
        window.statusBarColor = Color.TRANSPARENT // Or Color.WHITE if you don't want full transparency initially
        val insetsController = WindowInsetsControllerCompat(window, binding.root)
        insetsController.isAppearanceLightStatusBars = true // True for dark icons on light background

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main) // Inicializar aquí

        // Listener para el icono de cerrar en el nav_header
        val headerView = navView.getHeaderView(0)
        val closeButton = headerView.findViewById<ImageView>(R.id.nav_close_icon)
        closeButton?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home_main, R.id.nav_workouts, R.id.nav_meal_plans, R.id.nav_progress_tracking, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // navView.setupWithNavController(navController) // Ya no usamos esto directamente para el menú

        setupRecyclerView(navView, drawerLayout)

        // Observar cambios de destino para actualizar el ítem seleccionado en el RecyclerView
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navViewAdapter.setSelectedItem(destination.id)
        }
    }

    private fun setupRecyclerView(navView: NavigationView, drawerLayout: DrawerLayout) {
        // Obtener la cabecera primero
        val headerView = navView.getHeaderView(0)
        // Luego encontrar el RecyclerView dentro de la cabecera
        val recyclerView = headerView.findViewById<RecyclerView>(R.id.nav_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val navItems = listOf(
            NavItem(R.id.nav_home_main, R.string.menu_home, R.drawable.ic_home_material, true),
            NavItem(R.id.nav_workouts, R.string.menu_workouts, R.drawable.ic_workouts_placeholder),
            NavItem(R.id.nav_meal_plans, R.string.menu_meal_plans, R.drawable.ic_meal_plans_placeholder),
            NavItem(R.id.nav_progress_tracking, R.string.menu_progress_tracking, R.drawable.ic_progress_placeholder),
            NavItem(R.id.nav_settings, R.string.menu_settings, R.drawable.ic_settings_placeholder),
            NavItem(R.id.nav_logout, R.string.menu_logout, R.drawable.ic_logout_placeholder)
        )

        navViewAdapter = NavigationRVAdapter(navItems) { selectedNavItem ->
            val currentDestinationId = navController.currentDestination?.id
            if (currentDestinationId != selectedNavItem.id) {
                if (selectedNavItem.id == R.id.nav_logout) {
                    auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    return@NavigationRVAdapter
                }

                showLoading()
                // Use a when block for explicit navigation actions
                when (selectedNavItem.id) {
                    R.id.nav_home_main -> navController.popBackStack(R.id.nav_home_main, false)
                    R.id.nav_workouts -> navController.navigate(R.id.nav_workouts)
                    R.id.nav_settings -> navController.navigate(R.id.nav_settings)
                    R.id.nav_meal_plans -> navController.navigate(R.id.nav_meal_plans)
                    R.id.nav_progress_tracking -> navController.navigate(R.id.nav_progress_tracking)
                    else -> navController.navigate(selectedNavItem.id)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        recyclerView.adapter = navViewAdapter

        // Establecer la selección inicial basada en el destino actual (si la app no empieza en home)
        navController.currentDestination?.id?.let {
            navViewAdapter.setSelectedItem(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> { // Este es el ID del ítem en res/menu/main.xml
                navController.navigate(R.id.nav_settings) // Este es el ID del destino en mobile_navigation.xml
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun showLoading() {
        binding.loadingOverlay.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.loadingOverlay.visibility = View.GONE
    }
}