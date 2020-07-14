package com.example.allprintapp.ui.utils;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class VarGlobals extends Application {
    String NFuncGlobal=""; //Numero de funcionario
    String idFuncGlobal=""; //Numero de funcionario
    int succeed=0;
    ArrayList<String> listaLocais = null; // Lista dos locais
    ArrayList<HashMap<String, String>>  listaLocais1 = null; // Lista dos locais
    ArrayList<String> listaCombustivel = null; //
    ArrayList<HashMap<String, String>> ViaturasUser = null; // Lista de viatura do Utilizador + Empresa
    Boolean flagDecoration = false; // para detetar decoração na recyclerview
}