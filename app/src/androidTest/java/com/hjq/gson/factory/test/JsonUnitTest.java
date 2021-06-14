package com.hjq.gson.factory.test;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.coco.gson.power.JsonParseExceptionCallback;
import com.coco.gson.power.PowerGson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonToken;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/GsonFactory
 *    time   : 2020/11/10
 *    desc   : Gson 解析容错适配器测试用例
 *    doc    : https://developer.android.google.cn/studio/test
 */
@RunWith(AndroidJUnit4.class)
public final class JsonUnitTest{

    private Gson mPowerGson;
    private Gson mNormalGson;

    /**
     * 测试前
     */
    @Before
    public void onTestBefore() {
        mPowerGson = PowerGson.powerGsonBuilder().create();
        mNormalGson = new Gson();
        // 设置 Json 解析容错监听
        PowerGson.setJsonParseExceptionCallback(new JsonParseExceptionCallback() {

            @Override
            public void onTypeException(TypeToken<?> typeToken, String fieldName, JsonToken jsonToken) {
                Log.e("power_gson","", new IllegalArgumentException("类型解析异常：" + fieldName + "，后台返回的类型为：" + jsonToken));
            }
        });
    }

    @Test
    public void testApiResponseWithPowerGson() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        String json = getAssetsString(context, "apiResponse.json");
        ApiResponse<JsonBean> jsonBean = mPowerGson.fromJson(json, new TypeToken<ApiResponse<JsonBean>>(){}.getType());
        Assert.assertNotNull(jsonBean);
    }

    @Test
    public void testSpecificationWithPowerGson() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        String json = getAssetsString(context, "Specification.json");
        JsonBean jsonBean = mPowerGson.fromJson(json, JsonBean.class);
        Assert.assertNotNull(jsonBean);
    }

    @Test
    public void testNoSpecificationWithPowerGson() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        String json = getAssetsString(context, "NoSpecification.json");
        JsonBean jsonBean = mPowerGson.fromJson(json, JsonBean.class);
        Assert.assertNotNull(jsonBean);
    }

    @Test
    public void testApiResponseWithNormalGson() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        String json = getAssetsString(context, "apiResponse.json");
        ApiResponse<JsonBean> jsonBean = mNormalGson.fromJson(json, new TypeToken<ApiResponse<JsonBean>>(){}.getType());
        Assert.assertNotNull(jsonBean);
    }

    @Test
    public void testSpecificationWithNormalGson() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        String json = getAssetsString(context, "Specification.json");
        JsonBean jsonBean = mNormalGson.fromJson(json, JsonBean.class);
        Assert.assertNotNull(jsonBean);
    }

    @Test
    public void testNoSpecificationWithNormalGson() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        String json = getAssetsString(context, "NoSpecification.json");
        JsonBean jsonBean = mNormalGson.fromJson(json, JsonBean.class);
        Assert.assertNotNull(jsonBean);
    }

    /**
     * 测试完成
     */
    @After
    public void onTestAfter() {
        mPowerGson = null;
        mNormalGson = null;
    }

    /**
     * 获取资产目录下面文件的字符串
     */
    private static String getAssetsString(Context context, String file) {
        try {
            InputStream inputStream = context.getAssets().open(file);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, length);
            }
            outStream.close();
            inputStream.close();
            return outStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}