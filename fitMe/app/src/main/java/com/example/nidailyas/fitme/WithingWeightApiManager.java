package com.example.nidailyas.fitme;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


                List<Measuregrp> measuregrpsToday = new ArrayList<>();
                for (int i = 0; i < body.getMeasuregrps().size(); i++) {
                    if (new Date().getDay() == formateIntToDate(body.getMeasuregrps().get(i).getDate()).getDay()) {
                        measuregrpsToday.add(body.getMeasuregrps().get(i));
                    }
                }

                List<Measuregrp> measuregrpsCurHour = new ArrayList<>();
                for (int i = 0; i < measuregrpsToday.size(); i++) {
                    if (new Date().getHours() ==
                            formateIntToDate(body.getMeasuregrps().get(i).getDate()).getHours()) {

                        measuregrpsCurHour.add(body.getMeasuregrps().get(i));
                    }
                }

                List<Measuregrp> measuregrpsCur2Min = new ArrayList<>();
                for (int i = 0; i < measuregrpsCurHour.size(); i++) {
                    if (new Date().getMinutes() ==
                            formateIntToDate(body.getMeasuregrps().get(i).getDate()).getMinutes() ||
                            new Date().getMinutes() - 1 ==
                                    formateIntToDate(body.getMeasuregrps().get(i).getDate()).getMinutes() ||
                            new Date().getMinutes() - 2 ==
                                    formateIntToDate(body.getMeasuregrps().get(i).getDate()).getMinutes()
                            ) {

                        measuregrpsCur2Min.add(body.getMeasuregrps().get(i));
                    }
                }
                for (int i = 0; i < measuregrpsCur2Min.size(); i++) {
                    for (Measure measure : measuregrpsCur2Min.get(i).getMeasures()) {
                        if (measure.getType() == 1) {
                            myCallback.onCallback(formatResultValue(measure.getValue(), measure.getUnit()));
                        }
                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
