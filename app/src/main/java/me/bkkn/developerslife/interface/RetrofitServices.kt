package me.bkkn.developerslife.`interface`

import me.bkkn.developerslife.model.Data
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServices {
    @GET("random?json=true")
    fun getData(): Call<Data>
}
