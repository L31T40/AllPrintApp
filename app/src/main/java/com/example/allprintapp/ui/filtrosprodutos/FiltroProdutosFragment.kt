package com.example.allprintapp.ui.filtrosprodutos

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.size
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.example.allprintapp.MainActivity
import com.example.allprintapp.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_filtro_produtos.*

import kotlin.random.Random


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


//        val spinnerConcelhos = view.findViewById<Spinner>(R.id.spinner_concelho)
//        val spinnerDistritos = view.findViewById<Spinner>(R.id.spinner_distrito)

private val listaDistritos: ArrayList<String> = ArrayList() // Array para colocar nomes de distritos
private val listaConcelhos: ArrayList<String> = ArrayList() // Array para colocar nomes de Concelhos conforme o distrito seleccionado
private val listaCategorias: ArrayList<String> = ArrayList() // Array para colocar nomes de Concelhos conforme o distrito seleccionado
private val listaEtiquetas: ArrayList<String> = ArrayList() // Array para colocar nomes de Concelhos conforme o distrito seleccionado

private val mLocais = MainActivity.ListagemDistritos //Array de distrito e concelhos
private val mCategoria = MainActivity.ListagemCategorias //Array de categorias
private val mEtiqueta = MainActivity.ListagemEtiquetas//Array de etiquetas
private var mDistrito: String? = null
private var mConcelho: String? = null
private var selectedDistrito: String? = null
private var selectedConcelho: String? = null
private var selectedCategoria: String? = null
private var selectedEtiqueta: String? = null
private var selectedChips: List<String>? = null



/**
 * A simple [Fragment] subclass.
 * Use the [FiltroProdutosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FiltroProdutosFragment :  DialogFragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var spinnerDistritos: Spinner? = null
    private var spinnerConcelhos: Spinner? = null
    private var spinnerCategoria: Spinner? = null
    private var spinnerEtiqueta: Spinner? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_filtro_produtos, container, false)

        spinnerDistritos=view.findViewById<Spinner>(R.id.spinner_distrito) as Spinner
        spinnerConcelhos=view.findViewById<Spinner>(R.id.spinner_concelho) as Spinner
        spinnerCategoria=view.findViewById<Spinner>(R.id.spinner_categorias) as Spinner
        spinnerEtiqueta=view.findViewById<Spinner>(R.id.spinner_etiquetas) as Spinner

        spinnerDistritos!!.onItemSelectedListener=this

        spinnerDistritos()
        spinnerCategorias()
        spinnerEtiquetas()
      //  spinnerConcelhos()
       // chipsEtiquetas(view)



        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return view
    }

    // Toast extension method
    fun Fragment.toast(message: String, toastLength: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, toastLength).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FlitrosProdutosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FiltroProdutosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun chipsEtiquetas(view:View){

        /** implementaçao de chips**/
        // set the button click listener
        val btnfiltrar=   view.findViewById<Spinner>(R.id.spinner_etiquetas)
        btnfiltrar.setOnClickListener{
            // Initialize a new chip instance
            val chip = Chip(context)
            chip.text = "Random ${Random.nextInt(100)}"

            // Set the chip icon
            chip.chipIcon = context?.let { it1 -> ContextCompat.getDrawable(it1,R.drawable.ic_launcher_foreground) }
            //chip.setChipIconTintResource(R.color.abc_search_url_text)

            // Make the chip clickable
            chip.isClickable = true
            chip.isCheckable = false

            // Show the chip icon in chip
            chip.isCloseIconVisible = true

            // Set the chip click listener
            chip.setOnClickListener{
                toast("Clicked: ${chip.text}")
            }

            // Set chip close icon click listener
            chip.setOnCloseIconClickListener{
                // Smoothly remove chip from chip group
                TransitionManager.beginDelayedTransition(chip_filtro)
                chip_filtro.removeView(chip)
            }

            // Finally, add the chip to chip group
            chip_filtro.addView(chip)
        }

    }

    fun chipsEtiquetas2(texto:String){

        /** implementaçao de chips**/
        // set the button click listener

            // Initialize a new chip instance
            val chip = Chip(context)
            chip.text = texto

            // Set the chip icon
            chip.chipIcon = context?.let { it1 -> ContextCompat.getDrawable(it1,R.drawable.ic_launcher_foreground) }
            //chip.setChipIconTintResource(R.color.abc_search_url_text)

            // Make the chip clickable
            chip.isClickable = true
            chip.isCheckable = false

            // Show the chip icon in chip
            chip.isCloseIconVisible = true

            // Set the chip click listener
            chip.setOnClickListener{
                toast("Clicked: ${chip.text}")
            }

            // Set chip close icon click listener
            chip.setOnCloseIconClickListener{
                // Smoothly remove chip from chip group
                TransitionManager.beginDelayedTransition(chip_filtro)
                chip_filtro.removeView(chip)
            }

            // Finally, add the chip to chip group
            chip_filtro.addView(chip)
            chip_filtro.size


        val chipGroup = chip_filtro// get the ChipGroup view via your favorite inflation mechanism (view bindind, android extensions, etc)
        selectedChips = chipGroup.children
            //.filter { (it as Chip).isChecked }
            .map { (it as Chip).text.toString() }
            .toList()
        Log.i(TAG, "+========================== CHIPPPPSSS -> ${selectedChips!!.get(0)}")
        Log.i(TAG, "+========================== CHIPPPPSSS -> ${selectedChips!!.get(1)}")

    }



    fun spinnerDistritos(){
        /**SPINNER DISTRITOS**/


        //val index = mLocais.indexOfFirst { it.prefixo == "1AV3AN_" } // -1 if not found
        mLocais.groupBy { it.distrito }.forEach { (name, list) ->
            listaDistritos.add(name)
        }

        // access the spinner

        if (spinnerDistritos != null) {

            val adapterDistritos = ArrayAdapter(
                requireActivity().baseContext,
                android.R.layout.simple_spinner_item,
                listaDistritos
            )
            adapterDistritos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDistritos!!.adapter = adapterDistritos
            spinnerDistritos!!.setSelection(0, true);
            selectedDistrito = listaDistritos[0]
            spinnerDistritos!!.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        context,
                        getString(R.string.selected_item) + " " +
                                "" + listaDistritos[position], Toast.LENGTH_SHORT
                    ).show()

                    selectedDistrito = listaDistritos[position]
                    spinnerConcelhos()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        /**SPINNER DISTRITOS **/
    }


    fun spinnerConcelhos(){
        /**SPINNER CONCELHOS**/
        //val mLocais = MainActivity.ListagemDistritos


        //val index = mLocais.indexOfFirst { it.prefixo == "1AV3AN_" } // -1 if not found
//        val index = mLocais.indexOfFirst { it.distrito == selectedDistrito } // -1 if not found
//        mLocais.groupBy{ it.concelho }.forEach{ (name, index) ->
//            listaConcelhos.add(name)
//        }


        val index = mLocais.indexOfFirst { it.distrito == selectedDistrito } // -1 if not found
//        if (index >= 0) {
//            val locais = mLocais[index]
//
//            mDistrito = locais.prefixo
//            mConcelho = locais.concelho
//
//        }
        listaConcelhos.clear()

        for (i in 0 until mLocais.size) {
            if (mLocais[i].distrito == selectedDistrito) {
                val mConcelho = mLocais[i].concelho
                listaConcelhos.add(mConcelho)
            }
        }


        // aceder ao spinner
        if (spinnerConcelhos != null) {
            val adapterConcelhos = ArrayAdapter(
                requireActivity().baseContext,
                android.R.layout.simple_spinner_item,
                listaConcelhos
            )
            adapterConcelhos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapterConcelhos.notifyDataSetChanged()

            spinnerConcelhos!!.adapter = adapterConcelhos

            spinnerConcelhos!!.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        context,
                        getString(R.string.selected_item) + " " +
                                "" + listaConcelhos[position], Toast.LENGTH_SHORT
                    ).show()
                    selectedConcelho = getString(R.string.selected_item)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        /**SPINNER CONCELHOS **/
    }

    fun spinnerCategorias(){
        /**SPINNER CONCELHOS**/


        mCategoria.groupBy { it.nome }.forEach { (name, list) ->
            listaCategorias.add(name)
        }

        // aceder ao spinner
        if (spinnerCategoria != null) {
            val adapterConcelhos = ArrayAdapter(
                requireActivity().baseContext,
                android.R.layout.simple_spinner_item,
                listaCategorias
            )
            adapterConcelhos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapterConcelhos.notifyDataSetChanged()

            spinnerCategoria!!.adapter = adapterConcelhos

            spinnerCategoria!!.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        context,
                        getString(R.string.selected_item) + " " +
                                "" + mCategoria[position], Toast.LENGTH_SHORT
                    ).show()
                    selectedCategoria = getString(R.string.selected_item)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        /**SPINNER CONCELHOS **/

    }


    fun spinnerEtiquetas(){

        mEtiqueta.groupBy { it.nome }.forEach { (name, list) ->
            listaEtiquetas.add(name)
        }
        // aceder ao spinner
        if (spinnerEtiqueta != null) {
            val adapterEtiqueta = ArrayAdapter(
                requireActivity().baseContext,
                android.R.layout.simple_spinner_item,
                listaEtiquetas
            )
            adapterEtiqueta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapterEtiqueta.notifyDataSetChanged()

            spinnerEtiqueta!!.adapter = adapterEtiqueta

            spinnerEtiqueta!!.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        context,
                        getString(R.string.selected_item) + " " +
                                "" + mEtiqueta[position], Toast.LENGTH_SHORT
                    ).show()
                    selectedEtiqueta = getString(R.string.selected_item)
                    chipsEtiquetas2(listaEtiquetas[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        /**SPINNER CONCELHOS **/

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

}


