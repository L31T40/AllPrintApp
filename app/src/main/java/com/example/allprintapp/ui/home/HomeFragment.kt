package com.example.allprintapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.example.allprintapp.R
import com.example.allprintapp.ui.encomendas.ListaEncomendasFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class HomeFragment : Fragment() {



      @SuppressLint("WrongViewCast")
      override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
      ): View? {
          // Inflate the layout for this fragment
          val view =  inflater.inflate(R.layout.fragment_home, container, false)

          val btnEncomenda = view.findViewById<View>(R.id.button2) as Button

          btnEncomenda.setOnClickListener {
              //replaceFragment(ListagemProdutosFragment.newInstance())
              navigateToFragment(ListaEncomendasFragment.newInstance())

          }
          return  view
      }

    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
