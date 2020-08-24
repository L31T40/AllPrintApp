package com.example.allprintapp

import android.content.ContentValues
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
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
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.allprintapp.models.*
import com.example.allprintapp.ui.filtrosprodutos.FiltroProdutosFragment
import com.example.allprintapp.ui.listaprodutos.DetalhesProdutoFragment
import com.example.allprintapp.ui.listaprodutos.ProdutosRecyclerAdapter
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity(),
    ProdutosRecyclerAdapter.OnItemClickListener,
    DetalhesProdutoFragment.OnItemClickListener,
    FiltroProdutosFragment.OnItemClickListener {


/** listagem de distritos disponoveis na aplicaçao*/
//    companion object {
//        var ListagemDistritos = ArrayList<ListagemDistritosModel>()
//        var ListagemCategorias = ArrayList<ListaCategoriasModel>()
//        var ListagemCategoriasCompleta = ArrayList<ListaCategoriasCompletaModel>()
//        var ListagemEtiquetas = ArrayList<ListaEtiquetasModel>()
//        var ListagemEtiquetasCompleta = ArrayList<ListaEtiquetasCompletaModel>()
//       // var ListagemCategorias :  HashMap<Int,String> = HashMap()
//
//
//        //var ListagemEtiquetas :   HashMap<Int,String> = HashMap()
//
//    }
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
   // private var preferenceHelper: PreferenceHelper? = null
 //   private var mListagemDistritos: ArrayList<ListagemDistritosModel>? = null



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

        //mDrawerLayout = findViewById(R.id.drawer_layout)

//        mListagemDistritos = ArrayList()
//
//        getJSONListagemDistritos()
//        DoAsync {
//            getJSONListagemCategorias()
//
//        }.execute()

/*        doAsync {
            getJSONListagemCategorias()
        }.execute()

        doAsync {
            getJSONListagemEtiquetas()
        }.execute()*/

    }

    //Criar menu definiçoes top
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



/*

    private fun getJSONListagemDistritos() {

        val urlDistritos= "http://beta.allprint.pt/wp-content/uploads/2020/ListagemDistritos-XPTO.json"
        val requestQueue = Volley.newRequestQueue(this)
        Log.i(ContentValues.TAG, "+==========================Nome $urlDistritos")
        val stringRequest: StringRequest = object : StringRequest(Method.POST,urlDistritos, Response.Listener { response ->
            try {
                val jsonArray =  JSONArray(response)
                val tamanho = jsonArray.length()
                Log.i(ContentValues.TAG, "+=========== Distritos $tamanho")
                for (i in 0 until jsonArray.length()) {
                    val hit = jsonArray.getJSONObject(i)
                    val idDistrito = hit.getString("IdDistrito")
                    val distrito = hit.getString("Distrito")
                    val idConcelho = hit.getString("IdConcelho")
                    val concelho = hit.getString("Concelho")
                    val prefixo = hit.getString("Prefixo")
                   // Log.i(ContentValues.TAG, "+==========================Prefixo -> $prefixo")
                    try {
                        ListagemDistritos.add(
                            ListagemDistritosModel(
                                idDistrito,
                                distrito,
                                idConcelho,
                                concelho,
                                prefixo
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            */
/**adicionar listagem de distrito ao companion object*//*

           // ListagemDistritos= mListagemDistritos!!
            //Toast.makeText(applicationContext, response, Toast.LENGTH_LONG).show()
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
        stringRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue!!.add(stringRequest)
    }



    private fun getJSONListagemCategorias() {

            val token =
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiN2ViYTk4YmI3YTk4OWI2NjI1YThkZjAyOTA3MWMxZTI3NzZkY2UzNmRlZmM1ZGE5Nzk0MjliMTNhNGUzN2RjODFiODkwOGNlYzNjY2M5YjgiLCJpYXQiOjE1OTM2Mzc4ODYsIm5iZiI6MTU5MzYzNzg4NiwiZXhwIjoxNjI1MTczODg2LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.F5RId5LSLp19uFpxXrpU2KG1dvvE9so9NFc_HTrPCsUrg-ObcHh4tJ1RRtURcMx4qmoZ0z4tk9bfGGB9TM2DjsT2tfDDokqiGdTYdboHmqkSlGKTMFZ897CFtYBlK_ETn4Cj4HoE3bG0djTHftuutXvZYzMhuWXGILkY3dfSQP2PQRwDY2CHBwAtc9CMveIdyAYE9liYF9MyjK4-pnLJvNiI5fxQvpfWBjague_-ffmFsmvqO4UjUfpZVKkLK-RjWdHtYl2EyXOejT6O4pJMANahVXJPPC97fKzKfU9pOMIVK1mMnv6TKpvz9smq7Hdg2XUjuRJ6FEHOBp3-qtX_FIGYSzQAhBlD4ITpi8jAFN4BMV-rxTs9kaxSLu_TT7OIw9JWfm40z9foBvgeDrlOYmTG1q4GI5BwteQ_31TngFkY8vDzeDxr8HBpFEogw1aAvHBJifARzR7t48FG3J9EENNGDG1LddvRgMB3a-55TQJlho6MGXodT3LRGLXoikySHjPcDEl9PbncUnkKvPh97IcdCg1OkwTnbZkgj4zyAdafjhW7vtwS9D-FIdN0g8vJHS7pSvFThtLfqCHwUuCS-Bz6cx-r1mUuUEidmz3w94clBE9EG2ZvToLmqDLy93hOM6raU6NdIlCVhnQ-fjMogknvtA7qEm5cdC8I3pQlCGw"
            val url = "http://middleware.allprint.pt/api/categorias"
            val requestQueue = Volley.newRequestQueue(this)
            Log.i(ContentValues.TAG, "+========================== URL $url")
            val stringRequest: StringRequest =
                object : StringRequest(Method.POST, url, Response.Listener { response ->
                    try {
                        val jsonArray = JSONArray(response)
                        for (i in 0 until jsonArray.length()) {
                            val hit = jsonArray.getJSONObject(i)
                            val categoriaId = hit.getString("id")
                            val categoria = hit.getString("name")
//                            ListagemCategorias!!.add(categoria)
//                            Log.i(ContentValues.TAG, "+=========== Categorias $categoria")
                            // verifica se a categoria ja existe nos distritos, concelhos ou no Array e nao adiciona à lista
                            val existeDistrito= MainActivity.ListagemDistritos.find { it.distrito == categoria }
                            val existeConcelho= MainActivity.ListagemDistritos.find { it.concelho == categoria }

                            ListagemCategoriasCompleta!!.add(
                                ListaCategoriasCompletaModel(
                                    categoriaId,
                                    categoria
                                )
                            )

                            if ((ListagemCategorias.find { it.nome == categoria  }==null)&&(existeDistrito==null||existeConcelho==null )) {
                                ListagemCategorias!!.add(
                                    ListaCategoriasModel(
                                        categoriaId,
                                        categoria
                                    )
                                )
                                //ListagemCategorias!!.add(categoria)
                                Log.i(ContentValues.TAG, "+=========== Categorias $categoria")}
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    getJSONListagemEtiquetas()
                    //Toast.makeText(context, response, Toast.LENGTH_LONG).show()
                }, Response.ErrorListener { volleyerror ->
                    */
/*progressDialog.dismiss()*//*


                    Toast.makeText(applicationContext, volleyerror.message, Toast.LENGTH_LONG).show()
                }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String>? {
                        val headers: MutableMap<String, String> = HashMap()
                        val auth = "Bearer $token"
                        headers["Authorization"] = auth
                        headers["Content-Type"] = "application/x-www-form-urlencoded"
                        headers["Accept"] = "application/json"

                        return headers
                    }
                }
        stringRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        requestQueue!!.add(stringRequest)

        }


    private fun getJSONListagemEtiquetas() {



        val token =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiN2ViYTk4YmI3YTk4OWI2NjI1YThkZjAyOTA3MWMxZTI3NzZkY2UzNmRlZmM1ZGE5Nzk0MjliMTNhNGUzN2RjODFiODkwOGNlYzNjY2M5YjgiLCJpYXQiOjE1OTM2Mzc4ODYsIm5iZiI6MTU5MzYzNzg4NiwiZXhwIjoxNjI1MTczODg2LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.F5RId5LSLp19uFpxXrpU2KG1dvvE9so9NFc_HTrPCsUrg-ObcHh4tJ1RRtURcMx4qmoZ0z4tk9bfGGB9TM2DjsT2tfDDokqiGdTYdboHmqkSlGKTMFZ897CFtYBlK_ETn4Cj4HoE3bG0djTHftuutXvZYzMhuWXGILkY3dfSQP2PQRwDY2CHBwAtc9CMveIdyAYE9liYF9MyjK4-pnLJvNiI5fxQvpfWBjague_-ffmFsmvqO4UjUfpZVKkLK-RjWdHtYl2EyXOejT6O4pJMANahVXJPPC97fKzKfU9pOMIVK1mMnv6TKpvz9smq7Hdg2XUjuRJ6FEHOBp3-qtX_FIGYSzQAhBlD4ITpi8jAFN4BMV-rxTs9kaxSLu_TT7OIw9JWfm40z9foBvgeDrlOYmTG1q4GI5BwteQ_31TngFkY8vDzeDxr8HBpFEogw1aAvHBJifARzR7t48FG3J9EENNGDG1LddvRgMB3a-55TQJlho6MGXodT3LRGLXoikySHjPcDEl9PbncUnkKvPh97IcdCg1OkwTnbZkgj4zyAdafjhW7vtwS9D-FIdN0g8vJHS7pSvFThtLfqCHwUuCS-Bz6cx-r1mUuUEidmz3w94clBE9EG2ZvToLmqDLy93hOM6raU6NdIlCVhnQ-fjMogknvtA7qEm5cdC8I3pQlCGw"
        val url = "http://middleware.allprint.pt/api/etiquetas"
        val requestQueue = Volley.newRequestQueue(this)


        Log.i(ContentValues.TAG, "+========================== URL $url")
        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, url, Response.Listener { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val hit = jsonArray.getJSONObject(i)
                        val etiquetaId = hit.getString("id")
                        val etiqueta = hit.getString("name")

                        ListagemEtiquetasCompleta!!.add(
                            ListaEtiquetasCompletaModel(
                                etiquetaId,
                                etiqueta
                            )
                        )
//                        ListagemEtiquetas!!.add(etiqueta)
//                        Log.i(ContentValues.TAG, "+=========== Etiquetas $etiqueta")
                        // verifica se a categoria ja existe nos distritos, concelhos ou no Array e nao adiciona à lista
                        val existeDistrito= MainActivity.ListagemDistritos.find { it.distrito == etiqueta }
                        val existeConcelho= MainActivity.ListagemDistritos.find { it.concelho == etiqueta }
                        if (((ListagemEtiquetas.find { it.nome == etiqueta  }==null) && (ListagemCategorias.find { it.nome == etiqueta  }==null)) && (existeDistrito==null || existeConcelho==null )) {
                            ListagemEtiquetas!!.add(
                                ListaEtiquetasModel(
                                    etiquetaId,
                                    etiqueta
                                )
                            )
                            Log.i(ContentValues.TAG, "+=========== Etiquetas $etiqueta")}
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                //Toast.makeText(context, response, Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { volleyerror ->
                */
/*progressDialog.dismiss()*//*

                Toast.makeText(applicationContext, volleyerror.message, Toast.LENGTH_LONG).show()
            }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers: MutableMap<String, String> = HashMap()
                    val auth = "Bearer $token"
                    headers["Authorization"] = auth
                    headers["Content-Type"] = "application/x-www-form-urlencoded"
                    headers["Accept"] = "application/json"
                    return headers
                }
            }
        stringRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue!!.add(stringRequest)

    }

*/


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


class DoAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }


}
