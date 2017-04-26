package com.isep.android.weatherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;

import static com.isep.android.weatherapp.R.layout.activity_weather_info;

public class WeatherInfoActivity extends AppCompatActivity {
    static ProgressDialog pd_ring;
    private WeatherConditions conditions = new WeatherConditions();
    private String urlapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_weather_info);

        Intent i = getIntent();
        final Bundle extras = getIntent().getExtras();
        LatLng coordinate = i.getParcelableExtra("mapCoordinate");
        String lat = String.valueOf(coordinate.latitude).substring(0,6);
        String lng = String.valueOf(coordinate.longitude).substring(0,6);

        conditions.setLat(lat);
        conditions.setLng(lng);

        urlapi = "http://api.worldweatheronline.com/free/v1/weather.ashx?q="+lat+","+lng+"&format=json&date=today&num_of_days=1&key=9dahjexnkyedxgzrgk4nxrhb";
        new GetWWDataTask().execute(urlapi);

        TextView latView = (TextView) findViewById(R.id.lat_text_view);
        latView.setText("Latitude: "+lat);

        TextView lngView = (TextView) findViewById(R.id.lng_text_view);
        lngView.setText("Longitude: "+lng);
    }


    private void UpdateDisplay() {
        TextView temperatureView = (TextView) findViewById(R.id.temperature_text_view);
        temperatureView.setText(conditions.getTemperature()+" °C");

        TextView humidityView = (TextView) findViewById(R.id.humidity_text_view);
        humidityView.setText(conditions.getHumidity()+" %");

        TextView windView = (TextView) findViewById(R.id.wind_text_view);
        windView.setText(conditions.getWind()+" Km/h");
    }

   private class GetWWDataTask extends AsyncTask<String, String, String> {

       @Override
        protected void onPreExecute() {
            pd_ring = new ProgressDialog(WeatherInfoActivity.this);
            pd_ring.setMessage("Loading....");
            pd_ring.setTitle("Please Wait...");
            pd_ring.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd_ring.setCancelable(false);
            pd_ring.setIndeterminate(true);
            pd_ring.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();

                BufferedReader reader= new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null){
                    buffer.append(line+"\n");
                    Log.e("Response:",line);
                }
                return getWeatherConditions(buffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pd_ring.dismiss();
            UpdateDisplay();
        }
    }

    public String getWeatherConditions (String resp) throws JSONException {

        JSONObject mResponseObject = new JSONObject(resp);
        JSONObject responObject	= mResponseObject.getJSONObject("data");
        JSONArray array;

        array = responObject.getJSONArray("current_condition");
        for(int i = 0; i<array.length(); i++) {
            conditions.setTemperature(array.getJSONObject(i).getString("temp_C"));
            conditions.setWind(array.getJSONObject(i).getString("windspeedKmph"));
            conditions.setHumidity(array.getJSONObject(i).getString("humidity"));
        }

        DBAdapter adapter = new DBAdapter(getApplicationContext());
        adapter.insertWeatherCondition(conditions.getLat(), conditions.getLng(), conditions.getTemperature(), conditions.getWind(), conditions.getHumidity());
        Log.i("holaaaa",adapter.getAllWeatherConditions().toString());
        return null;
    }
}
