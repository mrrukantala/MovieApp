package com.rukantala.movieapp.utils

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.rukantala.movieapp.domain.entity.BasicEntity
import okhttp3.ResponseBody

data class BasicResponse(
    @SerializedName("status_code") val status: Boolean?,
    @SerializedName("status_message") val message: String?,
    @SerializedName("success") val isSuccess: Boolean?
) {
    fun toBasicEntity() = BasicEntity(status, message, isSuccess)
}

fun isNotSuccesfull(responseReader: ResponseBody) : BasicResponse{
    val type = object : TypeToken<BasicResponse>(){}.type
    val err: BasicResponse = Gson().fromJson(responseReader.charStream(), type)
    return err
}