package com.example.allprintapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.allprintapp.ui.encomendas.ListaEncomendasFragment
import com.example.allprintapp.ui.filtrosprodutos.FiltroProdutosFragment
import com.example.allprintapp.ui.listaprodutos.DetalhesProdutoFragment
import com.example.allprintapp.ui.listaprodutos.ProdutosRecyclerAdapter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(),
    ProdutosRecyclerAdapter.OnItemClickListener,
    DetalhesProdutoFragment.OnItemClickListener,
    FiltroProdutosFragment.OnItemClickListener,
    ListaEncomendasFragment.OnItemClickListener{



    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var navController: NavController
    private var isActivityVisible = false

    override fun onResume() {
        super.onResume()
        isActivityVisible = true
    }

    override fun onPause() {
        super.onPause()
        isActivityVisible = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController= navHostFragment.navController

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

          navView    = findViewById(R.id.nav_view)
       // val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                //R.id.nav_categorias,
                R.id.nav_loja,
                // R.id.nav_pesquisa,
               // R.id.nav_logout
            ),
            drawerLayout
        )

        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController)

        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)


        visibilityNavElements(navController)//If you want to hide drawer or bottom navigation configure that in this function

        /**https://stackoverflow.com/questions/3007998/on-logout-clear-activity-history-stack-preventing-back-button-from-opening-l */
        /**faz logout fazendo a limpeza da sessão e volta para a login activity */


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nav_logout) {
                Toast.makeText(this@MainActivity, "RSAIRRRRRRR", Toast.LENGTH_SHORT).show()
                exitApp()
                finish()
                //logout()
            }
        }
    }

    private fun visibilityNavElements(navController: NavController) {

        //Listen for the change in fragment (navigation) and hide or show drawer or bottom navigation accordingly if required
        //Modify this according to your need
        //If you want you can implement logic to deselect(styling) the bottom navigation menu item when drawer item selected using listener

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_categorias -> hideBothNavigation()
                R.id.nav_loja -> hideBottomNavigation()
                R.id.nav_home -> hideBottomNavigation()
                R.id.nav_pesquisa -> hideBottomNavigation()
                else -> showBothNavigation()
            }
        }

    }


    private fun hideBothNavigation() { //Hide both drawer and bottom navigation bar
        bottomBar?.visibility = View.GONE
        fab?.visibility = View.GONE
        navView.visibility = View.GONE
        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    private fun hideBottomNavigation() { //Hide bottom navigation
        bottomBar?.visibility = View.GONE
        fab?.visibility = View.GONE
        navView.visibility = View.VISIBLE
        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED) //To unlock navigation drawer

       // main_navigation_view?.setupWithNavController(navController) //Setup Drawer navigation with navController
    }

    private fun showBothNavigation() {
        bottomBar?.visibility = View.VISIBLE
        fab?.visibility = View.VISIBLE
        navView.visibility = View.VISIBLE
        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setupNavControl() //To configure navController with drawer and bottom navigation
    }

    private fun setupNavControl() {
        navView.setupWithNavController(navController) //Setup Drawer navigation with navController
        bottomBar?.setupWithNavController(navController) //Setup Bottom navigation with navController
    }
    //Criar menu definiçoes top
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
*/


    override fun onBackPressed() {
        when { //If drawer layout is open close that on back pressed
            drawer_layout.isDrawerOpen(GravityCompat.START) -> {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            else -> {
                super.onBackPressed() //If drawer is already in closed condition then go back
            }
        }
    }



//    override fun onBackPressed() {
//        if(navController.currentDestination!!.id!=R.id.nav_host_fragment){
//            navController.navigate(R.id.nav_host_fragment)
//        }else{
//            super.onBackPressed()
//        }
//    }
//
//    private fun exitApp() { //To exit the application call this function from fragment
//        exitProcess(-1)
//    }
//
    private fun exitApp() { //To exit the application call this function from fragment
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        this.startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean { //Setup appBarConfiguration for back arrow
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
//                || super.onOptionsItemSelected(item)
//    }



    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}


class DoAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }







}
