package com.example.allprintapp.ui.listaprodutos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.allprintapp.MainActivity
import com.example.allprintapp.R
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_DESCRICAO
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_ID
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_NAME
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_PRICE
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_STOCK
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.EXTRA_URL
import com.squareup.picasso.Picasso


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


