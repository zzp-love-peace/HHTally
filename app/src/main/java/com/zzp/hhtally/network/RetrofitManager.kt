package com.zzp.hhtally.network

import com.zzp.hhtally.data.*
import com.zzp.hhtally.data.UserData
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val okBuilder = OkHttpClient.Builder().connectTimeout(5L, TimeUnit.SECONDS).addInterceptor {
            val original = it.request()
            val request =
                original.newBuilder().header(TOKEN_VALUE, UserData.token)
                    .method(original.method(), original.body())
                    .build()
            return@addInterceptor it.proceed(request)
        }
        return okBuilder.build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}