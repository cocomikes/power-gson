package com.hjq.gson.factory.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.coco.gson.power.PowerGson;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = PowerGson.powerGsonBuilder().create();
    }
}