package com.example.allprintapp.carrinho

import android.content.Context
import io.paperdb.Paper


class CarrinhoCompras {

    companion object {

        fun addItem(itensCarrinho: ItensCarrinho, qty: String) {
            val cart = getCart()

            val targetItem = cart.singleOrNull { it.produto.id == itensCarrinho.produto.id }

            if (targetItem == null) {
                //cartItem.quantity++
                var a=itensCarrinho.quantidade
                var b=qty.toInt()
                itensCarrinho.quantidade=a+b
                cart.add(itensCarrinho)
            } else {
                var a=targetItem.quantidade
                var b=qty.toInt()
                targetItem.quantidade=a+b

                //targetItem.quantity++
            }
            saveCart(cart)

        }

        fun removeItem(itensCarrinho: ItensCarrinho, context: Context) {

            val cart = getCart()

// remove todos produto do carro iguais
            val targetItem = cart.removeAll { it.produto.id == itensCarrinho.produto.id }

            saveCart(cart)

        }

        fun removeAllItem() {

            val cart = getCart()

// remove todos produto do carro
            val targetItem = cart.clear()

            saveCart(cart)

        }

        fun saveCart(cart: MutableList<ItensCarrinho>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<ItensCarrinho> {
            return Paper.book().read("cart", mutableListOf())
        }

        fun getShoppingCartSize(): Int {

            var cartSize = 0
            getCart().forEach {
                cartSize += it.quantidade;
            }

            return cartSize
        }
    }

}


/*
fun removeItem(cartItem: CartItem, context: Context) {

    val cart = ShoppingCart.getCart()

// remove todos produto do carro
    val targetItem = cart.removeAll { it.product.id == cartItem.product.id }

    val targetItem = cart.singleOrNull { it.product.id == cartItem.product.id }

            if (targetItem != null) {

                if (targetItem.quantity > 0) {

                    Toast.makeText(context, "great quantity", Toast.LENGTH_SHORT).show()
                    targetItem.quantity--
                } else {
                    cart.remove(targetItem)
                }

            }

    ShoppingCart.saveCart(cart)

}
*/
