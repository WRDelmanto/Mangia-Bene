package com.example.mangiabene.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class FastSharedPreferences {
    private static SharedPreferences sharedPreferences;
    private static final Gson gson = new Gson();  // Gson instance

    private static void configure(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("FastSharedPreferences", Context.MODE_PRIVATE);
        }
    }

    public static void put(Context context, String key, Object value) {
        configure(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String || value instanceof Integer || value instanceof Boolean ||
                value instanceof Float || value instanceof Long) {
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            }
        } else {
            String json = gson.toJson(value);
            editor.putString(key, json);
        }
        editor.apply();
    }

    public static <T> T get(Context context, String key, Type typeOfT, T defaultValue) {
        configure(context);
        String json = sharedPreferences.getString(key, null);
        if (json != null) {
            return gson.fromJson(json, typeOfT);
        }
        return defaultValue;
    }
}
