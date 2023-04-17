package com.zzp.hhtally.network.textin

import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface TextInService {

    //图像切边矫正
    @POST("/ai/service/v1/dewarp")
    fun imgDewarp(@Body imgBody: RequestBody): Observable<TextInResult<DewarpData>>

    //图像切边增强
    @POST("/ai/service/v1/crop_enhance_image")
    fun imgCropEnhance(@Body imgBody: RequestBody): Observable<TextInResult<CropEnhanceData>>

    //通用文字识别
    @POST("/ai/service/v2/recognize")
    fun textRecognize(@Body imgBody: RequestBody): Observable<TextInResult<RecognizeData>>

    //通用票据识别
    @POST("/api/bills_crop")
    fun billsCrop(@Body imgBody: RequestBody): Observable<TextInResult<BillsCropData>>
}