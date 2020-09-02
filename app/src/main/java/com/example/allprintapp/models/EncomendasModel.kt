package com.example.allprintapp.models

class EncomendasModel(
    val num_encomenda: String? = null, //number
    val nome_cliente: String? = null, //billing -> first_name + last_name
    val empresa: String? = null, //billing -> company
    val endereco1: String? = null, //billing -> address_1
    val endereco2: String? = null, //billing -> address_2
    val cidade: String? = null, //billing -> city
    val cod_postal: String? = null, //billing -> postcode
    val email: String? = null, //billing -> email
    val telefone: String? = null, //billing -> phone
    val tipo_pagamento: String? = null, //payment_method_title
    val contribuinte: String? = null, //meta_data -> "key": "_nif_number", "value": "111111111"
    val itens: String? = null, //line_items
    val order_key: String? = null, //order_key
    val status: String? = null, //status
    val data_criada: String? = null, //date_created
    val data_modificada: String? = null, //date_modified
    val total: String? = null, //total
    val total_iva: String? = null, //total_tax
//    val stockqt: String? = null,
//    val imageUrl: String? = null
)