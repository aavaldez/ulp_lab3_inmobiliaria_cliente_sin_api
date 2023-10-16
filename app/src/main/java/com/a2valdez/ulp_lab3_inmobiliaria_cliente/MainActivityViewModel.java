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

public class MainActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Propietario> mPropietario;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public LiveData<Propietario> getMPropietario() {
        if(mPropietario == null){
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    public void LeerUsuario(){
        Propietario p = ApiClient.getApi().obtenerUsuarioActual();
        if( p != null) {
            mPropietario.setValue(p);
        }
    }
}
