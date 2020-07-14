package com.example.allprintapp

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.allprintapp.Login.PreferenceHelper
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment
import com.example.allprintapp.ui.listaprodutos.ProdutosRecyclerAdapter
import com.example.allprintapp.ui.slideshow.SlideshowFragment
import com.google.android.material.navigation.NavigationView
import java.util.HashMap

class MainActivity : AppCompatActivity(),ProdutosRecyclerAdapter.OnItemClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private var preferenceHelper: PreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

     /**   getJSONListagemDistrito()*/

        //toolbar.isActivated = true
       // setSupportActionBar(bottom_app_bar)
//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
          navView    = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_loja, R.id.nav_slideshow, R.id.nav_logout), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mDrawerLayout = findViewById(R.id.drawer_layout)

 /**       //val navigationView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            // Handle navigation view item clicks here.
            when (menuItem.itemId) {

                R.id.nav_home -> {
                    Toast.makeText(this, "Home", Toast.LENGTH_LONG).show()
                }
                R.id.nav_loja -> {
                    Toast.makeText(this,"Loja", Toast.LENGTH_LONG).show()
                    val fragment: ListagemProdutosFragment?=ListagemProdutosFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    if (fragment != null) {
                        transaction.replace(R.id.nav_host_fragment, fragment)
                    }
                    transaction.commit()
                }
                R.id.nav_slideshow -> {
                    Toast.makeText(this,"slide", Toast.LENGTH_LONG).show()
                    val fragment: SlideshowFragment? = SlideshowFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    if (fragment != null) {
                        transaction.replace(R.id.nav_host_fragment, fragment)
                    }
                    transaction.commit()
                }

                R.id.nav_logout -> {
                    Toast.makeText(this, "sair", Toast.LENGTH_LONG).show()

                   preferenceHelper!!.putIsLogin(false)
                    finishAffinity()
                }

            }
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            true
        } **/


//
//        nav_logout!!.setOnClickListener {
//            preferenceHelper!!.putIsLogin(false)
//            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            finish()
//        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun getJSONListagemDistrito() {
        val url1= "http://beta.allprint.pt/wp-content/uploads/2020/ListagemDistritos-XPTO.json"

        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(Method.POST,url1, Response.Listener { response ->
            Toast.makeText(applicationContext, response, Toast.LENGTH_LONG).show()
        }, Response.ErrorListener { volleyerror ->
            //progressDialog.dismiss()
            Toast.makeText(applicationContext, volleyerror.message, Toast.LENGTH_LONG).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers: MutableMap<String, String> = HashMap()

                return headers
            }


        }

        requestQueue.add(stringRequest)
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}


