/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fan.service.rest.converter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    private String replaceStr(String str) {
        Pattern pattern = Pattern.compile("^\\{+|\\}+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("");
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String original = value.string();
            JSONObject jsonContent = null;
            try {
                jsonContent = new JSONObject(original);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int code = jsonContent.optInt("err");
            String msg = jsonContent.optString("msg");
            if (code != 0 && code != -333) {
                try {
                    String data = jsonContent.optString("data");
                    if (data.equals("")) {
                        original = "{\"err\":"+code+",\"msg\":\""+msg+"\",\"data\":null}";
                    }
                } catch (Exception e) {
                    original = "{\"err\":"+code+",\"msg\":\""+msg+"\",\"data\":null}";
                }
            }
            Reader reader = new StringReader(original);
            JsonReader jsonReader = gson.newJsonReader(reader);
            Log.e("....", original);
            return adapter.read(jsonReader);

        } finally {
            value.close();
        }
    }
}


