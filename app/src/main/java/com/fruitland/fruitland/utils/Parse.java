package com.fruitland.fruitland.utils;

import android.app.Activity;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by acer on 4/13/2016.
 */
public class Parse {


    private Activity activity;
    private final boolean KEY_SUCCESS = true;
    private final boolean KEY_ERROR = false;

    private final String KEY_MESSAGE = "message";
    private final String KEY_STATUS = "success";
    public Parse(Activity activity) {
        this.activity = activity;
    }

    public boolean isSuccess(String response) {
        if (TextUtils.isEmpty(response))
            return false;
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean(KEY_STATUS) == KEY_SUCCESS) {
                return true;
            } else {
                Utility.showToast(jsonObject.getString(KEY_MESSAGE), activity);
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
