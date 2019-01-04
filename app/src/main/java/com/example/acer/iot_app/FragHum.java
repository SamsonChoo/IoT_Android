package com.example.acer.iot_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.LinearGauge;
import com.anychart.enums.Anchor;
import com.anychart.enums.Layout;
import com.anychart.enums.MarkerType;
import com.anychart.enums.Orientation;
import com.anychart.enums.Position;
import com.anychart.scales.OrdinalColor;

import org.json.JSONException;
import org.json.JSONObject;

public class FragHum extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.tab_fragment_2, container, false);

        AnyChartView anyChartView = rootview.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(rootview.findViewById(R.id.progressBar));

        LinearGauge linearGauge = AnyChart.linear();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(rootview.getContext());
        String url ="http://192.168.1.102:1880/mn_notification";

// Request a string response from the provided URL.
        final Double[] temp = new Double[1];
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            temp[0] =Double.valueOf((String) response.get("humidity"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

        linearGauge.data(new SingleValueDataSet(new Double[] { 80d }));

        linearGauge.layout(Layout.HORIZONTAL);

        linearGauge.label(0)
                .position(Position.LEFT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetY("-50px")
                .offsetX("50px")
                .fontColor("black")
                .fontSize(17);
        linearGauge.label(0).text("Humidity");

        linearGauge.label(1)
                .position(Position.LEFT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetY("40px")
                .offsetX("50px")
                .fontColor("#777777")
                .fontSize(17);
        linearGauge.label(1).text("Too Dry");

        linearGauge.label(2)
                .position(Position.RIGHT_CENTER)
                .anchor(Anchor.RIGHT_CENTER)
                .offsetY("40px")
                .offsetX("50px")
                .fontColor("#777777")
                .fontSize(17);
        linearGauge.label(2).text("Too Humid");

        OrdinalColor scaleBarColorScale = OrdinalColor.instantiate();
        scaleBarColorScale.ranges(new String[]{
                "{ from: 0, to: 20, color: ['red 0.5'] }",
                "{ from: 20, to: 50, color: ['yellow 0.5'] }",
                "{ from: 50, to: 80, color: ['green 0.5'] }",
                "{ from: 80, to: 90, color: ['yellow 0.5'] }",
                "{ from: 90, to: 100, color: ['red 0.5'] }"
        });

        linearGauge.scaleBar(0)
                .width("5%")
                .colorScale(scaleBarColorScale);

        linearGauge.marker(0)
                .type(MarkerType.TRIANGLE_DOWN)
                .color("red")
                .offset("-3.5%")
                .zIndex(10);

        linearGauge.scale()
                .minimum(0)
                .maximum(100);
//        linearGauge.scale().ticks

        linearGauge.axis(0)
                .minorTicks(false)
                .width("1%");
        linearGauge.axis(0)
                .offset("-1.5%")
                .orientation(Orientation.TOP)
                .labels("top");

        linearGauge.padding(0, 30, 0, 30);

        anyChartView.setChart(linearGauge);

        return rootview;
    }

}
