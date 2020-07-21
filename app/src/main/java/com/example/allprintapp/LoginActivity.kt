package com.example.allprintapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
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
import retrofit2.converter.scalars.ScalarsConverterFactory


class LoginActivity : AppCompatActivity() {
    private var etEmail: EditText? = null
    private var etPass: EditText? = null
    private var btnlogin: Button? = null
   // private var tvreg: TextView? = null
    private var preferenceHelper: PreferenceHelper? = null
    private var mProgressView: View? = null
    private var mLoginFormView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        preferenceHelper = PreferenceHelper(this)
        etEmail = findViewById<View>(R.id.etusername) as EditText
        etPass = findViewById<View>(R.id.etpassword) as EditText
        btnlogin = findViewById<View>(R.id.btn_entrar) as Button
        btnlogin!!.setOnClickListener { loginUser() }
        mProgressView = findViewById(R.id.progressbar_login)
        mLoginFormView = findViewById(R.id.login_form)
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
        showProgress(true)
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
                            //Toast.makeText(this@LoginActivity, "ENTROU"+response.body().toString(), Toast.LENGTH_LONG).show()

                            // lança o spinner de progresso e ao mesmo tempo a asynctask de login

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


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        val shortAnimTime =
            resources.getInteger(android.R.integer.config_mediumAnimTime)
        mLoginFormView?.visibility = if (show) View.GONE else View.VISIBLE
        mLoginFormView?.animate()?.setDuration(shortAnimTime.toLong())?.alpha(
            if (show) 0F else 5.toFloat()
        )?.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mLoginFormView?.visibility = if (show) View.GONE else View.VISIBLE
            }
        })
        mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
        mProgressView!!.animate().setDuration(shortAnimTime.toLong()).alpha(
            if (show) 5F else 0.toFloat()
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }

}

