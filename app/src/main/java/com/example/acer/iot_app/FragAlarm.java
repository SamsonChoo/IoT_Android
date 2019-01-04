package com.example.acer.iot_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FragAlarm extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.tab_fragment_1, container, false);
        final TextView mTextView = (TextView) rootview.findViewById(R.id.textView);

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(rootview.getContext());
        String url ="http://192.168.1.102:1880/mn_notification";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("alarm").equals("off"))
                                mTextView.setText("Alarm is off"+ response.toString());
                            else
                                mTextView.setText("Alarm is ON!!!!!"+ response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("Sorry, server connection error =3");
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
        return rootview;
    }
}
