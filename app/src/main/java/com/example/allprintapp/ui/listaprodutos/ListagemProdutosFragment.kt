package com.example.allprintapp.ui.listaprodutos

import android.app.Activity
import android.app.ProgressDialog
import android.app.ProgressDialog.STYLE_SPINNER
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.allprintapp.LoginActivity.Companion.ListagemDistritos
import com.example.allprintapp.LoginActivity.Companion.token
import com.example.allprintapp.R
import com.example.allprintapp.carrinho.ItensCarrinho
import com.example.allprintapp.carrinho.CarrinhoComprasFragment
import com.example.allprintapp.models.DistritosModel
import com.example.allprintapp.models.ProdutoModel
import com.example.allprintapp.ui.filtrosprodutos.FiltroProdutosFragment.Companion.stringPesquisa
import com.example.allprintapp.ui.utils.Utils.Companion.minhaTosta
import com.example.allprintapp.carrinho.CarrinhoCompras
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_listagem_produtos_recyclerview.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A fragment representing a list of Items.
 */

@Suppress("DEPRECATION")

open class ListagemProdutosFragment : Fragment(),ProdutosRecyclerAdapter.OnItemClickListener {
    private var param1: String? = null
    private var param2: String? = null

    private var columnCount = 1
    var ma: Activity? = null

    private var pDialog: ProgressDialog? = null

    var mModelProdutoCart: ArrayList<ItensCarrinho>? = null
    var mRecyclerView: RecyclerView? = null
    var mProdutosAdapter: ProdutosRecyclerAdapter? = null

    //    private var mExampleList: ArrayList<ExampleItem>? = null
    var mModelProdutoListagens: ArrayList<ProdutoModel>? = null
    var mDistritos: ArrayList<DistritosModel>? = null
    var mRequestQueue: RequestQueue? = null
    var mRequestQueueDistritos: RequestQueue? = null
    var requestQueue: RequestQueue? = null


    lateinit var itemsCells: ArrayList<String?>
    lateinit var loadMoreItemsCells: ArrayList<String?>

    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: RecyclerView.LayoutManager


    lateinit var mContext: AppCompatActivity

    companion object {



        fun newInstance(): Fragment {
            return ListagemProdutosFragment()
            TODO("Not yet implemented")
        }

       //   fun newInstance() = ListagemProdutosFragment()


        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val EXTRA_URL = "imageUrl"
        const val EXTRA_ID = "id"
        const val EXTRA_NAME = "name"
        const val EXTRA_PRICE = "price"
        const val EXTRA_DESCRICAO = "description"
        const val EXTRA_DESCRICAOCURTA = "short_description"
        const val EXTRA_STOCK = "stock_quantity"
        const val STRING_PESQUISA: String = ""
        var flag_pesquisa=false
        var qt:String="0"
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context as AppCompatActivity

        pDialog = ProgressDialog(mContext, STYLE_SPINNER)
        pDialog!!.setMessage("Aguarde... a carregar produtos")
        pDialog!!.setCancelable(true)
        pDialog!!.show()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
        setHasOptionsMenu(true) // para criar menu Filtro
//

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

    }

    // cria menu Filtro
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_listagem_produtos_recyclerview, container, false)
        val coisas = ListagemDistritos
        val cenas = mDistritos?.get(1)
        val coisas1 = coisas[1]
        Paper.init(mContext)

        // This callback will only be called when MyFragment is at least Started.
//        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
//            // Handle the back button even
//            fragmentManager?.popBackStack()
//        }



        return view

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection

        return when (item.itemId) {
            R.id.menu_filtrar -> {
                menuFiltrarProdutos()
                true
            }
            R.id.menu_carrinho -> {
                //navigateToFragment(ShoppingCartFragment.newInstance())
                val x = CarrinhoCompras.getShoppingCartSize()
                if (CarrinhoCompras.getShoppingCartSize() <= 0) {
                    minhaTosta(
                        mContext,
                        R.drawable.ic_error_24,
                        "O Carrinho de Compras está Vazio!",
                        "long",
                        "erro"
                    )
                        .show()

                    //Toast.makeText(context, "O Carrinho de Compras está Vazio!", Toast.LENGTH_LONG).show()
                } else {
                    navigateToFragment(CarrinhoComprasFragment.newInstance())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

     fun menuFiltrarProdutos() {

       //  fragmentManager?.let { FiltroProdutosFragment(this).show(it,"FiltroDialog") }

 /*       val fragment : Fragment = FiltroProdutosFragment()


        val fm = (mContext as FragmentActivity).supportFragmentManager
        val ft: FragmentTransaction
        ft = fm.beginTransaction()
        val prev = mContext.supportFragmentManager.findFragmentByTag("FiltroDialog")
        if (prev != null)
        {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
         fragment.view(ft, "FiltroDialog")*/


    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mModelProdutoListagens = ArrayList()
        // mListagemDistritos = ArrayList()
        /**Definir api service*/
        mRequestQueue = Volley.newRequestQueue(context)
        /**Parse a json pedido ao middleware*/
        getDataProdutos("1")

        setRVScrollListener()
    }




    private fun loadRecyclerView() {
        // Termina o progress Dialog
        if (pDialog!!.isShowing) pDialog!!.dismiss()
        mRecyclerView = view?.findViewById(R.id.recycler_view_lista_produtos)
        mProdutosAdapter = activity?.let { ProdutosRecyclerAdapter(it, mModelProdutoListagens!!) }
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mProdutosAdapter!!.setOnItemClickListener(this)
        mRecyclerView!!.adapter = mProdutosAdapter

    }


    private fun setAdapter() {
        if (pDialog!!.isShowing) pDialog!!.dismiss()
        mRecyclerView = view?.findViewById(R.id.recycler_view_lista_produtos)
        mProdutosAdapter = mModelProdutoListagens?.let { ProdutosRecyclerAdapter(mContext, it) }
        mProdutosAdapter?.notifyDataSetChanged()
        recycler_view_lista_produtos.adapter = mProdutosAdapter
    }

    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(mContext)
        recycler_view_lista_produtos.layoutManager = mLayoutManager
        mProdutosAdapter!!.setOnItemClickListener(this)
        recycler_view_lista_produtos.setHasFixedSize(true)
    }

    private fun setRVScrollListener() {
        mLayoutManager = LinearLayoutManager(mContext)
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                LoadMoreData()
            }
        })
        recycler_view_lista_produtos.addOnScrollListener(scrollListener)
    }

    //Faz o parse do JSON de informção dos produtos através do middleware usando um token recebido pelo protocolo OAUTH2
    fun getDataProdutos(pag: String) {
        var url: String? = null
       // url = "https://middleware.allprint.pt/api/produtosfiltro/$stringPesquisa"
        if (flag_pesquisa){
            url = "https://middleware.allprint.pt/api/produtosfiltro/$stringPesquisa"
            flag_pesquisa=false}
        else{
            url = "https://middleware.allprint.pt/api/produtos/$pag" }
        Log.i(ContentValues.TAG, "+========================== URL $url")
        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, url, Response.Listener { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val hit = jsonArray.getJSONObject(i)
                        val id = hit.getString("id")
                        val nome = hit.getString("name")
                        val preco = hit.getString("price")
                        val descricao = hit.getString("description")
                        val descricaocurta = hit.getString("short_description")
                        val stockqt = hit.getString("stock_quantity")
                        val imageUrl = hit.getString("image")
                        mModelProdutoListagens!!.add(
                            ProdutoModel(
                                id,
                                nome,
                                preco,
                                descricao,
                                descricaocurta,
                                stockqt,
                                imageUrl
                            )
                        )
                    }

                    //loadRecyclerView()
                    setAdapter()
                    setRVLayoutManager()


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                //Toast.makeText(context, response, Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { volleyerror ->
                /*progressDialog.dismiss()*/

                Toast.makeText(context, volleyerror.message, Toast.LENGTH_LONG).show()
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
        mRequestQueue!!.add(stringRequest)
        flag_pesquisa=false
    }





    private fun LoadMoreData() {
        //Add the Loading View
        mProdutosAdapter?.addLoadingView()
        //Create the mModelProdutoListagems Arraylist
        mModelProdutoListagens = ArrayList()
        //Get the number of the current Items of the main Arraylist and divide by number of elements each json gets
        val start = mProdutosAdapter?.itemCount?.div(15)?.inc()

        //Load 1 more items
        val end = start?.plus(1)
        //Use Handler if the items are loading too fast.
        //If you remove it, the data will load so fast that you can't even see the LoadingView
        Handler().postDelayed({
            if (start != null) {
                for (i in start..end!!) {
                    var x = i - 23
                    //Get data and add them to loadMoreItemsCells ArrayList
                    getDataProdutos(i.toString())
                }
            }
            //Remove the Loading View
            mProdutosAdapter?.removeLoadingView()
            //We adding the data to our main ArrayList
            mModelProdutoListagens?.let { mProdutosAdapter?.addData(it) }
            //Change the boolean isLoading to false
            scrollListener.setLoaded()
            //Update the recyclerView in the main thread
            recycler_view_lista_produtos.post {
                mProdutosAdapter?.notifyDataSetChanged()
            }
        }, 3000)

    }




    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")

    }
    override fun recreate() {
        TODO("Not yet implemented")
    }

}

/*

    //Faz o parse do JSON de informção dos produtos através do middleware usando um token OAUTH2
    fun getDataProdutosFiltro() {



        val url = "https://middleware.allprint.pt/api/produtos/$stringPesquisa"

        Log.i(ContentValues.TAG, "+========================== URL $url")
        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, url, Response.Listener { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val hit = jsonArray.getJSONObject(i)
                        val id = hit.getString("id")
                        val nome = hit.getString("name")
                        val preco = hit.getString("price")
                        val descricao = hit.getString("description")
                        val descricaocurta = hit.getString("short_description")
                        val stockqt = hit.getString("stock_quantity")
                        val imageUrl = hit.getString("image")
                        mModelProdutoListagens!!.add(
                            ProdutoModel(
                                id,
                                nome,
                                preco,
                                descricao,
                                descricaocurta,
                                stockqt,
                                imageUrl
                            )
                        )
                    }

                    //loadRecyclerView()
                    setAdapter()
                    setRVLayoutManager()


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                //Toast.makeText(context, response, Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { volleyerror ->
                */
/*progressDialog.dismiss()*//*


                Toast.makeText(context, volleyerror.message, Toast.LENGTH_LONG).show()
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
        mRequestQueue!!.add(stringRequest)

    }
*/
