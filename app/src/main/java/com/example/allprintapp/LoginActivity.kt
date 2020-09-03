package com.example.allprintapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.allprintapp.Login.PreferenceHelper
import com.example.allprintapp.models.*
import com.example.allprintapp.ui.utils.Utils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.set


class LoginActivity : AppCompatActivity() {
    private var etEmail: EditText? = null
    private var etPass: EditText? = null
    private var btnlogin: Button? = null
   // private var tvreg: TextView? = null
    private var preferenceHelper: PreferenceHelper? = null
    private var mProgressView: View? = null
    private var mLoginFormView: View? = null
    private var mDistritos: ArrayList<DistritosModel>? = null
    companion object {
        var ListagemDistritos = ArrayList<DistritosModel>()
        var ListagemCategorias = ArrayList<ListaCategoriasModel>()
        var ListagemCategoriasCompleta = ArrayList<ListaCategoriasCompletaModel>()
        var ListagemEtiquetas = ArrayList<EtiquetasModel>()
        var ListagemEtiquetasCompleta = ArrayList<EtiquetasCompletaModel>()
        var navigateToResults: Boolean = false
        // var ListagemCategorias :  HashMap<Int,String> = HashMap()

        var token: String = ""

        //var ListagemEtiquetas :   HashMap<Int,String> = HashMap()

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        preferenceHelper = PreferenceHelper(this)
        etEmail = findViewById<View>(R.id.etusername) as EditText
        etPass = findViewById<View>(R.id.etpassword) as EditText
        btnlogin = findViewById<View>(R.id.btn_entrar) as Button
        btnlogin!!.setOnClickListener { loginUser() }
        mProgressView = findViewById(R.id.progressbar_login)
        mLoginFormView = findViewById(R.id.login_form)
    }


    private fun loginUser1() {
        val email = etEmail!!.text.toString().trim { it <= ' ' }
        val password = etPass!!.text.toString().trim { it <= ' ' }
        mDistritos = ArrayList()
        val retrofit = Retrofit.Builder()
            .baseUrl(LoginInterface.LOGINURL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val api = retrofit.create(LoginInterface::class.java)
        val call = api.getUserLogin(email, password)
        showProgress(true)
        if (call != null) {
            call.enqueue(object : Callback<String?>{
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    Log.i("string de resposta -> ", response.body().toString())
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Log.i("onSuccess", response.body().toString())
                            val jsonresponse = response.body().toString()
                            parseLoginData(jsonresponse)
                            mDistritos = ArrayList()
                            getJSONListagemDistritos()
                            DoAsync {
                                getJSONListagemCategorias()
                            }.execute()
                        } else {
                            Log.i("onEmptyResponse", "Não Devolveu resposta")
                        }
                    }
                }
                override fun onFailure(call: Call<String?>, t: Throwable) {}
            })
        }
    }

    private fun loginUser() {
//        val email = etEmail!!.text.toString().trim { it <= ' ' }
//        val password = etPass!!.text.toString().trim { it <= ' ' }
        val email = "cenas@cenas.pt"
        val password = "12345"
        mDistritos = ArrayList()
        val retrofit = Retrofit.Builder()
            .baseUrl(LoginInterface.LOGINURL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val api = retrofit.create(LoginInterface::class.java)
        val call = api.getUserLogin(email, password)
        showProgress(true)
        if (call != null) {
            call.enqueue(object : Callback<String?>{
                //  call!!.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    Log.i("Response string -> ", response.body().toString())
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Log.i("onSuccess", response.body().toString())
                            val jsonresponse = response.body().toString()
                            parseLoginData(jsonresponse)
                            mDistritos = ArrayList()
                            getJSONListagemDistritos()
                            DoAsync {
                                getJSONListagemCategorias()
                            }.execute()
                        } else {
                            Log.i("onEmptyResponse", "Não Devolveu resposta")
                        }
                    }
                }
                override fun onFailure(call: Call<String?>, t: Throwable) {}
            })
        }
    }

    private fun parseLoginData(response: String) {

        try {
            val jsonObject = JSONObject(response)
            if (jsonObject.has("success")) {//if (jsonObject.getString("status") == "true") {
                token=jsonObject.getJSONObject("success").getString("token")
                saveInfo(response)
                Utils.minhaTosta(
                    this@LoginActivity,
                    R.drawable.ic_check_circle_24,
                    "Benvindo!",
                    "long",
                    "sucesso"
                )
                    .show()
                //Toast.makeText(this@LoginActivity,"Bem Vindo", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    private fun saveInfo(response: String) {
        preferenceHelper!!.putIsLogin(true)
        try {
            val jsonObject = JSONObject(response)
            if (jsonObject.getString("status") == "true") {
                val dataArray = jsonObject.getJSONArray("data")
                for (i in 0 until dataArray.length()) {
                    val dataobj = dataArray.getJSONObject(i)
                    preferenceHelper!!.putName(dataobj.getString("email"))
                    preferenceHelper!!.putHobby(dataobj.getString("password"))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }




    private fun getJSONListagemDistritos() {

        val urlDistritos= "http://beta.allprint.pt/wp-content/uploads/2020/ListagemDistritos-XPTO.json"
        val requestQueue = Volley.newRequestQueue(this)
        Log.i(ContentValues.TAG, "+==========================Nome $urlDistritos")
        val stringRequest: StringRequest = object : StringRequest(Method.POST,urlDistritos, com.android.volley.Response.Listener { response ->
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
                            DistritosModel(
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
            /**adicionar listagem de distrito ao companion object*/
            // ListagemDistritos= mListagemDistritos!!
            //Toast.makeText(applicationContext, response, Toast.LENGTH_LONG).show()
        }, com.android.volley.Response.ErrorListener { volleyerror ->
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
        val url = "http://middleware.allprint.pt/api/categorias"
        val requestQueue = Volley.newRequestQueue(this)
        Log.i(ContentValues.TAG, "+========================== URL $url")
        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, url, com.android.volley.Response.Listener { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val hit = jsonArray.getJSONObject(i)
                        val categoriaId = hit.getString("id")
                        val categoria = hit.getString("name")
                        // verifica se a categoria ja existe nos distritos, concelhos ou no Array e nao adiciona à lista
                        val existeDistrito= ListagemDistritos.find { it.distrito == categoria }
                        val existeConcelho= ListagemDistritos.find { it.concelho == categoria }
                        ListagemCategoriasCompleta.add(
                            ListaCategoriasCompletaModel(
                                categoriaId,
                                categoria
                            )
                        )
                        if ((ListagemCategorias.find { it.nome == categoria  }==null)&&(existeDistrito==null||existeConcelho==null )) {
                            ListagemCategorias.add(
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
            }, com.android.volley.Response.ErrorListener { volleyerror ->
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
        val url = "http://middleware.allprint.pt/api/etiquetas"
        val requestQueue = Volley.newRequestQueue(this)
        Log.i(ContentValues.TAG, "+========================== URL $url")
        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, url, com.android.volley.Response.Listener { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val hit = jsonArray.getJSONObject(i)
                        val etiquetaId = hit.getString("id")
                        val etiqueta = hit.getString("name")

                        ListagemEtiquetasCompleta.add(
                            EtiquetasCompletaModel(
                                etiquetaId,
                                etiqueta
                            )
                        )
                        // verifica se a categoria ja existe nos distritos, concelhos ou no Array e nao adiciona à lista
                        val existeDistrito= ListagemDistritos.find { it.distrito == etiqueta }
                        val existeConcelho= ListagemDistritos.find { it.concelho == etiqueta }
                        val existeCategoria= ListagemCategorias.find { it.nome == etiqueta  }
                        val existeEtiqueta= ListagemEtiquetas.find { it.nome == etiqueta }
                        if ((existeCategoria==null && existeEtiqueta==null) && (existeDistrito==null && existeConcelho==null )) {
                            ListagemEtiquetas.add(
                                EtiquetasModel(
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
            }, com.android.volley.Response.ErrorListener { volleyerror ->
                /*progressDialog.dismiss()*/
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


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        val shortAnimTime =
            resources.getInteger(android.R.integer.config_longAnimTime)
        mLoginFormView?.visibility = if (show) View.GONE else View.VISIBLE
        mLoginFormView?.animate()?.setDuration(shortAnimTime.toLong())?.alpha(
            if (show) 0F else 15.toFloat()
        )?.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mLoginFormView?.visibility = if (show) View.GONE else View.VISIBLE
            }
        })
        mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
        mProgressView!!.animate().setDuration(shortAnimTime.toLong()).alpha(
            if (show) 15F else 0.toFloat()
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }

}

