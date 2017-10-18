package com.example.finance.androidwebview;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/23.
 */

public class GsonUtils {
    private static Gson mGson = new Gson();


    public static String castObjectJson(Object object) {
        return mGson.toJson(object);
    }

    public static <T> T castJsonObject(String json, Class<T> type) {
        return mGson.fromJson(json, type);
    }

    public static <T> List<T> castJsonObjList(String json, Class<T> type) {
        JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
        ArrayList<T> arrayList = new ArrayList<>(jsonArray.size());
        for (final JsonElement element : jsonArray) {
            arrayList.add(mGson.fromJson(element, type));
        }
        return arrayList;
    }

}
