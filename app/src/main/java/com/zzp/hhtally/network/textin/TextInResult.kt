package com.zzp.hhtally.network.textin

data class TextInResult<T>(
    val code: Int,
    val duration: Int,
    val message: String,
    val result: T,
    val version: String
)
