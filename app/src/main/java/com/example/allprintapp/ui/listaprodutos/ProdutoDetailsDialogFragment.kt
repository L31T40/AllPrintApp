package com.example.allprintapp.ui.listaprodutos





import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.allprintapp.LoginActivity.Companion.ListagemDistritos
import com.example.allprintapp.R
import com.example.allprintapp.ui.utils.Utils.Companion.cortaString
import com.squareup.picasso.Picasso



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var buttonSairDetalhes: Button? = null
/**
 * A simple [Fragment] subclass.
 * Use the [DetalhesProdutoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetalhesProdutoFragment : DialogFragment(){//,View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


interface OnItemClickListener {
    fun onItemClick(position: Int)
}

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_detalhes_produto, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        this.dialog?.setTitle("BANAAN")
        buttonSairDetalhes = view.findViewById<View>(R.id.btn_SairDetalhes) as Button
        buttonSairDetalhes!!.setOnClickListener {  this.dismiss()}
        val mLocais = ListagemDistritos
        var mDistrito: String? = null
        var mConcelho: String? = null



//
//        val index = mLocais.indexOfFirst { it.prefixo == "1AV3AN_" } // -1 if not found
//        if (index >= 0) {
//            val locais = mLocais[index]
//            mDistrito = locais.distrito
//            mConcelho = locais.concelho
//
//        }

        val textViewId: TextView = view.findViewById(R.id.textViewRefDetalhes) as TextView
        val textId = arguments?.getString(EXTRA_ID)
        textViewId.text =  cortaString(textId.toString(),"_")

        val textViewDistrito: TextView = view.findViewById(R.id.textViewDistritoDetalhes) as TextView
        textViewDistrito.text = "Distrito: "+mDistrito.toString()
        //textViewName.text = arguments?.getString(EXTRA_NAME)

        val textViewConcelho: TextView = view.findViewById(R.id.textViewConcelhoDetalhes) as TextView
        textViewConcelho.text = "Concelho: "+mConcelho.toString()
        //textViewName.text = arguments?.getString(EXTRA_NAME)

        val textViewDescricaoCurta: TextView = view.findViewById(R.id.textViewDescricaoCurtaDetalhes) as TextView
        val textDescricaoCurta = arguments?.getString(EXTRA_DESCRICAOCURTA)
        textViewDescricaoCurta.text = android.text.Html.fromHtml(textDescricaoCurta).toString() //remove tags htmls da string



        val textViewDescricao: TextView = view.findViewById(R.id.textViewDescricaoDetalhes) as TextView
        val textDescricao = arguments?.getString(EXTRA_DESCRICAO)
        textViewDescricao.text = android.text.Html.fromHtml(textDescricao).toString() //remove tags htmls da string


        val textViewPrice: TextView = view.findViewById(R.id.textViewPrecoDetalhes) as TextView
        val textPrice = "Preço: " + arguments?.getString(EXTRA_PRICE)+"€"
        textViewPrice.text = textPrice

        val textViewStock: TextView = view.findViewById(R.id.textViewStockDetalhes) as TextView
        val textStock = "Stock: " + arguments?.getString(EXTRA_STOCK)
        textViewStock.text = textStock



        val imageUrl = arguments?.getString(EXTRA_URL)
        val imageView = view.findViewById<ImageView>(R.id.imageViewProdutoDetalhes)
        Picasso.get().load(imageUrl).fit().centerInside().into(imageView)

        return view
    }


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val EXTRA_URL = "imageUrl"
        const val EXTRA_ID = "id"
        const val EXTRA_NAME = "name"
        const val EXTRA_PRICE = "price"
        const val EXTRA_DESCRICAO = "description"
        const val EXTRA_DESCRICAOCURTA = "short_description"
        const val EXTRA_STOCK = "stock_quantity"

        fun newInstance(param1: String, param2: String) =
            DetalhesProdutoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}

/*        var mProdutosListagemModel: ArrayList<ListagemProdutosModel>? = null
        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(position: Int): DetalhesProdutoFragment {
            val clickedItem = mProdutosListagemModel!![position]
            val fragment = DetalhesProdutoFragment()
            val args = Bundle()
            args.putString(EXTRA_URL, clickedItem.imageUrl)
            args.putString(EXTRA_ID, clickedItem.id)
            args.putString(EXTRA_NAME, clickedItem.nome)
            fragment.arguments = args
            return fragment
        }*/

//        fun newInstance(columnCount: Int) =
//            ListagemProdutosFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_COLUMN_COUNT, columnCount)
//                }
//            }





//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment DetalhesProdutoFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            DetalhesProdutoFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }

//    override fun onClick(v: View?) {
//        TODO("Not yet implemented")
//    }


