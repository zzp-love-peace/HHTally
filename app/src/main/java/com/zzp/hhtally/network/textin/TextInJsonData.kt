package com.zzp.hhtally.network.textin

data class DewarpData(val image: String)//返回的base64字符串

data class CropEnhanceData(
    val image_list: List<Image>) {
    data class Image(
        val angle: Int,
        val cropped_height: Int,
        val cropped_width: Int,
        val image: String,//返回的base64字符串
        val position: List<Int>
    )
}

data class RecognizeData(
    val angle: Int,
    val height: Int,
    val lines: List<Line>,
    val width: Int
) {
    data class Line(
        val angle: Int,
        val direction: Int,
        val handwritten: Int,
        val position: List<Int>,
        val score: Double,
        val text: String,//识别结果
        val type: String
    )
}

data class BillsCropData(
    val object_list: List<Object>
) {
    data class Object(
        val `class`: String,
        val image_angle: Int,
        val item_list: List<Item>,
        val position: List<Int>,
        val rotated_image_height: Int,
        val rotated_image_width: Int,
        val type: String,//行程单、商户小票、火车票等票据类型
        val type_description: String,
        val kind: String,//交通、办公、日用等票据大类
        val kind_description: String,
    ) {
        data class Item(
            val description: String,
            val key: String,
            val position: List<Int>,
            val value: String
        )
    }
}