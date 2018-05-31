package com.example.nidailyas.fitme;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WithingWeightApiManager {
    private static final String withingsURL = "http://api.health.nokia.com/measure?action=getmeas&" +
            "oauth_consumer_key=e0eb6d9611b56d21809792948c6f11956d62f4d6be1608cdebd397125183b4&" +
            "oauth_nonce=61add8bfe3b21a3e67743dcb30fa454b&" +
            "oauth_signature=8dq6FjapoISLge3bciBlLWtPPog%3D&" +
            "oauth_signature_method=HMAC-SHA1&oauth_timestamp=1527681037&" +
            "oauth_token=d877b579de419c7ccbc60b7105808be47e32b24ebbdc8ed809e16e7b951654&" +
            "oauth_version=1.0&userid=15707726";

    Context context;

    public WithingWeightApiManager(Context context) {
        this.context = context;
    }


    public void setConnection(final MyCallback<Double> myCallback) {
        StringRequest withingsRequest = new StringRequest(withingsURL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("CODE", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                WithingsData withingsData = gson.fromJson(response, WithingsData.class);
                Body body = withingsData.getBody();

                Measuregrp measuregrp1 = body.getMeasuregrps().get(0);

                // formatedate
                Date date1 = formateIntToDate(measuregrp1.getDate());


                //toDo: mag weg: 85-89
                // print date
                String dateAsText = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                        .format(date1);
                Log.w("measuregrp1: ", dateAsText);

                // result

                com.example.nidailyas.fitme.Measure  measure1 = measuregrp1.getMeasures().get(0);
                double result = formatResultValue(measure1.getValue(), measure1.getUnit());
                myCallback.onCallback(result);


                //toDo: mag weg: 96 - 98
                // print result
                Log.w("measuregrp1: ", Double.toString(result));


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("withings Error: ", "Something went wrong");
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(withingsRequest);

    }

    private Date formateIntToDate(Integer intDate) {
        Date date = new java.util.Date((long) intDate * 1000L);
        return date;
    }

    private double formatResultValue(Integer value, Integer unit) {
        double result = value * (Math.pow(10, unit));
        return result;
    }
}
