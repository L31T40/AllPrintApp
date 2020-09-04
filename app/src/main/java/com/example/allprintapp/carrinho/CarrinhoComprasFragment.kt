package com.example.allprintapp.carrinho


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allprintapp.LoginActivity.Companion.carrinho

import com.example.allprintapp.R
import com.example.allprintapp.ui.encomendas.CriarEncomendaFragment

import com.example.allprintapp.ui.filtrosprodutos.ARG_PARAM1
import com.example.allprintapp.ui.filtrosprodutos.ARG_PARAM2
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_carrinho.*
import java.util.*


class CarrinhoComprasFragment : Fragment(), CarrinhoComprasAdapter.OnItemClickListener {

    private var pDialog: ProgressDialog? = null
    var mRecyclerView: RecyclerView? = null
    var mCartAdapter: CarrinhoComprasAdapter? = null
    var mModelProdutoCart: ArrayList<ItensCarrinho>? = null
    private val mCarrinho = carrinho


    lateinit var adapter: CarrinhoComprasAdapter
    lateinit var mContext: AppCompatActivity
    lateinit var mLayoutManager: RecyclerView.LayoutManager


    private var param1: String? = null
    private var param2: String? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context as AppCompatActivity
        pDialog = ProgressDialog(mContext, ProgressDialog.STYLE_SPINNER)
        pDialog!!.setMessage("Aguarde... a carregar Carrinho")
        pDialog!!.setCancelable(true)
        pDialog!!.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_carrinho, container, false)

        Paper.init(mContext)
        val upArrow = ContextCompat.getDrawable(mContext, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)


            var totalPrice = CarrinhoCompras.getCart().fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantidade.times(cartItem.produto.preco!!.toDouble()) }

            val xx = view.findViewById<TextView>(R.id.total_price)
            xx.text = "${totalPrice}€"

        Toast.makeText(mContext, "Quantidade ${ListagemProdutosFragment.qt}", Toast.LENGTH_SHORT).show()

       return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mModelProdutoCart= ArrayList()
        setAdapter()
        setRVLayoutManager()
         var btncheckout: Button? = null
        btncheckout = mContext.findViewById<View>(R.id.btn_checkout) as Button
        btncheckout!!.setOnClickListener { navigateToFragment(CriarEncomendaFragment.newInstance())}
    }



    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    private fun loadRecyclerView() {
        // Termina o progress Dialog
        //if (pDialog!!.isShowing) pDialog!!.dismiss()
        mRecyclerView = view?.findViewById(R.id.shopping_cart_recyclerView)
        mCartAdapter = activity?.let { CarrinhoComprasAdapter(it, mModelProdutoCart!!) }
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager = LinearLayoutManager(mContext)
        mCartAdapter!!.setOnItemClickListener(this)
        mCartAdapter!!.notifyDataSetChanged()
        mRecyclerView!!.adapter = mCartAdapter

    }


    private fun setAdapter() {
        if (pDialog!!.isShowing) pDialog!!.dismiss()
        mRecyclerView = view?.findViewById(R.id.shopping_cart_recyclerView)
        mCartAdapter = mModelProdutoCart?.let { CarrinhoComprasAdapter(mContext, CarrinhoCompras.getCart()) }
        mCartAdapter?.notifyDataSetChanged()
        shopping_cart_recyclerView.adapter = mCartAdapter
    }



    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(mContext)
        shopping_cart_recyclerView.layoutManager = mLayoutManager
        mCartAdapter!!.setOnItemClickListener(this)
        shopping_cart_recyclerView.setHasFixedSize(true)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
              //  onBackPressed()

            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {

        fun newInstance(): Fragment {
            return CarrinhoComprasFragment()
            TODO("Not yet implemented")
        }


    }
}


