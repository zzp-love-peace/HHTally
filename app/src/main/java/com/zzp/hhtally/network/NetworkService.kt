package com.zzp.hhtally.network

import retrofit2.http.POST
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Query

interface NetworkService {

//    @FormUrlEncoded
//    @POST("/user/login")
//    fun login(
//        @Field("username") username: String,
//        @Field("password") password: String
//    ): Observable<HttpResult<String>>

    @POST("/user/login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Observable<HttpResult<String>>

}