package com.example.allprintapp.ui.listacategorias

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.allprintapp.R
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment


class ListaCategoriasFragmentOLD : Fragment() {
    private var columnCount = 1
    lateinit var mContext: AppCompatActivity

    val hashMapFamiliaProdutos = mutableMapOf<Int, String>()



    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context as AppCompatActivity

       // hashMapFamiliaProdutos.put(3, "John")
        hashMapFamiliaProdutos[1] = "MAGNÉTICOS"
        hashMapFamiliaProdutos[2] = "CANECAS"
        hashMapFamiliaProdutos[3] = "CANECAS"
        hashMapFamiliaProdutos[4] = "CENAS"
        hashMapFamiliaProdutos[5] = "COISAS"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            columnCount = it.getInt(ListagemProdutosFragment.ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_listagem_categorias_recyclerview, container, false)


        return view

    }



}