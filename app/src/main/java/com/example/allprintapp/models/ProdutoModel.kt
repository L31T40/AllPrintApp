package com.example.allprintapp.models

import com.google.gson.annotations.SerializedName

class ProdutoModel(

    val id: String? = null,
    val nome: String? = null,
    val preco: String? = null,
    val descricao: String? = null,
    val descricaocurta: String? = null,
    val stockqt: String? = null,
    val imageUrl: String? = null
)