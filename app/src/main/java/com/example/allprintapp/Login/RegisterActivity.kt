package com.example.allprintapp.Login
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allprintapp.LoginActivity
import com.example.allprintapp.R
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class RegisterActivity : AppCompatActivity() {
    private var etname: EditText? = null
    private var ethobby: EditText? = null
    private var etusername: EditText? = null
    private var etpassword: EditText? = null
    private var btnregister: Button? = null
    private var tvlogin: TextView? = null
    private var preferenceHelper: PreferenceHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceHelper = PreferenceHelper(this)
        if (preferenceHelper!!.isLogin) {
            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
//        etname = findViewById<View>(R.id.etname) as EditText
//        ethobby = findViewById<View>(R.id.ethobby) as EditText
        etusername = findViewById<View>(R.id.etusername) as EditText
        etpassword = findViewById<View>(R.id.etpassword) as EditText
        btnregister = findViewById<View>(R.id.btn_entrar) as Button
//        tvlogin = findViewById<View>(R.id.tvlogin) as TextView
        tvlogin!!.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnregister!!.setOnClickListener { registerMe() }
    }

    private fun registerMe() {
        val name = etname!!.text.toString()
        val hobby = ethobby!!.text.toString()
        val username = etusername!!.text.toString()
        val password = etpassword!!.text.toString()
        val retrofit = Retrofit.Builder()
                .baseUrl(RegisterInterface.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        val api = retrofit.create(RegisterInterface::class.java)
        val call = api.getUserRegi(name, hobby, username, password)
        if (call != null) {
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    Log.i("Responsestring", response.body().toString())
                    //Toast.makeText()
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Log.i("onSuccess", response.body().toString())
                            val jsonresponse = response.body().toString()
                            try {
                                parseRegData(jsonresponse)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        } else {
                            Log.i("onEmptyResponse", "Returned empty response") //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {}
            })
        }
    }

    @Throws(JSONException::class)
    private fun parseRegData(response: String) {
        val jsonObject = JSONObject(response)
        if (jsonObject.optString("status") == "true") {
            saveInfo(response)
            Toast.makeText(this@RegisterActivity, "Registered Successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this@RegisterActivity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
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
                    preferenceHelper!!.putName(dataobj.getString("name"))
                    preferenceHelper!!.putHobby(dataobj.getString("hobby"))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}