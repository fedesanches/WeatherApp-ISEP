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

import java.net.URL;

import static com.isep.android.weatherapp.R.layout.activity_weather_info;

public class WeatherInfoActivity extends AppCompatActivity {
    static ProgressDialog pd_ring;
    private WeatherConditions conditions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_weather_info);

        Intent i = getIntent();
        final Bundle extras = getIntent().getExtras();
        LatLng coordinate = i.getParcelableExtra("mapCoordinate");
        String lat = String.valueOf(coordinate.latitude);
        String lng = String.valueOf(coordinate.longitude);


        GetWWDataTask task = new GetWWDataTask();
        task.execute("http://api.worldweatheronline.com/free/v1/weather.ashx?q="+lat+","+lng+"&format=xml&date=today&num_of_days=1&key=9dahjexnkyedxgzrgk4nxrhb");

        TextView latView = (TextView) findViewById(R.id.lat_text_view);
        latView.setText("Latitude: "+lat);

        TextView lngView = (TextView) findViewById(R.id.lng_text_view);
        lngView.setText("Longitude: "+lng);

        /*DBAdapter adapter = new DBAdapter(getApplicationContext());
        adapter.insertWeatherCondition(lat, lng, null, null, null);
        Log.i("holaaaa",adapter.getAllWeatherConditions().toString());*/
    }


    private void UpdateDisplay() {
        TextView temperatureView = (TextView) findViewById(R.id.temperature_text_view);
        TextView humidityView = (TextView) findViewById(R.id.humidity_text_view);
        TextView windView = (TextView) findViewById(R.id.wind_text_view);

        temperatureView.setText(conditions.getTemperature());
        humidityView.setText(conditions.getHumidity());
        windView.setText(conditions.getWind());
    }

    private class GetWWDataTask extends AsyncTask<String, Void, WeatherConditions > {

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
        protected WeatherConditions doInBackground(String... params) {
            try {
                WeatherConditionsHandler handler = new WeatherConditionsHandler();
                URL url = new URL(params[0]);
                return handler.getWeatherConditions(url);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(WeatherConditions c) {
            conditions = c;
            pd_ring.dismiss();
            UpdateDisplay();
        }
    }
}
