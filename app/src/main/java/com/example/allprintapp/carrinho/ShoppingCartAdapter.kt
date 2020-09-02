package com.example.ethieladiassa.shoppingcart

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.allprintapp.R
import com.example.allprintapp.carrinho.CartItem
import com.example.allprintapp.carrinho.ShoppingCartFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_list_item.view.*

class ShoppingCartAdapter(var context: Context, var cartItems: List<CartItem>) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    private var mListener: ShoppingCartAdapter.OnItemClickListener? = null

    interface OnItemClickListener {

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ShoppingCartAdapter.ViewHolder {

        val layout = LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(viewHolder: ShoppingCartAdapter.ViewHolder, position: Int) {

        viewHolder.bindItem(cartItems[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bindItem(cartItem: CartItem) {

            //Picasso.get().load(cartItem.product.imageUrl).fit().centerInside().into(holder.mImageView)
            Picasso.get().load(cartItem.product.imageUrl).fit().into(itemView.product_image)


            itemView.product_name.text = cartItem.product.nome

            itemView.product_price.text = "$${cartItem.product.preco}"

            itemView.product_quantity.text = cartItem.quantity.toString()

        }


    }

    fun setOnItemClickListener(listener: ShoppingCartFragment) {
        mListener = listener
    }

}