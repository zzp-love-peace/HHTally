package com.zzp.hhtally.network.textin

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface TextInService {

    //图像切边矫正
    @POST("/ai/service/v1/dewarp")
    fun imgDewarp(@Body imgBody: RequestBody): TextInResult<DewarpData>

    //图像切边增强
    @POST("/ai/service/v1/crop_enhance_image")
    fun imgCropEnhance(@Body imgBody: RequestBody): TextInResult<CropEnhanceData>

    //通用文字识别
    @POST("/ai/service/v2/recognize")
    fun textRecognize(@Body imgBody: RequestBody): TextInResult<RecognizeData>

    @POST("/robot/v1.0/api/bills_crop")
    fun billsCrop(@Body imgBody: RequestBody): TextInResult<BillsCropData>
}