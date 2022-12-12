package com.zzp.hhtally.network

data class HttpResult<T>(var code: Int, var data: T, var msg: String)
