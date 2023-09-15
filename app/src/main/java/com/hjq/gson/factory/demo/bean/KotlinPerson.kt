package com.hjq.gson.factory.demo.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by LiYunLong at 2021/11/22 09:01
 * ================================================
 * ================================================
 */
data class KotlinPerson(
    @SerializedName(value = "status", alternate = ["code"])
    var status : Int = 0,
    var arg : Int = 0
)