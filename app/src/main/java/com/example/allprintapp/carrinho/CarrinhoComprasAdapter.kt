package com.example.allprintapp.carrinho

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.allprintapp.MainActivity
import com.example.allprintapp.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.fragment_carrinho_cardview.view.*
import kotlinx.android.synthetic.main.content_main.*


class CarrinhoComprasAdapter(var context: Context, var itensCarrinhos: List<ItensCarrinho>) :
    RecyclerView.Adapter<CarrinhoComprasAdapter.ViewHolder>() {

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val layout = LayoutInflater.from(context).inflate(R.layout.fragment_carrinho_cardview, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = itensCarrinhos.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.bindItem(itensCarrinhos[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        @SuppressLint("SetTextI18n", "CheckResult")
        fun bindItem(itensCarrinho: ItensCarrinho) {

            //Picasso.get().load(cartItem.product.imageUrl).fit().centerInside().into(holder.mImageView)
            Picasso.get().load(itensCarrinho.produto.imageUrl).fit().into(itemView.product_image)


            itemView.product_name.text = itensCarrinho.produto.nome
            val nome = itensCarrinho.produto.nome

            itemView.product_price.text = "${itensCarrinho.produto.preco}€"

            itemView.product_quantity.text = itensCarrinho.quantidade.toString()



            Observable.create(ObservableOnSubscribe<MutableList<ItensCarrinho>> {


                itemView.btn_removeItem.setOnClickListener { view ->

                    val item = ItensCarrinho(itensCarrinho.produto)

                    CarrinhoCompras.removeItem(item, itemView.context)

                    Snackbar.make(
                        (itemView.context as MainActivity).coordinator,
                        "$nome Removido",
                        Snackbar.LENGTH_LONG
                    ).show()

                    it.onNext(CarrinhoCompras.getCart())

                }


            }).subscribe { cart ->

                var quantity = 0

                cart.forEach { cartItem ->

                    quantity += cartItem.quantidade
                }
                Toast.makeText(itemView.context, "Cart size $quantity", Toast.LENGTH_SHORT).show()
            }
        }




    }

    fun setOnItemClickListener(listener: CarrinhoComprasFragment) {
        mListener = listener
    }








}