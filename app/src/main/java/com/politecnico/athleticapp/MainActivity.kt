package com.politecnico.athleticapp

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navViewAdapter: NavigationRVAdapter
    private lateinit var navController: NavController // Hacerlo miembro de la clase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color and icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = Color.WHITE
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.WHITE
        }

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                // .setAnchorView(R.id.fab) // Considerar quitar si el FAB no está o está oculto
                .show()
        }
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
            NavItem(R.id.nav_home_main, R.string.menu_home, R.drawable.ic_home_material, true), // Marcar home como seleccionado inicialmente
            NavItem(R.id.nav_workouts, R.string.menu_workouts, R.drawable.ic_workouts_placeholder), // Reemplaza R.string y R.drawable
            NavItem(R.id.nav_meal_plans, R.string.menu_meal_plans, R.drawable.ic_meal_plans_placeholder),
            NavItem(R.id.nav_progress_tracking, R.string.menu_progress_tracking, R.drawable.ic_progress_placeholder),
            NavItem(R.id.nav_settings, R.string.menu_settings, R.drawable.ic_settings_placeholder)
        )

        navViewAdapter = NavigationRVAdapter(navItems) { selectedNavItem ->
            // Navegar al destino y cerrar el drawer
            if (navController.currentDestination?.id != selectedNavItem.id) {
                val navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.custom_fade_in)
                    .setExitAnim(R.anim.custom_fade_out)
                    .setPopEnterAnim(R.anim.custom_fade_in)
                    .setPopExitAnim(R.anim.custom_fade_out)
                    .build()
                navController.navigate(selectedNavItem.id, null, navOptions)
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            // La selección visual se actualiza mediante addOnDestinationChangedListener
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