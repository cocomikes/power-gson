package com.hjq.gson.factory.demo.bean

import java.math.BigDecimal

/**
 * Created by LiYunLong at 2021/11/21 15:03
 * ================================================
 * ================================================
 */
data class KotlinBean (
    val listTest1 : TestBean,
    val listTest2: List<String>,
    val listTest3: List<Int>,
    val listTest4: List<Boolean>,

    val booleanTest1 : Boolean,
    val booleanTest2 : Boolean,
    val booleanTest3 : Boolean,
    val booleanTest4 : Boolean,
    val booleanTest5 : Boolean,
    val booleanTest6 : Boolean,

    val stringTest1: String,
    val stringTest2: String,
    val stringTest3: String,
    val stringTest4: String,
    val stringTest5: String,

    val intTest1 : Int,
    val intTest2 : Int,
    val intTest3 : Int,
    val intTest4 : Int,
    val intTest5 : Int,

    val longTest1: Long,
    val longTest2: Long,
    val longTest3: Long,
    val longTest4: Long,
    val longTest5: Long,

    val floatTest1: Float,
    val floatTest2: Float,
    val floatTest3: Float,
    val floatTest4: Float,
    val floatTest5: Float,

    val doubleTest1 : Double,
    val doubleTest2 : Double,
    val doubleTest3 : Double,
    val doubleTest4 : Double,
    val doubleTest5 : Double,

    val bigDecimal1: BigDecimal,
    val bigDecimal2: BigDecimal,
    val bigDecimal3: BigDecimal,
){
    inner class TestBean{
        val number : Int = 0
    }
}