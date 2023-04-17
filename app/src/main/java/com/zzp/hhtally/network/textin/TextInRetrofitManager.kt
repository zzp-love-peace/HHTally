package com.zzp.hhtally.network.textin

import com.zzp.hhtally.data.BASE_URL
import com.zzp.hhtally.data.TEXT_IN_BASE_URL
import com.zzp.hhtally.network.NetworkService
import com.zzp.hhtally.network.RetrofitManager
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TextInRetrofitManager {

    private val appId = "030451c62bb1d44fb2389ad1636cc4b7"
    private val secretCode = "2d34835bf5b9c88d7aa25d71744d461b"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(TEXT_IN_BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    val apiService by lazy {
        RetrofitManager.create<TextInService>()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val okBuilder = OkHttpClient.Builder().connectTimeout(8L, TimeUnit.SECONDS).addInterceptor {
            val original = it.request()
            val request =
                original.newBuilder()
                    .header("connection", "Keep-Alive")
//                    .header("Content-Type", "application/octet-stream")
                    .header("x-ti-app-id", appId)
                    .header("x-ti-secret-code", secretCode)
                    .build()
            return@addInterceptor it.proceed(request)
        }
        return okBuilder.build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}