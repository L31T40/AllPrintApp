package com.example.allprintapp.Login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.allprintapp.R

class WelcomeActivity : AppCompatActivity() {
    private var tvname: TextView? = null
    private var tvhobby: TextView? = null
    private var btnlogout: Button? = null
    private var preferenceHelper: PreferenceHelper? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        preferenceHelper = PreferenceHelper(this)
        tvhobby = findViewById<View>(R.id.tvhobby) as TextView
        tvname = findViewById<View>(R.id.tvname) as TextView
        btnlogout = findViewById<View>(R.id.btn_entrar) as Button
        tvname!!.text = "Welcome " + preferenceHelper!!.name
        tvhobby!!.text = "Your hobby is " + preferenceHelper!!.hobby
        btnlogout!!.setOnClickListener {
            preferenceHelper!!.putIsLogin(false)
            val intent = Intent(this@WelcomeActivity, MainActivityX::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}