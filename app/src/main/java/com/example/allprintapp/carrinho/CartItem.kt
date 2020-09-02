package com.example.allprintapp.carrinho

import com.example.allprintapp.models.ProdutoModel

data class CartItem(var product: ProdutoModel, var quantity: Int = 0)