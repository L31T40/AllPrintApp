package com.example.allprintapp.Login

import android.content.Context
import android.content.SharedPreferences

/**
 *
 */
class PreferenceHelper(context: Context) {
    private val EMAIL = "email"
    private val NAME = "nome"
    private val HOBBY = "hobby"
    private val app_prefs: SharedPreferences
    private val context: Context
    fun putIsLogin(loginorout: Boolean) {
        val edit = app_prefs.edit()
        edit.putBoolean(EMAIL, loginorout)
        edit.apply()
    }

    val isLogin: Boolean
        get() = app_prefs.getBoolean(EMAIL, false)

    fun putName(loginorout: String?) {
        val edit = app_prefs.edit()
        edit.putString(NAME, loginorout)
        edit.apply()
    }

    val name: String?
        get() = app_prefs.getString(NAME, "")

    fun putHobby(loginorout: String?) {
        val edit = app_prefs.edit()
        edit.putString(HOBBY, loginorout)
        edit.apply()
    }

    val hobby: String?
        get() = app_prefs.getString(HOBBY, "")

    init {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE)
        this.context = context
    }
}