package com.zzp.hhtally.network

import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.Label
import com.zzp.hhtally.data.chart.MonthInfo
import com.zzp.hhtally.data.chart.WeekInfo
import com.zzp.hhtally.data.chart.YearInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface NetworkService {

    @POST("/user/login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Observable<HttpResult<String>>


    @PUT("/user/register")
    fun register(
        @Query("username") username: String,
        @Query("password") password: String
    ): Observable<HttpResult<Any?>>

    @GET("/label/getAll")
    fun getAllLabels(): Observable<HttpResult<List<Label>>>

    @PUT("/label/add")
    fun addLabel(@Query("labelName") labelName: String): Observable<HttpResult<Int>>

    @DELETE("/label/remove")
    fun removeLabel(@Query("labelId") labelId: Int): Observable<HttpResult<Any?>>

    @GET("/bill/getAll")
    fun getAllBills(): Observable<HttpResult<List<Bill>>>

    @GET("/bill/getByLabel")
    fun getBillsByLabel(@Query("labelId") labelId: Int): Observable<HttpResult<List<Bill>>>

    @GET("/bill/getByDate")
    fun getBillsByDate(@Query("date") date: String): Observable<HttpResult<List<Bill>>>

    @PUT("/bill/add")
    fun addBill(@Body bill: Bill): Observable<HttpResult<Any?>>

    @DELETE("/bill/remove")
    fun removeBill(@Query("billId") billId: Int): Observable<HttpResult<Any?>>

    @GET("chart/getYearInfor")
    fun getYearInfo(@Query("year") year: String): Observable<HttpResult<YearInfo>>

    @GET("chart/getMonthInfor")
    fun getMonthInfo(
        @Query("year") year: String,
        @Query("month") month: String
    ): Observable<HttpResult<MonthInfo>>

    @GET("chart/getWeekInfor")
    fun getWeekInfo(): Observable<HttpResult<WeekInfo>>

    @GET("chart/getYearIncome")
    fun getYearIncome(@Query("year") year: String): Observable<HttpResult<YearInfo>>

    @GET("chart/getMonthIncome")
    fun getMonthIncome(
        @Query("year") year: String,
        @Query("month") month: String
    ): Observable<HttpResult<MonthInfo>>

    @GET("chart/getWeekIncome")
    fun getWeekIncome(): Observable<HttpResult<WeekInfo>>
}
