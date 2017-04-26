package com.isep.android.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WeatherConditionsHandler {

    public WeatherConditionsHandler(){}

    public WeatherConditions getWeatherConditions(URL url){
        StringBuilder data = new StringBuilder();
        try	{
            BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                data.append(inputLine);
            }
            in.close();
            return ProcessResponse(data.toString());
        } catch (Exception ee){
            return null;
        }
    }
    private WeatherConditions ProcessResponse(String resp) throws IllegalStateException, IOException, JSONException, NoSuchAlgorithmException {
        WeatherConditions conditions = new WeatherConditions();

        JSONObject mResponseObject = new JSONObject(resp);
        JSONObject responObject	= mResponseObject.getJSONObject("data");
        JSONArray array;

        array = responObject.getJSONArray("current_condition");
        for(int i = 0; i<array.length(); i++) {
            conditions.setTemperature(array.getJSONObject(i).getString("temp_C"));
        }

        array = responObject.getJSONArray("weatherDesc");
        for(int i = 0; i<array.length(); i++) {
            conditions.setHumidity(array.getJSONObject(i).getString("windspeedKmph"));
            conditions.setHumidity(array.getJSONObject(i).getString("humidity"));
        }
        return conditions;
    }
}
