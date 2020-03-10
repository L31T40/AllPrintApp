package com.example.allprintapp.Login

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterInterface {
    @FormUrlEncoded
    @POST("simpleregister.php")
    fun getUserRegi(
            @Field("name") name: String?,
            @Field("hobby") hobby: String?,
            @Field("username") uname: String?,
            @Field("password") password: String?
    ): Call<String?>?

    companion object {
        const val REGIURL = "https://demonuts.com/Demonuts/JsonTest/Tennis/"
    }
}