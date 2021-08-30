package com.prakriti.viewmodelweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String inputCity;
    private TextView txtForecast, txtTemp, txtCity, txtTime, txtCountry, txtHumidity, txtMinTemp, txtMaxTemp, txtSunrise, txtSunset;
    private EditText edtLocation;
    private Button btnClear, btnGetWeather;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        edtLocation = findViewById(R.id.edtLocation);

        txtForecast = findViewById(R.id.txtForecast);
        txtTemp = findViewById(R.id.txtTemp);
        txtTime = findViewById(R.id.txtTime);
        txtCity = findViewById(R.id.txtCity);
        txtCountry = findViewById(R.id.txtCountry);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtMinTemp = findViewById(R.id.txtMinTemp);
        txtMaxTemp = findViewById(R.id.txtMaxTemp);
        txtSunrise = findViewById(R.id.txtSunrise);
        txtSunset = findViewById(R.id.txtSunset);

        btnGetWeather = findViewById(R.id.btnGetWeather);
        btnClear = findViewById(R.id.btnClear);

        btnGetWeather.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        displayAllValues();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnGetWeather:
                if(edtLocation.getText().toString().trim().equals("")) {
                    edtLocation.setError("This field cannot be empty");
                    return;
                }
                inputCity = edtLocation.getText().toString().trim();
                viewModel.getWeatherInfo(inputCity);
                displayAllValues();
                break;

            case R.id.btnClear:
                edtLocation.setText("");
                txtForecast.setText("");
                txtTemp.setText("");
                txtTime.setText("");
                txtCity.setText("");
                txtCountry.setText("");
                txtHumidity.setText("");
                txtMinTemp.setText("");
                txtMaxTemp.setText("");
                txtSunrise.setText("");
                txtSunset.setText("");
                break;
        }
    }

    private void displayAllValues() {
        txtCity.setText(viewModel.cityName);
        txtCountry.setText(viewModel.countryName);
        txtTime.setText(viewModel.updatedAt);
        txtTemp.setText(viewModel.temperature);
        txtForecast.setText(viewModel.forecast);
        txtHumidity.setText(viewModel.humidity);
        txtMinTemp.setText(viewModel.temp_min);
        txtMaxTemp.setText(viewModel.temp_max);
        txtSunrise.setText(viewModel.sunrise);
        txtSunset.setText(viewModel.sunset);
    }

}