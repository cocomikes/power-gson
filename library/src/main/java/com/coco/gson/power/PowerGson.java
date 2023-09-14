package com.coco.gson.power;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.ReflectionAccessFilter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.TypeAdapters;
import com.coco.gson.power.data.BigDecimalTypeAdapter;
import com.coco.gson.power.data.BooleanTypeAdapter;
import com.coco.gson.power.data.DoubleTypeAdapter;
import com.coco.gson.power.data.FloatTypeAdapter;
import com.coco.gson.power.data.IntegerTypeAdapter;
import com.coco.gson.power.data.LongTypeAdapter;
import com.coco.gson.power.data.StringTypeAdapter;
import com.coco.gson.power.element.CollectionTypeAdapterFactory;
import com.coco.gson.power.element.ReflectiveTypeAdapterFactory;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/GsonFactory
 *    time   : 2020/11/10
 *    desc   : Gson 解析容错适配器
 */
public final class PowerGson {

    private static final HashMap<Type, InstanceCreator<?>> INSTANCE_CREATORS = new HashMap<>(0);

    private static final List<TypeAdapterFactory> TYPE_ADAPTER_FACTORIES = new ArrayList<TypeAdapterFactory>();

    private static JsonParseExceptionCallback sJsonParseExceptionCallback;

    private PowerGson() {}

    /**
     * Json解析异常回调
     */
    public static void setJsonParseExceptionCallback(JsonParseExceptionCallback callback) {
        sJsonParseExceptionCallback = callback;
    }

    public static JsonParseExceptionCallback getJsonParseExceptionCallback() {
        return sJsonParseExceptionCallback;
    }

    /**
     * 注册类型适配器
     */
    public static void registerTypeAdapterFactory(TypeAdapterFactory factory) {
        TYPE_ADAPTER_FACTORIES.add(factory);
    }

    /**
     * 注册构造函数创建器
     *
     * @param type                  对象类型
     * @param creator               实例创建器
     */
    public static void registerInstanceCreator(Type type, InstanceCreator<?> creator) {
        INSTANCE_CREATORS.put(type, creator);
    }

    /**
     * 创建 Gson 构建对象
     */
    public static GsonBuilder powerGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        for (TypeAdapterFactory typeAdapterFactory : TYPE_ADAPTER_FACTORIES) {
            gsonBuilder.registerTypeAdapterFactory(typeAdapterFactory);
        }
        return powerGsonBuilder(gsonBuilder);
    }

    public static GsonBuilder powerGsonBuilder(GsonBuilder gsonBuilder) {
        for (TypeAdapterFactory typeAdapterFactory : TYPE_ADAPTER_FACTORIES) {
            gsonBuilder.registerTypeAdapterFactory(typeAdapterFactory);
        }
        ConstructorConstructor constructor = new ConstructorConstructor(INSTANCE_CREATORS, true, new ArrayList<ReflectionAccessFilter>());
        return gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(String.class, new StringTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(boolean.class, Boolean.class, new BooleanTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(int.class, Integer.class, new IntegerTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(long.class, Long.class, new LongTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(float.class, Float.class, new FloatTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(double.class, Double.class, new DoubleTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(BigDecimal.class, new BigDecimalTypeAdapter()))
                .registerTypeAdapterFactory(new CollectionTypeAdapterFactory(constructor))
                .registerTypeAdapterFactory(new ReflectiveTypeAdapterFactory(constructor, FieldNamingPolicy.IDENTITY, Excluder.DEFAULT));
    }
}