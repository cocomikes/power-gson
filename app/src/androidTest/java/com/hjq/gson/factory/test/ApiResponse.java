package com.hjq.gson.factory.test;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LiYunLong at 2021/6/11 16:54
 * ================================================
 * ================================================
 */
public class ApiResponse<T> {
    @SerializedName(value = "status", alternate = {"code"})
    private int status;
    @SerializedName(value = "message", alternate = {"msg"})
    private String message;
    @SerializedName(value = "data", alternate = {"pay_status"})
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
