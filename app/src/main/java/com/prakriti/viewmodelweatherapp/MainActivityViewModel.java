package com.prakriti.viewmodelweatherapp;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivityViewModel extends AndroidViewModel {

    private String apiKey = "66f3cfea2d52f0f35c01a1b9b39d2ff5";

    private Context context;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void getWeatherInfo(String location) {
        // create Volley request
        RequestQueue myQueue = Volley.newRequestQueue(context);

        JsonObjectRequest myjson = new JsonObjectRequest(Request.Method.GET, "https://api.openweathermap.org/data/2.5/weather?q=" + location
                + "&units=metric&appid=" + apiKey, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main = response.getJSONObject("main");
                    JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                    JSONObject sys = response.getJSONObject("sys");

                    cityName = response.getString("name");
                    Long time = response.getLong("dt");
                    updatedAt = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(time * 1000));

                    temperature = main.getString("temp") + " °C";
                    humidity = main.getString("humidity");
                    temp_min = main.getString("temp_min");
                    temp_max = main.getString("temp_max");

                    forecast = weather.getString("description");

                    countryName = sys.getString("country");
                    Long rise = sys.getLong("sunrise");
                    sunrise = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(rise * 1000));
                    Long set = sys.getLong("sunset");
                    sunset = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(set * 1000));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Error occured\nPlease try again", Toast.LENGTH_SHORT).show();
            }
        });
        myQueue.add(myjson);
    }
}