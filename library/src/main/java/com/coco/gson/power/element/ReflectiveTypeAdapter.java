package com.coco.gson.power.element;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.coco.gson.power.PowerGson;
import com.coco.gson.power.JsonParseExceptionCallback;

import java.io.IOException;
import java.util.Map;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/GsonFactory
 *    time   : 2020/12/08
 *    desc   : Object 解析适配器，参考：{@link com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter}
 */
public class ReflectiveTypeAdapter<T> extends TypeAdapter<T> {

    private final ObjectConstructor<T> mConstructor;
    private final Map<String, ReflectiveFieldBound> mBoundFields;

    private TypeToken<?> mTypeToken;
    private String mFieldName;

    public ReflectiveTypeAdapter(ObjectConstructor<T> constructor, Map<String, ReflectiveFieldBound> fields) {
        mConstructor = constructor;
        mBoundFields = fields;
    }

    public void setReflectiveType(TypeToken<?> typeToken, String fieldName) {
        mTypeToken = typeToken;
        mFieldName = fieldName;
    }

    @Override
    public T read(JsonReader in) throws IOException {
        JsonToken jsonToken = in.peek();

        if (jsonToken == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        if (jsonToken != JsonToken.BEGIN_OBJECT) {
            in.skipValue();
            JsonParseExceptionCallback callback = PowerGson.getJsonParseExceptionCallback();
            if (callback != null) {
                StringBuilder exField = new StringBuilder();
                if(mConstructor != null){
                    if(mConstructor.construct() != null){
                        exField.append(mConstructor.construct().getClass().getName()).append("#");
                    }
                }
                exField.append(mFieldName);
                callback.onTypeException(mTypeToken, exField.toString(), jsonToken);
            }
            return null;
        }

        T instance = mConstructor.construct();
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            ReflectiveFieldBound field = mBoundFields.get(name);
            if (field == null || !field.isDeserialized()) {
                in.skipValue();
                continue;
            }

            JsonToken peek = in.peek();

            try {
                field.read(in, instance);
            } catch (IllegalStateException e) {
                throw new JsonSyntaxException(e);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (IllegalArgumentException e) {
                JsonParseExceptionCallback callback = PowerGson.getJsonParseExceptionCallback();
                if (callback != null) {
                    callback.onTypeException(TypeToken.get(instance.getClass()), field.getFieldName(), peek);
                }
            }
        }
        in.endObject();
        return instance;
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        out.beginObject();
        for (ReflectiveFieldBound fieldBound : mBoundFields.values()) {
            try {
                if (fieldBound.writeField(value)) {
                    out.name(fieldBound.getFieldName());
                    fieldBound.write(out, value);
                }
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
        out.endObject();
    }
}