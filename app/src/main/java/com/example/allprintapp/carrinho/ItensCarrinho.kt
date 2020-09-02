package com.example.allprintapp.carrinho

import com.example.allprintapp.models.ProdutoModel

data class ItensCarrinho(var produto: ProdutoModel, var quantidade: Int = 0)