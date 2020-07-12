package com.example.allprintapp.ui.listaprodutos

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allprintapp.R
import com.squareup.picasso.Picasso
import java.util.*

class ProdutosRecyclerAdapter(private val mContext: Context,
                              private val mProductsList: ArrayList<ProdutosLista>) : RecyclerView.Adapter<ProdutosRecyclerAdapter.produtosViewHolder>() {
//    private var mListener: OnItemClickListener? = null
    private var mListener: AdapterView.OnItemClickListener? =null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        //fun getDataProdutos()
    }

    fun setOnItemClickListener(listener: ListagemProdutosFragment) {
        mListener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): produtosViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.fragment_listagem_produtos_cardview, parent, false)
        return produtosViewHolder(v)
    }

    override fun onBindViewHolder(holder: produtosViewHolder, position: Int) {
        val currentItem = mProductsList[position]
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
        return mProductsList.size
    }

    inner class produtosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
                        mListener!!.onItemClick(position)
                    }
                }
            }
        }
    }



private fun AdapterView.OnItemClickListener.onItemClick(position: Int) {
    TODO("Not yet implemented")
}


}
