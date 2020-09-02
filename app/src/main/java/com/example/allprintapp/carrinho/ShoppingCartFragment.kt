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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allprintapp.R
import com.example.allprintapp.ui.filtrosprodutos.ARG_PARAM1
import com.example.allprintapp.ui.filtrosprodutos.ARG_PARAM2
import com.example.ethieladiassa.shoppingcart.ShoppingCart
import com.example.ethieladiassa.shoppingcart.ShoppingCartAdapter
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import java.util.*


class ShoppingCartFragment : Fragment(), ShoppingCartAdapter.OnItemClickListener {

    private var pDialog: ProgressDialog? = null
    var mRecyclerView: RecyclerView? = null
    var mCartAdapter: ShoppingCartAdapter? = null
    var mModelProdutoCart: ArrayList<CartItem>? = null

    lateinit var adapter: ShoppingCartAdapter
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
        val view = inflater.inflate(R.layout.activity_shopping_cart, container, false)
//        setContentView(R.layout.activity_shopping_cart)
//
//
//        setSupportActionBar(toolbar)
//
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Paper.init(mContext)
        val upArrow = ContextCompat.getDrawable(mContext, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
       // supportActionBar?.setHomeAsUpIndicator(upArrow)


//        adapter = ShoppingCartAdapter(context, ShoppingCart.getCart())
//        adapter.notifyDataSetChanged()
//
//        shopping_cart_recyclerView.adapter = adapter
//
//        shopping_cart_recyclerView.layoutManager = LinearLayoutManager(context)
     //   loadRecyclerView()
      //  if (ShoppingCart.getShoppingCartSize()>0){
            setAdapter()
            setRVLayoutManager()
            var totalPrice = ShoppingCart.getCart()
                .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.preco!!.toDouble()) }

            total_price.text = "$${totalPrice}"
     //   } else{ Toast.makeText(context, "VAZIO!", Toast.LENGTH_SHORT).show()}




       return view
    }





    private fun loadRecyclerView() {
        // Termina o progress Dialog
        //if (pDialog!!.isShowing) pDialog!!.dismiss()
        mRecyclerView = view?.findViewById(R.id.shopping_cart_recyclerView)
        mCartAdapter = activity?.let { ShoppingCartAdapter(it, mModelProdutoCart!!) }
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager = LinearLayoutManager(mContext)
        mCartAdapter!!.setOnItemClickListener(this)
        mCartAdapter!!.notifyDataSetChanged()
        mRecyclerView!!.adapter = mCartAdapter

    }


    private fun setAdapter() {
        if (pDialog!!.isShowing) pDialog!!.dismiss()
        mRecyclerView = view?.findViewById(R.id.shopping_cart_recyclerView)
        mCartAdapter = mModelProdutoCart?.let { ShoppingCartAdapter(mContext, it) }
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
            return ShoppingCartFragment()
            TODO("Not yet implemented")
        }


    }
}


