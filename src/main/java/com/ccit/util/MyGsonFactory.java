package com.ccit.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyGsonFactory {

    public static Gson buildGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

}
