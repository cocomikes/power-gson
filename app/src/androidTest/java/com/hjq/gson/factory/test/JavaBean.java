package com.hjq.gson.factory.test;

import java.math.BigDecimal;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/GsonFactory
 *    time   : 2020/11/10
 *    desc   : Gson 解析容错模拟数据
 */
public final class JavaBean {

    private TestBean listTest1;
    private List<String> listTest2;
    private List<Integer> listTest3;
    private List<Boolean> listTest4;

    private boolean booleanTest1;
    private boolean booleanTest2;
    private boolean booleanTest3;
    private boolean booleanTest4;
    private boolean booleanTest5;
    private boolean booleanTest6;

    private String stringTest1;
    private String stringTest2;
    private String stringTest3;
    private String stringTest4;
    private String stringTest5;

    private int intTest1;
    private int intTest2;
    private int intTest3;
    private int intTest4;
    private int intTest5;

    private long longTest1;
    private long longTest2;
    private long longTest3;
    private long longTest4;
    private long longTest5;

    private float floatTest1;
    private float floatTest2;
    private float floatTest3;
    private float floatTest4;
    private float floatTest5;

    private double doubleTest1;
    private double doubleTest2;
    private double doubleTest3;
    private double doubleTest4;
    private double doubleTest5;

    private BigDecimal bigDecimal1;
    private BigDecimal bigDecimal2;
    private BigDecimal bigDecimal3;

    private TestBean bean;

    private static class TestBean {

        private int number;
    }
}