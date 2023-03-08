package com.example.sensordata;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.invoke.ConstantCallSite;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener { //sensor event listener akan mengupdate perubahan yang terjadi
    private SensorManager mSensorManager;
    //variabel untuk sensor light dan proximity
    private Sensor mSensorLight;
    private Sensor mSensorProximity;
    private Sensor mSensorHumidity;
    private Sensor mSensorPressure;
    private Sensor mSensorAmbient;


    private TextView mTextSensorLight; // inisialisasi textview dari layout
    private TextView mTextSensorProximity;
    private TextView mTextSensorAmbient;
    private TextView mTextSensorHumidity;
    private TextView mTextSensorPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL); //manggil sensor manager (tipe all karena mau dipake semua)
//        StringBuilder sensorText = new StringBuilder();
//        for (Sensor currentSensor : sensorList){ //iterasi sensor list
//            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator")); //menampilkan sensor dengan separator setiap line nya
//        }
////        TextView sensorTextView = findViewById(R.id.sensor_list);
//        sensorTextView.setText(sensorText);


        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mTextSensorAmbient = findViewById(R.id.label_ambient);
        mTextSensorHumidity = findViewById(R.id.label_humidity);
        mTextSensorPressure = findViewById(R.id.label_pressure);

        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //mengambil sensor dengan type light
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorAmbient = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        String sensor_error = "no sensor"; //String yang akan ditampilkan jika sensor tidak ada

        //percabangan
        if (mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null){
            mTextSensorProximity.setText(sensor_error);
        }

        if (mTextSensorAmbient == null){
            mTextSensorAmbient.setText(sensor_error);
        }

        if (mTextSensorHumidity == null){
            mTextSensorHumidity.setText(sensor_error);
        }

        if (mTextSensorPressure == null){
            mTextSensorPressure.setText(sensor_error);
        }

    }

    protected void onStart(){
        super.onStart();
        if (mSensorProximity != null){
            mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorLight != null){
            mSensorManager.registerListener(this, mSensorLight,SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorAmbient != null){
            mSensorManager.registerListener(this, mSensorLight,SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorAmbient != null){
            mSensorManager.registerListener(this, mSensorAmbient,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorHumidity != null){
            mSensorManager.registerListener(this, mSensorHumidity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorPressure != null){
            mSensorManager.registerListener(this, mSensorPressure,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    protected void onStop(){
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    //untuk menerima perubahan data
    public void onSensorChanged(SensorEvent sensorEvent) {
        //parameter sensor event untuk mendeteksi perubahan ada di sebelah mana
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(
                        String.format("light sensor : %1$.2f", currentValue));
                changeBackgroundColor(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(String.format("Proximity sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorAmbient.setText(String.format("Proximity sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PRESSURE:
                mTextSensorPressure.setText(String.format("Pressure sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(String.format("Humidity sensor : %1$.2f", currentValue));
                break;

            default:

        }

    }

    private void changeBackgroundColor (float currentValue){
        ConstraintLayout layout = findViewById(R.id.constraint_layout);
        if (currentValue <= 40000 && currentValue >=20000) layout.setBackgroundColor(Color.RED);
        else if (currentValue < 20000 && currentValue >= 10) layout.setBackgroundColor(Color.YELLOW);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}