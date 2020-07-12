package com.example.allprintapp.ui.listaprodutos

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.allprintapp.R
import com.squareup.picasso.Picasso


abstract class DetalhesProdutosActivity : AppCompatActivity(), ProdutosRecyclerAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalhes_produto)

        val intent = intent
        val imageUrl = intent.getStringExtra(ListagemProdutosFragment.EXTRA_URL)
        val nome = intent.getStringExtra(ListagemProdutosFragment.EXTRA_NAME)
        val id = intent.getStringExtra(ListagemProdutosFragment.EXTRA_ID)

        val imageView = findViewById<ImageView>(R.id.imageViewProduto)
        val textViewName = findViewById<TextView>(R.id.textViewDistrito)
        val textViewid = findViewById<TextView>(R.id.textViewRef)
        Picasso.get().load(imageUrl).fit().centerInside().into(imageView)
        textViewName.text = nome
        textViewid.text = id


    }


}