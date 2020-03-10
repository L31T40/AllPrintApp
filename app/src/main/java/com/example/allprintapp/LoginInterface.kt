package com.example.allprintapp

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginInterface {
    @FormUrlEncoded
    @POST("login")
    fun getUserLogin(
            @Field("email") email: String?,
            @Field("password") password: String?
    ): Call<String?>?

    companion object {
        //String LOGINURL = "https://demonuts.com/Demonuts/JsonTest/Tennis/";
        const val LOGINURL = "http://www.middleware.allprint.pt/api/"
    }
}