package com.example.allprintapp.ui.filtrosprodutos

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.example.allprintapp.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_filtro_produtos.*
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FiltroProdutosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FiltroProdutosFragment :  DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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



        /** implementaçao de chips**/
        // set the button click listener
        val btnfiltrar=   view.findViewById<Button>(R.id.button_Filtro)
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


        /** implementaçao de chips**/


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

}


