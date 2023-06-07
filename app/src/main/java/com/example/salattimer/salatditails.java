package com.example.salattimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class salatditails extends AppCompatActivity {
    private LinearLayout imageView;
    String Fajr1,Dhuhr1,Asar1,Maghrib1,Isha1,Currenttime;
    Button profile,compass;
    TextView text1,text2,text3,text4,text5;
    TextView fo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salatditails);
        imageView=findViewById(R.id.image);
        profile=findViewById(R.id.profile);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        text3=findViewById(R.id.text3);
        text4=findViewById(R.id.text4);
        text5=findViewById(R.id.text5);
        compass=findViewById(R.id.compass);

        Intent intents = getIntent();
        String str = intents.getStringExtra("name");

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(salatditails.this, profile.class);
                intent.putExtra("names",str);
                startActivity(intent);
            }
        });
        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentss=new Intent(salatditails.this, compasss.class);
                intentss.putExtra("names",str);
                startActivity(intentss);
            }
        });
        RequestQueue requestqueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://muslimsalat.com/dhaka.json", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    DateFormat df12 = new SimpleDateFormat("hh:mm aa");//using 12 hour formate
                    DateFormat df24 = new SimpleDateFormat("HH:mm");//using 24 hour formate
                    Currenttime = df24.format(Calendar.getInstance().getTime());//geting curent time here
                    JSONArray jsonArray = response.getJSONArray("items");//intering to the items
                    JSONObject items = jsonArray.getJSONObject(0);
                    String Fajr = items.getString("fajr");//to get fajr
                    Date date=df12.parse(Fajr);
                    Fajr1=df24.format(date);

                    String Dhuhr = items.getString("dhuhr");//to get dhuhr
                    date=df12.parse(Dhuhr);
                    Dhuhr1=df24.format(date);

                    String Asar = items.getString("asr");//to get asar
                    date=df12.parse(Asar);
                    Asar1=df24.format(date);

                    String Maghrib = items.getString("maghrib");//to get magrib
                    date=df12.parse(Maghrib);
                    Maghrib1=df24.format(date);

                    String Isha = items.getString("isha");//to get isha
                    date=df12.parse(Isha);
                    Isha1=df24.format(date);

                    salatchecker(Fajr1,Dhuhr1,Asar1,Maghrib1,Isha1,Currenttime);//coling a function to find out a salat details


                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myapp", "something is rong");
            }
        });
        requestqueue.add(jsonObjectRequest);
    }
    public void salatchecker(String fajr,String dhuhr,String asar,String maghrib,String isha,String currenttime) {


        String pattern="HH:mm";
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        text1.setText(fajr);
        text2.setText(dhuhr);
        text3.setText(asar);
        text4.setText(maghrib);
        text5.setText(isha);

        try{
            Date firsttime=sdf.parse(fajr);
            Date secondtime=sdf.parse(dhuhr);
            Date thardtime=sdf.parse(asar);
            Date forthtime=sdf.parse(maghrib);
            Date fivthtime=sdf.parse(isha);
            Date maintime=sdf.parse(currenttime);

            if(firsttime.before(maintime))
            {
                imageView.setBackground(getResources().getDrawable(R.drawable.fajar));
            }
            if(secondtime.before(maintime))
            {
                imageView.setBackground(getResources().getDrawable(R.drawable.dhuhr));
            }
            if(thardtime.before(maintime))
            {
                imageView.setBackground(getResources().getDrawable(R.drawable.asar));
            }
            if(forthtime.before(maintime))
            {
                imageView.setBackground(getResources().getDrawable(R.drawable.maghrib));
            }
            if(fivthtime.before(maintime))
            {
                imageView.setBackground(getResources().getDrawable(R.drawable.isha));
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}