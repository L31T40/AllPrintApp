package com.example.allprintapp.ui.utils

import android.app.Application
import java.util.*

class VarGlobals : Application() {
    var NFuncGlobal = "" //Numero de funcionario
    var idFuncGlobal = "" //Numero de funcionario
    var succeed = 0
    var listaLocais: ArrayList<String>? = null // Lista dos locais
    var listaLocais1: ArrayList<HashMap<String, String>>? =
        null // Lista dos locais
    var listaCombustivel: ArrayList<String>? = null //
    var ViaturasUser: ArrayList<HashMap<String, String>>? =
        null // Lista de viatura do Utilizador + Empresa
    var flagDecoration = false // para detetar decoração na recyclerview
    var listagemDistritos: ArrayList<ListagemDistritosModel>? = null
}