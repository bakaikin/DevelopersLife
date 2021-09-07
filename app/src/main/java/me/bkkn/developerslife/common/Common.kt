package me.bkkn.developerslife.common

import me.bkkn.developerslife.`interface`.RetrofitServices
import me.bkkn.developerslife.retrofit.RetrofitClient

object Common {
    private val BASE_URL = "https://developerslife.ru/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)

    fun toHttps(http: String?): String {
        if (http != null) {
            return http.substring(0, 4) + "s" + http.substring(4, http.length)
        } else
            return ""

    }
}