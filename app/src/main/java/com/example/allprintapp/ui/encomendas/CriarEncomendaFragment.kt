package com.example.allprintapp.ui.encomendas

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.allprintapp.R
import com.example.allprintapp.ui.utils.Utils
import com.example.allprintapp.carrinho.CarrinhoCompras
import kotlinx.android.synthetic.main.fragment_criar_encomenda.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CriarEncomendaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CriarEncomendaFragment : Fragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
     lateinit var mContext: AppCompatActivity
    var btnPesquisaCliente: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_criar_encomenda, container, false)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context as AppCompatActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


// when button is clicked, show the alert
        btn_encomenda.setOnClickListener {
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(mContext)

            // set message of alert dialog
            dialogBuilder.setMessage("Pretende enviar a encomenda?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("AVANÇAR", DialogInterface.OnClickListener {
                        dialog, id -> dialog.dismiss()
                        CarrinhoCompras.removeAllItem()
                        activity?.onBackPressed()
                        activity?.onBackPressed()
                    Utils.minhaTosta(
                        mContext,
                        R.drawable.ic_check_circle_24,
                        "Encomenda Enviada!",
                        "long",
                        "sucesso"
                    )
                        .show()

                })
                // negative button text and action
                .setNegativeButton("CANCELAR", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("ENCOMENDA")
            // show alert dialog
            alert.show()
        }
//        btnPesquisaCliente = mContext.findViewById<View>(R.id.btn_pesquisacliente) as ImageButton
       // btnPesquisaCliente.setOnClickListener { navigateToFragment(CriarEncomendaFragment.newInstance())}
    }


    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment CriarEncomendaFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            CriarEncomendaFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }

        companion object {

            fun newInstance(): Fragment {
                return CriarEncomendaFragment()
                TODO("Not yet implemented")
            }


        }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}