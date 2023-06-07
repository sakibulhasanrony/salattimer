package com.example.salattimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class compasss extends AppCompatActivity implements SensorEventListener {
    private Button button,buttoncampass;
    private ImageView imageView;
    private float[] mGravity=new float[3];
    private float[] mGeomatric=new float[3];
    private float azimuth =0f;
    private float currentAzumtion=0f;
    private SensorManager mensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compasss);
        imageView=findViewById(R.id.compas);
        buttoncampass=findViewById(R.id.buttoncampass);
        mensorManager =(SensorManager)getSystemService(SENSOR_SERVICE);
        Intent intents = getIntent();
        String str = intents.getStringExtra("names");

        buttoncampass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(compasss.this, salatditails.class);
                intent.putExtra("name",str);
                startActivity(intent);

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        mensorManager.registerListener(this,mensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),mensorManager.SENSOR_DELAY_GAME);
        mensorManager.registerListener(this,mensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),mensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha =0.97f;
        synchronized(this){
            if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                mGravity[0]=alpha*mGravity[0]+(1-alpha)*event.values[0];
                mGravity[1]=alpha*mGravity[1]+(1-alpha)*event.values[1];
                mGravity[2]=alpha*mGravity[2]+(1-alpha)*event.values[2];
            }
            if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
                mGeomatric[0]=alpha*mGeomatric[0]+(1-alpha)*event.values[0];
                mGeomatric[1]=alpha*mGeomatric[1]+(1-alpha)*event.values[1];
                mGeomatric[2]=alpha*mGeomatric[2]+(1-alpha)*event.values[2];

            }
            float R[]=new float[9];
            float I[]=new float[9];
            boolean success=SensorManager.getRotationMatrix(R,I,mGravity,mGeomatric);
            if(success){
                float orientation[] =new float[3];
                SensorManager.getOrientation(R,orientation);
                azimuth=(float)Math.toDegrees(orientation[0]);
                azimuth=(azimuth+360)%360;
                Animation anim=new RotateAnimation(-currentAzumtion,-azimuth,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                currentAzumtion=azimuth;
                anim.setDuration(500);
                anim.setDuration(0);
                anim.setFillAfter(true);
                imageView.startAnimation(anim);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}