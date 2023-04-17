package com.zzp.hhtally.util

import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.core.net.toFile
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

fun uri2RequestBody(uri: Uri): RequestBody =
    RequestBody.create(MediaType.parse("image/jpeg"), uri.toFile())

fun byteArray2RequestBody(bytes: ByteArray): RequestBody =
    RequestBody.create(MediaType.parse("application/octet-stream"), bytes)
//    RequestBody.create(MediaType.parse("image/jpeg"), bytes)

fun bitmap2ByteArray(bitmap: Bitmap): ByteArray {
    val byteArrayOutPutStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutPutStream)
    byteArrayOutPutStream.close()
    return byteArrayOutPutStream.toByteArray()
}

fun bitmap2RequestBody(bitmap: Bitmap) = byteArray2RequestBody(bitmap2ByteArray(bitmap))

fun bitmap2Base64(image: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.JPEG, 100,
        byteArrayOutputStream)
    byteArrayOutputStream.flush()
    byteArrayOutputStream.close()
    val imageByteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(imageByteArray, Base64.DEFAULT)
}

fun base642ByteArray(base64: String): ByteArray = Base64.decode(base64, Base64.DEFAULT)

