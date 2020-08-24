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
import android.widget.*
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.size
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.example.allprintapp.LoginActivity.Companion.ListagemCategorias
import com.example.allprintapp.MainActivity
import com.example.allprintapp.LoginActivity.Companion.ListagemCategoriasCompleta
import com.example.allprintapp.LoginActivity.Companion.ListagemDistritos
import com.example.allprintapp.LoginActivity.Companion.ListagemEtiquetas
import com.example.allprintapp.LoginActivity.Companion.ListagemEtiquetasCompleta
import com.example.allprintapp.R
import com.example.allprintapp.ui.listaprodutos.ListagemProdutosFragment.Companion.flag_pesquisa
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
private val filtraDistritoId : ArrayList<String> = ArrayList() // Array para colocar nomes de Concelhos conforme o distrito seleccionado
private val filtraEtiquetaId : ArrayList<String> = ArrayList() // Array para colocar nomes de Concelhos conforme o distrito seleccionado
private var filtraCategoriaId : String? = null // Array para colocar nomes de Concelhos conforme o distrito seleccionado

private val mLocais = ListagemDistritos //Array de distrito e concelhos
private val mCategoria = ListagemCategorias //Array de categorias
private val mEtiqueta = ListagemEtiquetas//Array de etiquetas
private var mDistrito: String? = null
private var mConcelho: String? = null
//private var selectedDistrito: String? = null
//private var selectedConcelho: String? = null
//private var selectedCategoria: String? = null
//private var selectedEtiqueta: String? = null
//private var selectedChips: List<String>? = null
private var spinnerDistritos: Spinner? = null
private var spinnerConcelhos: Spinner? = null
private var spinnerCategoria: Spinner? = null
private var spinnerEtiqueta: Spinner? = null
private var btnFiltrar: Button? = null


/**
 * A simple [Fragment] subclass.
 * Use the [FiltroProdutosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FiltroProdutosDialogFragment  :  DialogFragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    companion object {

        var selectedDistrito: String? = null
        var selectedConcelho: String? = null
        var selectedCategoria: String? = null
        var selectedEtiqueta: String? = null
        var selectedChips: List<String>? = null
        var stringPesquisa: String? = null


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
        btnFiltrar = view.findViewById<View>(R.id.button_Filtro) as Button
       // btnFiltrar!!.setOnClickListener { filtrarPesquisaProdutos() }
        btnFiltrar!!.setOnClickListener{

        }
        spinnerDistritos!!.onItemSelectedListener=this

        spinnerDistritos()
        spinnerCategorias()
        spinnerEtiquetas()


//
//        if (dialog != null && dialog?.window != null) {
//            dialog?.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
//            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
//        }

        return view
    }

    // Toast extension method
    fun Fragment.toast(message: String, toastLength: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, toastLength).show()
    }




    @SuppressLint("SetTextI18n")
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
        Log.i(TAG, "+========================== CHIPPPPSSS -> ${selectedChips!![0]}")
    //    Log.i(TAG, "+========================== CHIPPPPSSS -> ${selectedChips!!.get(1)}")

    }



    fun spinnerDistritos(){
        /**SPINNER DISTRITOS**/



//        val existeDistrito= MainActivity.ListagemDistritos.find { it.distrito == etiqueta }
//        val existeConcelho= MainActivity.ListagemDistritos.find { it.concelho == etiqueta }
//        if (((MainActivity.ListagemEtiquetas.find { it.nome == etiqueta  }==null) && (MainActivity.ListagemCategorias.find { it.nome == etiqueta  }==null)) && (existeDistrito==null || existeConcelho==null )) {
//            MainActivity.ListagemEtiquetas!!.add(
//                ListaEtiquetasModel(
//                    etiquetaId,
//                    etiqueta
//                )
//            )


        //val index = mLocais.indexOfFirst { it.prefixo == "1AV3AN_" } // -1 if not found
        listaDistritos.clear()
        //Coloca apenas os distritos com produtos no spinner
        mLocais.groupBy { it.distrito }.forEach { (name, list) ->
            val existeDistrito= ListagemCategoriasCompleta.find { it.nome == name }
            if(existeDistrito!=null){
                listaDistritos.add(name)
            }

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


        val index = mLocais.indexOfFirst { it.distrito == selectedDistrito } // -1 if not found

        listaConcelhos.clear()


        for (i in 0 until mLocais.size) {
            if (mLocais[i].distrito == selectedDistrito) {
                val mConcelho = mLocais[i].concelho
                val existeDistrito= ListagemCategoriasCompleta.find { it.nome == mConcelho }
                if(existeDistrito!=null){
                    listaConcelhos.add(mConcelho)
                }

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
                    //selectedConcelho = getString(R.string.selected_item)
                    selectedConcelho = listaConcelhos[position]
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
        listaCategorias.clear()

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
                    //selectedCategoria = getString(R.string.selected_item)
                    selectedCategoria = listaCategorias[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        /**SPINNER CONCELHOS **/

    }


    fun spinnerEtiquetas(){
        listaEtiquetas.clear()
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
                    Toast.makeText( context, getString(R.string.selected_item) + " " +" " + mEtiqueta[position].nome, Toast.LENGTH_SHORT).show()
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


    fun filtrarPesquisaProdutos(){

        // devolve ID das categorias relacionadas com distrito
        val filtraDistrito= selectedDistrito

        for (i in 0 until ListagemCategoriasCompleta.size) {
            if (ListagemCategoriasCompleta[i].nome == filtraDistrito) {
                 val _filtraDistritoId = ListagemCategoriasCompleta[i].id
                    filtraDistritoId.add(_filtraDistritoId)
            }
        }

        // devolve ID das categorias relacionadas com Concelho verificando se existe esse ID no array
        val filtraConcelho=  selectedConcelho
        for (i in 0 until ListagemCategoriasCompleta.size) {

            if (ListagemCategoriasCompleta[i].nome == filtraConcelho) {
                val _filtraDistritoId = ListagemCategoriasCompleta[i].id
                val existeDistrito= filtraDistritoId.find { it == _filtraDistritoId }
                if(existeDistrito==null) {
                    filtraDistritoId.add(_filtraDistritoId)
                }
            }
        }

        // devolve ID da  categoria selecionada

        val filtraCategoria= selectedCategoria
        for (i in 0 until ListagemCategoriasCompleta.size) {

            if (ListagemCategoriasCompleta[i].nome == filtraCategoria) {
                val _filtraCategoriaId = ListagemCategoriasCompleta[i].id
                //val existeDistrito= filtraDistritoId.find { it == _filtraCategoriaId }
                //if(existeDistrito==null) {
                    filtraCategoriaId=_filtraCategoriaId
                //}
            }
        }




        // devolve ID das etiquetas selecionadas
        val filtraEtiqueta= selectedChips
        for (i in 0 until ListagemEtiquetasCompleta.size) {
            if (filtraEtiqueta != null) {
                for (ii in 0 until filtraEtiqueta.size) {
                    if (ListagemEtiquetasCompleta[i].nome == filtraEtiqueta[ii]) {
                        val _filtraEtiquetaId = ListagemEtiquetasCompleta[i].id
                        //  val existeDistrito= filtraDistritoId.find { it == _filtraEtiquetaId }
                        //  if(existeDistrito==null) {
                        filtraEtiquetaId.add(_filtraEtiquetaId)
                        // }
                    }
                }
            }
        }


        val _filtrodistrito=filtraDistritoId.joinToString( prefix = "category=", separator = "&category=")
        val _filtroetiqueta= filtraEtiquetaId.joinToString( prefix = "tag=", separator = "&tag=")
        stringPesquisa=""
        stringPesquisa= "$_filtrodistrito&category=$filtraCategoriaId$_filtroetiqueta"
        flag_pesquisa=true



        this.dismiss()

        Log.i(TAG, "+========================== CAT FILTER -> $stringPesquisa")

    }



    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

}


