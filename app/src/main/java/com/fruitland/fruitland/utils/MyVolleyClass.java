package com.fruitland.fruitland.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fruitland.fruitland.R;

import java.util.HashMap;
import java.util.Map;


public class MyVolleyClass {
    String url;
    Context activity;
    ProgressDialog pDialog;
    private int serviceCode;
    private Map<String, String> map;
    private boolean isGetMethod = false;
    private VolleyCompleteListener mVolleylistener;


    public MyVolleyClass(Activity activity, HashMap<String, String> map, int serviceCode, VolleyCompleteListener volleyCompleteListener) {

        this.map = map;
        this.serviceCode = serviceCode;
        this.activity = activity;
        this.isGetMethod = false;
        if (Utility.isNetworkAvailable(activity)) {
            mVolleylistener = (VolleyCompleteListener) volleyCompleteListener;
            myBackgroundClass(activity, serviceCode, map, mVolleylistener);
        } else {
            Utility.showToast(
                    activity.getResources().getString(R.string.no_internet), activity);
        }
    }

    private void myBackgroundClass(final Context context, int serviceCode, final Map<String, String> map, VolleyCompleteListener volleyCompleteListener) {

        final int code = serviceCode;
        url = map.get("url");
        map.remove("url");

        mVolleylistener = (VolleyCompleteListener) volleyCompleteListener;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mVolleylistener.onTaskCompleted(response, code);
                        Utility.removeSimpleProgressDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utility.removeSimpleProgressDialog();
                         Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                return map;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
