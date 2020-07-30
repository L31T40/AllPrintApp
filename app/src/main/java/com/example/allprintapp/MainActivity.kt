package com.example.allprintapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
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
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.allprintapp.Login.PreferenceHelper
import com.example.allprintapp.ui.listaprodutos.DetalhesProdutoFragment
import com.example.allprintapp.ui.listaprodutos.ProdutosRecyclerAdapter
import com.example.allprintapp.ui.utils.ListagemDistritosModel
import com.example.allprintapp.ui.utils.VarGlobals
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class MainActivity : AppCompatActivity(),ProdutosRecyclerAdapter.OnItemClickListener, DetalhesProdutoFragment.OnItemClickListener {
/** listagem de distritos disponoveis na aplicaçao*/
    companion object {
        var ListagemDistritos = ArrayList<ListagemDistritosModel>()

    }
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
   // private var preferenceHelper: PreferenceHelper? = null
    private var mListagemDistritos: ArrayList<ListagemDistritosModel>? = null

  //  private var mRequestQueueDistritos: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)



        setSupportActionBar(toolbar)



        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
          navView    = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_categorias, R.id.nav_loja, R.id.nav_slideshow, R.id.nav_logout), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mDrawerLayout = findViewById(R.id.drawer_layout)

        mListagemDistritos = ArrayList()
        getJSONListagemDistrito()

//        val g =  application as VarGlobals // Criar Variavel global com ID utilizador
//        g.listagemDistritos = mListagemDistritos
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




    private fun getJSONListagemDistrito() {

        val urlDistritos= "http://beta.allprint.pt/wp-content/uploads/2020/ListagemDistritos-XPTO.json"
        val requestQueue = Volley.newRequestQueue(this)
        Log.i(ContentValues.TAG, "+==========================Nome $urlDistritos")
        val stringRequest: StringRequest = object : StringRequest(Method.POST,urlDistritos, Response.Listener { response ->
            try {
//                val cenas = JSONObject(response)
//                val jsonArray:JSONArray =  cenas.getJSONArray("cenas")
                val jsonArray =  JSONArray(response)
                val tamanho = jsonArray.length()
                Log.i(ContentValues.TAG, "+==========================tamanho $tamanho")
                for (i in 0 until jsonArray.length()) {
                    val hit = jsonArray.getJSONObject(i)
                    val idDistrito = hit.getString("IdDistrito")
                    val distrito = hit.getString("Distrito")
                    val idConcelho = hit.getString("IdConcelho")
                    val concelho = hit.getString("Concelho")
                    val prefixo = hit.getString("Prefixo")
                    Log.i(ContentValues.TAG, "+==========================Prefixo -> $prefixo")
                    try {

                        mListagemDistritos!!.add(ListagemDistritosModel( idDistrito,  distrito,  idConcelho, concelho,  prefixo))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            /**adicionar listagem de distrito ao companion object*/
            ListagemDistritos= mListagemDistritos!!
            Toast.makeText(applicationContext, response, Toast.LENGTH_LONG).show()
        }, Response.ErrorListener { volleyerror ->
            Toast.makeText(applicationContext, volleyerror.message, Toast.LENGTH_LONG).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers: MutableMap<String, String> = HashMap()
                headers["Content-Type"] = "application/x-www-form-urlencoded"
                headers["Accept"] ="application/json"
                return headers
            }
        }
        //mRequestQueueDistritos!!.add(stringRequest)
        requestQueue!!.add(stringRequest)

    }


/*

    fun getJSONListagemDistrito() {
        val urlDistritos= "http://beta.allprint.pt/wp-content/uploads/2020/ListagemDistritos-XPTO.json"
        mRequestQueueDistritos = Volley.newRequestQueue(this)
        //val requestQueue = Volley.newRequestQueue(this)
        Log.i(ContentValues.TAG, "+==========================Nome $urlDistritos")
        val stringRequest: StringRequest = object : StringRequest(Method.POST,urlDistritos, Response.Listener { response ->
            try {
//                val cenas = JSONObject(response)
//                val jsonArray:JSONArray =  cenas.getJSONArray("cenas")
                val jsonArray =  JSONArray(response)
                val tamanho = jsonArray.length()
                Log.i(ContentValues.TAG, "+==========================tamanho $tamanho")
                for (i in 0 until jsonArray.length()) {

                    val hit = jsonArray.getJSONObject(i)
                    val idDistrito = hit.getString("IdDistrito")
                    val distrito = hit.getString("Distrito")
                    val idConcelho = hit.getString("IdConcelho")
                    val concelho = hit.getString("Concelho")
                    val prefixo = hit.getString("Prefixo")
                    Log.i(ContentValues.TAG, "+==========================Prefixo -> $prefixo")
                    try {
                        val add = mListagemDistritos?.add(
                            ListagemDistritosModel(
                                idDistrito,
                                distrito,
                                idConcelho,
                                concelho,
                                prefixo
                            )
                        )
                        var venas= mListagemDistritos?.get(1)
                        var tam= mListagemDistritos?.size
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            Toast.makeText(this, response, Toast.LENGTH_LONG).show()
        }, Response.ErrorListener { volleyerror ->
            Toast.makeText(this, volleyerror.message, Toast.LENGTH_LONG).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers: MutableMap<String, String> = HashMap()
                headers["Content-Type"] = "application/x-www-form-urlencoded"
                headers["Accept"] ="application/json"
                return headers
            }
        }
        mRequestQueueDistritos!!.add(stringRequest)
        //requestQueue!!.add(stringRequest)
    }

*/


    /**   fun getJSONListagemDistrito() {
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
    }*/

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}


