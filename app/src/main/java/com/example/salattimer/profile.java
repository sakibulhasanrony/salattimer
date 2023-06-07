package com.example.salattimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class profile extends AppCompatActivity {
    TextView username1,email1,city1,password1;
    DBHelper DB2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username1=findViewById(R.id.username1);
        email1=findViewById(R.id.email1);
        city1=findViewById(R.id.city1);
        password1=findViewById(R.id.password1);


        Intent intents = getIntent();
        String str10 = intents.getStringExtra("names");
        username1.setText(str10);
        DB2 = new DBHelper(this);
        SQLiteDatabase sqLiteDatabase=DB2.getWritableDatabase();
        Cursor cursor=DB2.displayalldata();
        String name=username1.getText().toString();
        while (cursor.moveToNext()){
            String str1=cursor.getString(0);
            if(str1.equals(name)){
                password1.setText(cursor.getString(1));
                email1.setText(cursor.getString(2));
                city1.setText(cursor.getString(3));
            }
        }

    }
    public void delet_data(View v){
        String name=username1.getText().toString();
        DB2.deletdata(name);
    }
    public void update_data(View v){
        String name=username1.getText().toString();
        String password=password1.getText().toString();
        String email=email1.getText().toString();
        String city=city1.getText().toString();
        DB2.updatedata(name,password,email,city);
    }

}