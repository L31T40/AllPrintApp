package com.example.allprintapp.ui.listaprodutos

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.allprintapp.MainActivity
import com.example.allprintapp.R
import com.squareup.picasso.Picasso
import java.util.*

class ProdutosRecyclerAdapter(private val mContext: Context,
                              private val mListaProdutos: ArrayList<ProdutosLista>) : RecyclerView.Adapter<ProdutosRecyclerAdapter.ProdutosViewHolder>() {
//    private var mListener: OnItemClickListener? = null
private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: ListagemProdutosFragment) {
        mListener = listener
    }


    /*private var mListener: AdapterView.OnItemClickListener? =null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        //fun getDataProdutos()
    }

    fun setOnItemClickListener(listener: ListagemProdutosFragment) {
        mListener = listener
    }*/



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutosViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.fragment_listagem_produtos_cardview, parent, false)
        return ProdutosViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProdutosViewHolder, position: Int) {
        val currentItem = mListaProdutos[position]
        val id = currentItem.id
        val nome = currentItem.nome
        val preco = currentItem.preco
        //val category = currentItem.category
        val imageUrl = currentItem.imageUrl

        holder.mTextViewID.text = id
        holder.mTextViewName.text = nome
        holder.mTextViewPrice.text = preco
        //holder.mTextViewCategory.text = category
        Log.i(TAG, "+==========================Nome $nome")
        Log.i(TAG, "+==========================id $id")
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView)
    }

    override fun getItemCount(): Int {
        return mListaProdutos.size
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



 fun onItemClick(position: Int) {
//    val detailIntent = Intent(mContext, DetalhesProdutosActivity::class.java)
//    val clickedItem = mListaProdutos!![position]
//    detailIntent.putExtra(ListagemProdutosFragment.EXTRA_URL, clickedItem.imageUrl)
//    detailIntent.putExtra(ListagemProdutosFragment.EXTRA_ID, clickedItem.id)
//    detailIntent.putExtra(ListagemProdutosFragment.EXTRA_NAME, clickedItem.nome)

}

//    fun AppCompatActivity.replaceFragment(fragment: Fragment){
//        val fragmentManager = supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        transaction.replace(R.id.nav_host_fragment,fragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }

}
