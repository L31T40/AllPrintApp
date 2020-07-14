package com.example.allprintapp.ui.listaprodutos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.allprintapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detalhes_produto.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetalhesProdutoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetalhesProdutoFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.detalhes_produto, container, false)
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.detalhes_produto, container, false)
//
//        val intent = intent
//        val imageUrl = intent.getStringExtra(ListagemProdutosFragment.EXTRA_URL)
//        val nome = intent.getStringExtra(ListagemProdutosFragment.EXTRA_NAME)
//        val id = intent.getStringExtra(ListagemProdutosFragment.EXTRA_ID)
//
        val imageView = imageViewProduto.findViewById<ImageView>(R.id.imageViewProduto)
        val textViewName = textViewDistrito.findViewById<TextView>(R.id.textViewDistrito)
        val textViewid = textViewRef.findViewById<TextView>(R.id.textViewRef)
//        Picasso.get().load(imageUrl).fit().centerInside().into(imageView)
//        textViewName.text = nome
//        textViewid.text = id
////
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetalhesProdutoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetalhesProdutoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}