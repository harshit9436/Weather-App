package com.example.json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView long_textview, lati_textview , weather_textview , textView1 , textView2 , textView3 ,
            description_textview , textview4 , textView5 , textView6 , actual_temp_textview, feelsLike_temp_textview;


    public class DownloadTask extends AsyncTask<String , Void ,  String>{

        @Override
        protected String doInBackground(String... strings) {

            try {
                String Result = "";
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                InputStreamReader reader  = new InputStreamReader(stream);
                int data  = reader.read();
                while(data!=-1){
                    char current  = (char) data;
                    Result += current;

                    data = reader.read();
                }
                return  Result;

            } catch (Exception e) {
                e.printStackTrace();


                return "Failed";
            }






        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        lati_textview = findViewById(R.id.latt_textview);
        long_textview = findViewById(R.id.long_textview);
        weather_textview = findViewById(R.id.weather_textview);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textview4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView9);
        textView6 = findViewById(R.id.textView11);
        actual_temp_textview = findViewById(R.id.actual_temp);
        feelsLike_temp_textview = findViewById(R.id.feelsLike_temp_textview);
        description_textview = findViewById(R.id.description_textview);

        lati_textview.setVisibility(View.INVISIBLE);
        long_textview.setVisibility(View.INVISIBLE);
        weather_textview.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        textView1.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.INVISIBLE);
        textview4.setVisibility(View.INVISIBLE);
        textView5.setVisibility(View.INVISIBLE);
        textView6.setVisibility(View.INVISIBLE);
        actual_temp_textview.setVisibility(View.INVISIBLE);
        feelsLike_temp_textview.setVisibility(View.INVISIBLE);
        description_textview.setVisibility(View.INVISIBLE);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String city = editText.getText().toString().trim().toLowerCase();

                DownloadTask task = new DownloadTask();
                String result = null;
                try{
                    result = task.execute("https://api.openweathermap.org/data/2.5/weather?q="+ city + "&appid=ba24c6018ddd72041749018d0c1b1ef8&units=metric").get();
                    Log.d("jsonTest", "onCreate: "+ result);
                    if(result.equals("Failed")){
                        Toast.makeText(MainActivity.this , "Couldn't find weather", Toast.LENGTH_SHORT).show();

                    }
                    JSONObject JSON_obj = new JSONObject(result);
                    String coordinates = JSON_obj.getString("coord");
                    JSONObject coordinate_obj = new JSONObject(coordinates);



                    String latitude = coordinate_obj.getString("lat");
                    //Log.d("jsonTest", "onClick: "+ latitude);
                    double lat = Double.parseDouble(latitude);
                    int lat_final = (int) lat;
                    Log.d("jsonTest", "onClick: "+ lat_final);

                    if(lat_final>=0){
                        lati_textview.setText(String.valueOf(latitude) + " N");
                    }else{
                        lati_textview.setText(String.valueOf(latitude)+ " S");
                    }                    lati_textview.setVisibility(View.VISIBLE);
                    textView1.setVisibility(View.VISIBLE);

                    String longitude = coordinate_obj.getString("lon");
                    //Log.d("jsonTest", "onClick: "+ longitude);
                    double lon = Double.parseDouble(longitude);
                    int lon_final = (int) lon;
                    Log.d("jsonTest", "onClick: "+ lon_final);

                    if(lon_final>=0){
                        long_textview.setText(String.valueOf(longitude) + " E");
                    }else{
                        long_textview.setText(String.valueOf(longitude)+ " W");
                    }


                   // long_textview.setText(String.valueOf(longitude));
                    textView2.setVisibility(View.VISIBLE);
                    long_textview.setVisibility(View.VISIBLE);


                    String weather_info = JSON_obj.getString("weather");
                    JSONArray arr = new JSONArray(weather_info);
                    for (int i = 0; i < arr.length() ; i++) {
                        JSONObject part_obj = arr.getJSONObject(i);

                        String weather = part_obj.getString("main").toString();
                        weather_textview.setText(weather);
                        weather_textview.setVisibility(View.VISIBLE);
                        textView3.setVisibility(View.VISIBLE);

                        String description = part_obj.getString("description").toString();
                        description_textview.setText(description);
                        description_textview.setVisibility(View.VISIBLE);
                        textview4.setVisibility(View.VISIBLE);
                    }

                    String temp = JSON_obj.getString("main");
                    JSONObject temp_obj = new JSONObject(temp);
                    String actual_temp = temp_obj.getString("temp");
                    String feelLike_temp = temp_obj.getString("feels_like");
                    actual_temp_textview.setText(actual_temp);
                    feelsLike_temp_textview.setText(feelLike_temp);
                    textView5.setVisibility(View.VISIBLE);
                    textView6.setVisibility(View.VISIBLE);
                    actual_temp_textview.setVisibility(View.VISIBLE);
                    feelsLike_temp_textview.setVisibility(View.VISIBLE);







                }catch (Exception e){
                    Log.d("jsonTest", "onCreate: "+ e.toString());
                    Toast.makeText(MainActivity.this , "Couldn't find weather", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }




}
