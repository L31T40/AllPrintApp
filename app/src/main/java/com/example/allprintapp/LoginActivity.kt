package com.example.allprintapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allprintapp.Login.PreferenceHelper
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class LoginActivity : AppCompatActivity() {
    private var etEmail: EditText? = null
    private var etPass: EditText? = null
    private var btnlogin: Button? = null
   // private var tvreg: TextView? = null
    private var preferenceHelper: PreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        preferenceHelper = PreferenceHelper(this)
        etEmail = findViewById<View>(R.id.etusername) as EditText
        etPass = findViewById<View>(R.id.etpassword) as EditText
        btnlogin = findViewById<View>(R.id.btn_entrar) as Button
        btnlogin!!.setOnClickListener { loginUser() }
    }


    private fun loginUser() {
//        val email = etEmail!!.text.toString().trim { it <= ' ' }
//        val password = etPass!!.text.toString().trim { it <= ' ' }
        val email = "cenas@cenas.pt"
        val password = "12345"

        val retrofit = Retrofit.Builder()
            .baseUrl(LoginInterface.LOGINURL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val api = retrofit.create(LoginInterface::class.java)

        val call = api.getUserLogin(email, password)

        if (call != null) {
            call.enqueue(object : Callback<String?>{
                //  call!!.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    Log.i("Response string -> ", response.body().toString())
                    //Toast.makeText()
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Log.i("onSuccess", response.body().toString())
                            val jsonresponse = response.body().toString()
                            parseLoginData(jsonresponse)
                            Toast.makeText(this@LoginActivity, "ENTROU"+response.body().toString(), Toast.LENGTH_LONG).show()

                        } else {
                            Log.i("onEmptyResponse", "Não Devolveu resposta") //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {}
            })
        }
    }


    private fun parseLoginData(response: String) {
        try {
            val jsonObject = JSONObject(response)
            if (jsonObject.has("success")) {//if (jsonObject.getString("status") == "true") {
                saveInfo(response)
                Toast.makeText(this@LoginActivity,"lOGIN BEM SUCEDIDO", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun saveInfo(response: String) {
        preferenceHelper!!.putIsLogin(true)
        try {
            val jsonObject = JSONObject(response)
            if (jsonObject.getString("status") == "true") {
                val dataArray = jsonObject.getJSONArray("data")
                for (i in 0 until dataArray.length()) {
                    val dataobj = dataArray.getJSONObject(i)
                    preferenceHelper!!.putName(dataobj.getString("email"))
                    preferenceHelper!!.putHobby(dataobj.getString("password"))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}

