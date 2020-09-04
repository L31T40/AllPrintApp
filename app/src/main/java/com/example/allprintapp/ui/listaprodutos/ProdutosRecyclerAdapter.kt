package com.example.allprintapp.ui.listaprodutos

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.allprintapp.LoginActivity
import com.example.allprintapp.MainActivity
import com.example.allprintapp.R
import com.example.allprintapp.carrinho.ItensCarrinho
import com.example.allprintapp.models.DistritosModel
import com.example.allprintapp.models.ProdutoModel
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_DESCRICAO
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_DESCRICAOCURTA
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_ID
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_NAME
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_PRICE
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_STOCK
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_URL
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.qt
import com.example.allprintapp.ui.utils.Utils
import com.example.allprintapp.ui.utils.Utils.Companion.cortaString
import com.example.allprintapp.carrinho.CarrinhoCompras
import com.example.loadmoreexample.Constant
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_listagem_produtos_cardview.view.*
import kotlinx.android.synthetic.main.progress_loading.view.*


class ProdutosRecyclerAdapter(
    private val mContext: Context,
    private val mProdutosListagemModel: ArrayList<ProdutoModel>
) : RecyclerView.Adapter<ProdutosRecyclerAdapter.ProdutosViewHolder>() {
    //    private var mListener: OnItemClickListener? = null
    private var mListener: OnItemClickListener? = null
    // private var mListaProdutos: ArrayList<ProdutosLista>? = null
    private var mDistritos: java.util.ArrayList<DistritosModel>?=null

    val mLocais = LoginActivity.ListagemDistritos
    var mDistrito: String? = null
    var mConcelho: String? = null
   // lateinit var quantidade: EditText



    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {

        fun onItemClick(position: Int)
        fun recreate()

    }


    fun addData(dataViews: ArrayList<ProdutoModel>) {
        this.mProdutosListagemModel.addAll(dataViews)
        notifyDataSetChanged()
    }


    fun getItemAtPosition(position: Int): ProdutoModel? {
        return mProdutosListagemModel[position]
    }

    fun addLoadingView() {
        //add loading item

        Handler().post {
            mProdutosListagemModel.map {  }
            notifyItemInserted(mProdutosListagemModel.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (mProdutosListagemModel.size != 0) {
            mProdutosListagemModel.removeAt(mProdutosListagemModel.size - 1)
            notifyItemRemoved(mProdutosListagemModel.size)
        }
    }



    fun setOnItemClickListener(listener: ListagemProdutosFragment) {
        mListener = listener
    }

//    val v = LayoutInflater.from(mContext).inflate(R.layout.fragment_listagem_produtos_cardview, parent, false)
//
//    return ProdutosViewHolder(v)

 //   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutosViewHolder {
 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutosViewHolder {


       // mcontext = parent.context
     return if (viewType == Constant.VIEW_TYPE_ITEM) {
         val view = LayoutInflater.from(parent.context).inflate(
             R.layout.fragment_listagem_produtos_cardview,
             parent,
             false
         )
         ProdutosViewHolder(view)
     } else {
         val view = LayoutInflater.from(mContext).inflate(R.layout.progress_loading, parent, false)
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
             view.progressbar.indeterminateDrawable.colorFilter = BlendModeColorFilter(
                 Color.WHITE,
                 BlendMode.SRC_ATOP
             )
         } else {
             view.progressbar.indeterminateDrawable.setColorFilter(
                 Color.WHITE,
                 PorterDuff.Mode.MULTIPLY
             )
         }
         ProdutosViewHolder(view)
     }

    }

    override fun onBindViewHolder(holder: ProdutosViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            val currentItem = mProdutosListagemModel[position]
            //val currentPlace = mListagemDistritos[position]
            var idcompleto = currentItem.id



            val index = mLocais.indexOfFirst { it.prefixo == idcompleto?.let { it1 ->
                Utils.cortaStringAfter(
                    it1, "_"
                )
            }
            } // -1 if not found
            if (index >= 0) {
                val locais = mLocais[index]
                mDistrito = locais.distrito
                mConcelho = locais.concelho

            }

            val id = currentItem.id?.let { cortaString(it, "_") }
            val nome = currentItem.nome
            val preco = currentItem.preco
            val concelho = mConcelho
            val imageUrl = currentItem.imageUrl




            holder.mQuantidade?.afterTextChanged {
                qt
                Toast.makeText(mContext, "Quantidade $qt", Toast.LENGTH_SHORT).show()    }


           //val xx= holder.mQuantidade?.doAfterTextChanged { text -> val value = text?.toString()?.toInt() }



            holder.mQuantidade?.text ?:  qt
            holder.mTextViewID.text  = id
            holder.mTextViewName.text = nome
            holder.mTextViewPrice.text = preco
            holder.mTextViewConcelho.text = mConcelho
            holder.mTextViewDistrito.text = mDistrito
            //holder.quantidade1.setTag(R.id.editTextQuantidade, position)
            //holder.mTextViewCategory.text = category
            Log.i(
                TAG,
                "+========================== PRODUTO-> PDMF_$id NOME-> $nome  PREÇO-> $preco URL-> $imageUrl"
            )
            Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView)

        }


    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                qt = s.toString()
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    override fun getItemCount(): Int {
        return mProdutosListagemModel.size
    }

    override fun getItemViewType(position: Int): Int {
        return Constant.VIEW_TYPE_ITEM
        /*        return if (mProdutosListagemModel[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }*/
    }

    /*inner class ProdutosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var mImageView: ImageView
        var mTextViewID: TextView
        var mTextViewName: TextView
        var mTextViewConcelho: TextView
        var mTextViewDistrito: TextView
        var mTextViewPrice: TextView
        // var mTextViewCategory: TextView
        init {
            mImageView = itemView.findViewById(R.id.imageViewProduto)
            mTextViewID = itemView.findViewById(R.id.textViewRef)
            mTextViewName = itemView.findViewById(R.id.textViewConcelho)
            mTextViewConcelho = itemView.findViewById(R.id.textViewConcelho)
            mTextViewDistrito = itemView.findViewById(R.id.textViewDistrito)
            //mTextViewCategory = itemView.findViewById(R.id.textView)
            mTextViewPrice = itemView.findViewById(R.id.textViewPreco)

            itemView.setOnClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClick(position)
                    }
                }
            }
        }
    }*/

    /*****************************************************************/
    /*****************************************************************/

  //  override fun getItemCount(): Int = products.size

/*    override fun onBindViewHolder(viewHolder: ProdutosRecyclerAdapter.ProdutosViewHolder, position: Int) {



        //viewHolder.bindProduct(mProdutosListagemModel!![position])

        viewHolder.bindProduct(mProdutosListagemModel!![position])
        (mContext as MainActivity).coordinator

    }*/




//inner class ProdutosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
  inner  class ProdutosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mImageView: ImageView
        var mTextViewID: TextView
        var mTextViewName: TextView
        var mTextViewConcelho: TextView
        var mTextViewDistrito: TextView
        var mTextViewPrice: TextView
        var mQuantidade: EditText = itemView.findViewById(R.id.editTextQuantidade)



        // var mTextViewCategory: TextView
        init {


            mImageView = itemView.findViewById(R.id.imageViewProduto)
            mTextViewID = itemView.findViewById(R.id.textViewRef)
            mTextViewName = itemView.findViewById(R.id.textViewConcelho)
            mTextViewConcelho = itemView.findViewById(R.id.textViewConcelho)
            mTextViewDistrito = itemView.findViewById(R.id.textViewDistrito)
            //mTextViewCategory = itemView.findViewById(R.id.textView)
            mTextViewPrice = itemView.findViewById(R.id.textViewPreco)
            itemView.setOnClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                         bindProduct(mProdutosListagemModel!![position])//onItemClick(position)
                    }
                }
            }
        }

        @SuppressLint("CheckResult")
        fun bindProduct(product: ProdutoModel) {

//            mImageView = itemView.findViewById(R.id.imageViewProduto)
//            mTextViewID = itemView.findViewById(R.id.textViewRef)
//            mTextViewName = itemView.findViewById(R.id.textViewConcelho)
//            mTextViewConcelho = itemView.findViewById(R.id.textViewConcelho)
//            mTextViewDistrito = itemView.findViewById(R.id.textViewDistrito)
//            //mTextViewCategory = itemView.findViewById(R.id.textView)
//            mTextViewPrice = itemView.findViewById(R.id.textViewPreco)

            Observable.create(ObservableOnSubscribe<MutableList<ItensCarrinho>> {


                itemView.btn_AddCart.setOnClickListener { view ->
                    //  itemView.addToCart.setOnClickListener { view ->

                    val item = ItensCarrinho(product)

                    CarrinhoCompras.addItem(item, qt)
                    //notify users
                    Snackbar.make(
                        (itemView.context as MainActivity).coordinator,
                        "$mTextViewName added to your cart",
                        Snackbar.LENGTH_LONG
                    ).show()


                    it.onNext(CarrinhoCompras.getCart())

                }

//                itemView.removeItem.setOnClickListener { view ->
//
//                    val item = CartItem(product)
//
//                    ShoppingCart.removeItem(item, itemView.context)
//
//                    Snackbar.make(
//                        (itemView.context as MainActivity).coordinator,
//                        "$mTextViewName removed from your cart",
//                        Snackbar.LENGTH_LONG
//                    ).show()
//
//                    it.onNext(ShoppingCart.getCart())
//
//                }


            }).subscribe { cart ->

                var quantity = 0

                cart.forEach { cartItem ->

                    quantity += cartItem.quantidade
                }
                Toast.makeText(itemView.context, "Cart size $quantity", Toast.LENGTH_SHORT).show()
            }
        }
    }
    /*****************************************************************/
    /*****************************************************************/



    private fun onItemClick(position: Int) {
/*        var firstName = "banana"
        var lastName = "banana"
        Toast.makeText(mContext, firstName, Toast.LENGTH_SHORT).show()*/
//
        val clickedItem = mProdutosListagemModel!![position]

        val dialogFragment : DialogFragment  = DetalhesProdutoFragment()

        val bundle = Bundle()
        bundle.putString(EXTRA_URL, clickedItem.imageUrl)
        bundle.putString(EXTRA_ID, clickedItem.id)
        bundle.putString(EXTRA_NAME, clickedItem.nome)
        bundle.putString(EXTRA_PRICE, clickedItem.preco)
        bundle.putString(EXTRA_DESCRICAO, clickedItem.descricao)
        bundle.putString(EXTRA_DESCRICAOCURTA, clickedItem.descricaocurta)
        bundle.putString(EXTRA_STOCK, clickedItem.stockqt)
        bundle.putBoolean("notAlertDialog", true)
        dialogFragment.arguments = bundle


        val fm = (mContext as FragmentActivity).supportFragmentManager
        dialogFragment.arguments = bundle
        val ft: FragmentTransaction
        ft = fm.beginTransaction()
        val prev = mContext.supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null)
        {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialogFragment.show(ft, "dialog")


    }




}

/**
class ProdutosRecyclerAdapter(private val mContext: Context,
                              private val mProdutosListagemModel: ArrayList<ListagemProdutosModel>) : RecyclerView.Adapter<ProdutosRecyclerAdapter.ProdutosViewHolder>() {
    //    private var mListener: OnItemClickListener? = null
    private var mListener: OnItemClickListener? = null
    // private var mListaProdutos: ArrayList<ProdutosLista>? = null


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: ListagemProdutosFragment) {
        mListener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutosViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.fragment_listagem_produtos_cardview, parent, false)

        return ProdutosViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProdutosViewHolder, position: Int) {
        val currentItem = mProdutosListagemModel[position]
        val id = currentItem.id
        val nome = currentItem.nome
        val preco = currentItem.preco
        //val category = currentItem.category
        val imageUrl = currentItem.imageUrl

        holder.mTextViewID.text = id
        holder.mTextViewName.text = nome
        holder.mTextViewPrice.text = preco
        //holder.mTextViewCategory.text = category
        Log.i(TAG, "+========================== PRODUTO-> PDMF_$id NOME-> $nome  PREÇO-> $preco URL-> $imageUrl")
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView)

    }

    override fun getItemCount(): Int {
        return mProdutosListagemModel.size
    }


    inner class ProdutosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mImageView: ImageView
        var mTextViewID: TextView
        var mTextViewName: TextView
        var mTextViewPrice: TextView
        // var mTextViewCategory: TextView

        init {
            mImageView = itemView.findViewById(R.id.imageViewProduto)
            mTextViewID = itemView.findViewById(R.id.textViewRef)
            mTextViewName = itemView.findViewById(R.id.textViewConcelho)
            mTextViewName = itemView.findViewById(R.id.textViewDistrito)
            //mTextViewCategory = itemView.findViewById(R.id.textView)
            mTextViewPrice = itemView.findViewById(R.id.textViewPreco)

            itemView.setOnClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClick(position)
                    }
                }
            }
        }

    }



    private fun onItemClick(position: Int) {
/*        var firstName = "banana"
        var lastName = "banana"
        Toast.makeText(mContext, firstName, Toast.LENGTH_SHORT).show()*/
//
        val clickedItem = mProdutosListagemModel!![position]

        val dialogFragment : DialogFragment  = DetalhesProdutoFragment()

        val bundle = Bundle()
        bundle.putString(EXTRA_URL, clickedItem.imageUrl)
        bundle.putString(EXTRA_ID, clickedItem.id)
        bundle.putString(EXTRA_NAME, clickedItem.nome)
        bundle.putString(EXTRA_PRICE, clickedItem.preco)
        bundle.putString(EXTRA_DESCRICAO, clickedItem.descricaocurta)
        bundle.putString(EXTRA_STOCK, clickedItem.stockqt)
        bundle.putBoolean("notAlertDialog", true)
        dialogFragment.arguments = bundle


        val fm = (mContext as FragmentActivity).supportFragmentManager
        dialogFragment.arguments = bundle
        val ft: FragmentTransaction
        ft = fm.beginTransaction()
        val prev = mContext.supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null)
        {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialogFragment.show(ft, "dialog")


    }




}


*/