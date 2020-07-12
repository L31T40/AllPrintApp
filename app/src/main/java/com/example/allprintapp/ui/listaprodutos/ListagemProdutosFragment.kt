package com.example.allprintapp.ui.listaprodutos

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.allprintapp.R
import com.example.allprintapp.ui.Utils.ListagemDistritos
import com.example.allprintapp.ui.Utils.Utils
import org.json.JSONArray
import org.json.JSONException
import java.util.*

/**
 * A fragment representing a list of Items.
 */
class ListagemProdutosFragment : Fragment(), AdapterView.OnItemClickListener {

    private var columnCount = 1
    var ma: Activity? = null


    private var mRecyclerView: RecyclerView? = null
    //    private var mExampleAdapter: ExampleAdapter? = null
    var mProdutosAdapter: ProdutosRecyclerAdapter? = null
    //    private var mExampleList: ArrayList<ExampleItem>? = null
    var mProductsList: ArrayList<ProdutosLista>? = null
    var mListagemDistritos: ArrayList<ListagemDistritos>? = null
    var mRequestQueue: RequestQueue? = null
    var mRequestQueueDistritos: RequestQueue? = null
    var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listagem_produtos_recyclerview, container, false)
        //val view = inflater!!.inflate(R.layout.fragment_listagem_produtos_cardview, container, false)

        //setContentView(R.layout.ListagemProdutosFragment)
        mRecyclerView = view?.findViewById(R.id.recycler_view_lista_produtos)

        try {
            mRecyclerView!!.setHasFixedSize(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //mRecyclerView.setHasFixedSize(true)


        //mRecyclerView!!.layoutManager= LinearLayoutManager(ma)
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mProductsList = ArrayList()
        mListagemDistritos = ArrayList()
        mRequestQueue = Volley.newRequestQueue(context)
        // jsonParse()
        getDataProdutos()
        //  parseJSON()



        fun getJSONListagemDistritos() {
            val urlDistritos= "http://beta.allprint.pt/wp-content/uploads/2020/ListagemDistritos-XPTO.json"
            val requestQueue = Volley.newRequestQueue(context)
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
                            mListagemDistritos!!.add(ListagemDistritos( idDistrito,  distrito,  idConcelho, concelho,  prefixo))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                Toast.makeText(context, response, Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { volleyerror ->
                Toast.makeText(context, volleyerror.message, Toast.LENGTH_LONG).show()
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




        fun onItemClick(position: Int) {
            val detailIntent = Intent(context, DetalhesProdutosActivity::class.java)
            val clickedItem = mProductsList!![position]
            detailIntent.putExtra(EXTRA_URL, clickedItem.imageUrl)
            detailIntent.putExtra(EXTRA_ID, clickedItem.id)
            detailIntent.putExtra(EXTRA_NAME, clickedItem.nome)
            startActivity(detailIntent)
        }




        return view
    }


    fun getDataProdutos() {
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiN2ViYTk4YmI3YTk4OWI2NjI1YThkZjAyOTA3MWMxZTI3NzZkY2UzNmRlZmM1ZGE5Nzk0MjliMTNhNGUzN2RjODFiODkwOGNlYzNjY2M5YjgiLCJpYXQiOjE1OTM2Mzc4ODYsIm5iZiI6MTU5MzYzNzg4NiwiZXhwIjoxNjI1MTczODg2LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.F5RId5LSLp19uFpxXrpU2KG1dvvE9so9NFc_HTrPCsUrg-ObcHh4tJ1RRtURcMx4qmoZ0z4tk9bfGGB9TM2DjsT2tfDDokqiGdTYdboHmqkSlGKTMFZ897CFtYBlK_ETn4Cj4HoE3bG0djTHftuutXvZYzMhuWXGILkY3dfSQP2PQRwDY2CHBwAtc9CMveIdyAYE9liYF9MyjK4-pnLJvNiI5fxQvpfWBjague_-ffmFsmvqO4UjUfpZVKkLK-RjWdHtYl2EyXOejT6O4pJMANahVXJPPC97fKzKfU9pOMIVK1mMnv6TKpvz9smq7Hdg2XUjuRJ6FEHOBp3-qtX_FIGYSzQAhBlD4ITpi8jAFN4BMV-rxTs9kaxSLu_TT7OIw9JWfm40z9foBvgeDrlOYmTG1q4GI5BwteQ_31TngFkY8vDzeDxr8HBpFEogw1aAvHBJifARzR7t48FG3J9EENNGDG1LddvRgMB3a-55TQJlho6MGXodT3LRGLXoikySHjPcDEl9PbncUnkKvPh97IcdCg1OkwTnbZkgj4zyAdafjhW7vtwS9D-FIdN0g8vJHS7pSvFThtLfqCHwUuCS-Bz6cx-r1mUuUEidmz3w94clBE9EG2ZvToLmqDLy93hOM6raU6NdIlCVhnQ-fjMogknvtA7qEm5cdC8I3pQlCGw"
        val url= "https://middleware.allprint.pt/api/produtos/1"
//        progressDialog = ProgressDialog(this)
//        progressDialog.setMessage(getString(R.string.loading))
//        progressDialog.setCancelable(false)
//        progressDialog.show()
        //Defining api service
        //val requestQueue = Volley.newRequestQueue(this)
        Log.i(ContentValues.TAG, "+==========================Nome $url")
        val stringRequest: StringRequest = object : StringRequest(Method.POST,url, Response.Listener { response ->
            try {
                val jsonArray =  JSONArray(response)
                val tamanho = jsonArray.length()
                Log.i(ContentValues.TAG, "+==========================tamanho $tamanho")
                for (i in 0 until jsonArray.length()) {
                    val hit = jsonArray.getJSONObject(i)
                    val id = Utils.cortaString(hit.getString("id"),"_")
                    val nome = hit.getString("name")
                    val preco = hit.getString("price")
                    val descricaocurta = hit.getString("short_description")
                    val stockqt = hit.getString("stock_quantity")
                    val imageUrl = hit.getString("image")
                    Log.i(ContentValues.TAG, "+==========================Nome $mProductsList")
                    mProductsList!!.add(ProdutosLista(id, nome, preco, descricaocurta, stockqt,  imageUrl))
                }
                mProdutosAdapter = activity?.let { ProdutosRecyclerAdapter(it, mProductsList!!) }


                mRecyclerView!!.adapter = mProdutosAdapter
                mProdutosAdapter!!.setOnItemClickListener(this)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
            Toast.makeText(context, response, Toast.LENGTH_LONG).show()
        }, Response.ErrorListener { volleyerror ->
            //progressDialog.dismiss()
            Toast.makeText(context, volleyerror.message, Toast.LENGTH_LONG).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers: MutableMap<String, String> = HashMap()
                val auth = "Bearer $token"
                headers["Authorization"] = auth
                headers["Content-Type"] = "application/x-www-form-urlencoded"
                headers["Accept"] ="application/json"
                return headers
            }
        }
        mRequestQueue!!.add(stringRequest)
    }


/*
    fun onCreatedView()
         {

        //val view = inflater!!.inflate(R.layout.fragment_listagem_produtos_cardview, container, false)

        //setContentView(R.layout.ListagemProdutosFragment)
        mRecyclerView = view?.findViewById(R.id.recycler_view_lista_produtos)

        try {
            mRecyclerView!!.setHasFixedSize(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //mRecyclerView.setHasFixedSize(true)


        //mRecyclerView!!.layoutManager= LinearLayoutManager(ma)
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mProductsList = ArrayList()
        mListagemDistritos = ArrayList()
        mRequestQueue = Volley.newRequestQueue(context)
        // jsonParse()
        getDataProdutos()
        //  parseJSON()



        fun getJSONListagemDistritos() {
            val urlDistritos= "http://beta.allprint.pt/wp-content/uploads/2020/ListagemDistritos-XPTO.json"
            val requestQueue = Volley.newRequestQueue(context)
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
                            mListagemDistritos!!.add(ListagemDistritos( idDistrito,  distrito,  idConcelho, concelho,  prefixo))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                Toast.makeText(context, response, Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { volleyerror ->
                Toast.makeText(context, volleyerror.message, Toast.LENGTH_LONG).show()
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




        fun onItemClick(position: Int) {
            val detailIntent = Intent(ma, DetalhesProdutosActivity::class.java)
            val clickedItem = mProductsList!![position]
            detailIntent.putExtra(EXTRA_URL, clickedItem.imageUrl)
            detailIntent.putExtra(EXTRA_ID, clickedItem.id)
            detailIntent.putExtra(EXTRA_NAME, clickedItem.nome)
            startActivity(detailIntent)
        }


    }
*/







    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val EXTRA_URL = "imageUrl"
        const val EXTRA_ID = "id"
        const val EXTRA_NAME = "name"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ListagemProdutosFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }



    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }
}

