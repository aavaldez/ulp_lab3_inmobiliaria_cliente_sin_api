package com.a2valdez.ulp_lab3_inmobiliaria_cliente;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.a2valdez.ulp_lab3_inmobiliaria_cliente.modelo.Propietario;
import com.a2valdez.ulp_lab3_inmobiliaria_cliente.request.ApiClient;

public class LoginActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> mMensaje;
    private LeeSensor leeSensor;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        leeSensor = new LeeSensor(context);
        registerListener();
    }

    public LiveData<String> getmMensaje() {
        if(mMensaje == null){
            mMensaje = new MutableLiveData<>();
        }
        return mMensaje;
    }

    public void Login(String email, String password){
        String mensaje;
        Propietario propietario = ApiClient.getApi().login(email, password);
        if( propietario != null ){
            //Cargar activity
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            mensaje = "Usuario o contraseña incorrecto";
            mMensaje.setValue(mensaje);
        }
    }

    //    Funciones del sensor de movimiento
    public void registerListener() {
        if (leeSensor == null) return;
        leeSensor.registerListener();
    }

    public void unregisterListener() {
        if (leeSensor == null) return;
        leeSensor.unregisterListener();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        unregisterListener();
    }

    //    Clase interna para el sensor de movimiento
    private class LeeSensor implements SensorEventListener {

        private SensorManager sensorManager;
        private Sensor accelerometer;
        private float acceleration, currentAcceleration, lastAcceleration;

        private static final float SHAKE_THRESHOLD = 10.0f;

        public LeeSensor(Context context) {
            this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            acceleration = 0.0f;
            currentAcceleration = SensorManager.GRAVITY_EARTH;
            lastAcceleration = SensorManager.GRAVITY_EARTH;
        }

        public void registerListener() {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        public void unregisterListener() {
            sensorManager.unregisterListener(this);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            lastAcceleration = currentAcceleration;
            currentAcceleration = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = currentAcceleration - lastAcceleration;
            acceleration = acceleration * 0.9f + delta;

            if (acceleration > SHAKE_THRESHOLD) {
                LoginActivityViewModel.this.llamar();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    }

    private void llamar(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:2664"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try{
            context.startActivity(intent);
        } catch(Exception e){
            mMensaje.setValue("ERROR: NO TIENE PERMISOS");
        }
    }
}
