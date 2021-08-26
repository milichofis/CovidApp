package com.example.covidapp.services;

import com.example.covidapp.models.CovidRecord;
import com.example.covidapp.common.DateTimeHelper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CovidService {
    private static final String API_LINK = "https://api.covid19api.com/country/";

    public CovidRecord getData(String country){
        String json = null;
        HttpURLConnection connection = null;
        try
        {
            URL url = this.getCovidUrl(country);
            connection = (HttpURLConnection)url.openConnection();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while((line = r.readLine()) != null) {
                    sb.append(line);
                }

                json = sb.toString();
            }
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(connection != null) {
                connection.disconnect();
            }
        }

        List<CovidRecord> result = this.deserialize(json);
        if(result == null)
        {
            return null;
        }
        return result.get(0);
    }

    private List<CovidRecord> deserialize(String s){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
        Type listOfMyClassObject = new TypeToken<ArrayList<CovidRecord>>() {}.getType();
        return gson.fromJson(s, listOfMyClassObject);
    }

    private URL getCovidUrl(String country) throws MalformedURLException {
        String twoDaysAgo = DateTimeHelper.getTwoDaysAgo("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String yesterday = DateTimeHelper.getYesterday("yyyy-MM-dd'T'HH:mm:ss'Z'");
        StringBuilder url = new StringBuilder(API_LINK+ country +"?from="+twoDaysAgo+"&to="+yesterday);
        return new URL(url.toString());
    }
}
