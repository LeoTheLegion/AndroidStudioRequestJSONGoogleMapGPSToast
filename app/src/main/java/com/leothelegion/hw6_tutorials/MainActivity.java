package com.leothelegion.hw6_tutorials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //doSimpleRequest();
        //doJsonParse();
        //doGoogleMap();
        doToast();

    }
    void doToast(){
        Toast.makeText(getApplicationContext(),"Hello World",Toast.LENGTH_LONG).show();
    }
    void doGoogleMap(){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
    void doJsonParse(){
        try {
            JSONObject reader = JSONAssetReader.getJsonObjectFromFile(
                    "world-cities_json.json",this);
            JSONArray worldcities = reader.getJSONArray("value").getJSONArray(0);
            JSONObject city = worldcities.getJSONObject(0);

            createAlert(city.getString("name") +"\n" +
                    city.getString("subcountry") +"\n" +
                    city.getString("country") +"\n" +
                    city.getInt("geonameid") +"\n");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void doSimpleRequest(){
        SimpleRequest request = new SimpleRequest(this);

        String tag = "Pancake";

        request.sendRequest(SimpleRequest.Method.GET,"https://www.google.com",tag,
                new SimpleRequest.Response(){

                    @Override
                    public void onSuccess(String response) {
                        createAlert("Response is: "+ response.substring(0,500));
                    }

                    @Override
                    public void onError(String error) {
                        createAlert("That didn't work!");
                    }
                }
        );

        request.cancelRequest(tag);
    }


    void createAlert(String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setPositiveButton("Ok",null);
        alert.show();
    }
}
