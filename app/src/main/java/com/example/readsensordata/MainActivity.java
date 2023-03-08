package com.example.readsensordata;

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

import java.util.List;
//supaya dapat callback harus implement sebuah listener dari sensor
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
//    2 variabel untuk menampilkan sensor
    private Sensor mSensorLight;
    private Sensor mSensorProximity;
//    tugas
    private Sensor mSensorHumidity;
    private Sensor mSensorPressure;
    private Sensor mSensorTemperature;

//    mengambil TextView dari xml
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
//    tugas
    private TextView mTextSensorHumidity;
    private TextView mTextSensorPressure;
    private TextView mTextSensorTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

////        List untuk menyimpan sensor yang ada, memakai semua type sensor jadi TYPE_ALL
//        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//        StringBuilder sensorText = new StringBuilder();
//        for (Sensor currentSensor : sensorList){
//            sensorText.append(currentSensor.getName())
//                    .append(System.getProperty("line.separator"));
//        }
////        Tampilkan
//        TextView sensorTextView = findViewById(R.id.sensor_list);
//        sensorTextView.setText(sensorText);

//        ambil textviewnya
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
//        tugas
        mTextSensorHumidity = findViewById(R.id.label_humidity);
        mTextSensorPressure = findViewById(R.id.label_pressure);
        mTextSensorTemperature = findViewById(R.id.label_temperature);


        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//        tugas
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

//        kalau sensor tidak ada tampilkan no sensor
        if (mSensorLight == null){
            mTextSensorLight.setText("sensor_error");
        }
        if (mSensorProximity == null){
            mTextSensorProximity.setText("sensor_error");
        }
//        tugas
        if (mSensorHumidity == null){
            mTextSensorHumidity.setText("sensor_error");
        }
        if (mSensorPressure == null){
            mTextSensorPressure.setText("sensor_error");
        }
        if (mSensorTemperature == null){
            mTextSensorTemperature.setText("sensor_error");
        }
    }

//    mendaftarkan sensor ke activity
    @Override
    protected void onStart(){
        super.onStart();
        if (mSensorProximity != null){
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null){
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
//        tugas
        if (mSensorTemperature != null){
            mSensorManager.registerListener(this, mSensorTemperature,
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

//    unregister supaya event berakhir
    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

//    menerima update data dari sensor
//    untuk mendeteksi perubahan sensor di bagian mana melalui sensor event
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];

        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(String.format("Light sensor : %1$.2f", currentValue));
//                untuk mengubah background
                changeBackgroundColor(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(String.format("Light sensor : %1$.2f", currentValue));
                break;

//                tugas
            case Sensor.TYPE_PRESSURE:
                mTextSensorPressure.setText(String.format("Pressure sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(String.format("Humidity sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorTemperature.setText(String.format("Ambient sensor : %1$.2f", currentValue));
                break;

            default:
                //
        }
    }

//    method untuk menentukan warna background berdasar luxnya
    private void changeBackgroundColor(float currentValue) {
        ConstraintLayout layout = findViewById(R.id.layout_constraint);
        if(currentValue <= 40000 && currentValue >= 20000) layout.setBackgroundColor(Color.RED);
        else if(currentValue < 20000 && currentValue >= 10) layout.setBackgroundColor(Color.BLUE);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}