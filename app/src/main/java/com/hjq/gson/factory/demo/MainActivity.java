package com.hjq.gson.factory.demo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.coco.gson.power.JsonParseExceptionCallback;
import com.coco.gson.power.PowerGson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonToken;
import com.hjq.gson.factory.demo.bean.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private Gson mPowerGson;
    private Gson mNormalGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onTestBefore();
        findViewById(R.id.btn_power_gson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testApiResponseWithJavaBean(mPowerGson);
                testSpecificationWithJavaBean(mPowerGson);
                testNoSpecificationWithJavaBean(mPowerGson);
                testPersonWithKotlinBean(mPowerGson);
            }
        });

        findViewById(R.id.btn_normal_gson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    testApiResponseWithJavaBean(mNormalGson);
                    testSpecificationWithJavaBean(mNormalGson);
                    testNoSpecificationWithJavaBean(mNormalGson);
                    testPersonWithKotlinBean(mNormalGson);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 测试前
     */
    public void onTestBefore() {
        mPowerGson = PowerGson.powerGsonBuilder().create();
        mNormalGson = new GsonBuilder().serializeNulls().create();
        // 设置 Json 解析容错监听
        PowerGson.setJsonParseExceptionCallback(new JsonParseExceptionCallback() {

            @Override
            public void onTypeException(TypeToken<?> typeToken, String fieldName, JsonToken jsonToken) {
                Log.e("power_gson","", new IllegalArgumentException("类型解析异常：" + fieldName + "，后台返回的类型为：" + jsonToken));
            }
        });
    }

    public void testApiResponseWithJavaBean(Gson gson) {
        Context context = this;
        String json = getAssetsString(context, "apiResponse.json");
        ApiResponse<JavaBean> jsonBean = gson.fromJson(json, new TypeToken<ApiResponse<JavaBean>>(){}.getType());
        assertNotNull(jsonBean);
    }

    public void testSpecificationWithJavaBean(Gson gson) {
        Context context = this;
        String json = getAssetsString(context, "Specification.json");
        JavaBean javaBean = gson.fromJson(json, JavaBean.class);
        assertNotNull(javaBean);
    }

    public void testNoSpecificationWithJavaBean(Gson gson) {
        Context context = this;
        String json = getAssetsString(context, "NoSpecification.json");
        JavaBean javaBean = gson.fromJson(json, JavaBean.class);
        assertNotNull(javaBean);
    }

    public void testPersonWithKotlinBean(Gson gson) {
        Context context = this;
        String json = getAssetsString(context, "person.json");
        KotlinPerson javaBean = gson.fromJson(json, KotlinPerson.class);
        System.out.println(javaBean.toString());
        assertNotNull(javaBean);
    }

    /**
     * 测试完成
     */
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

    static public void assertNotNull(Object object) {
        if(object == null){
            throw new RuntimeException("Not Null");
        }
    }

    static public void assertEquals(long expected, long actual) {
        if(expected != actual){
            throw new RuntimeException( expected + " != " + actual);
        }
    }
}