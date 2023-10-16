package com.a2valdez.ulp_lab3_inmobiliaria_cliente.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.a2valdez.ulp_lab3_inmobiliaria_cliente.LoginActivity;
import com.a2valdez.ulp_lab3_inmobiliaria_cliente.MainActivity;
import com.a2valdez.ulp_lab3_inmobiliaria_cliente.modelo.Propietario;
import com.a2valdez.ulp_lab3_inmobiliaria_cliente.request.ApiClient;
import com.google.android.gms.common.api.Api;

public class PerfilViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> esEditable;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Propietario> getMPropietario() {
        if(mPropietario == null){
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    public LiveData<Boolean> getMEsEditable() {
        if (esEditable == null) {
            esEditable = new MutableLiveData<>();
            esEditable.setValue(false);
        }
        return esEditable;
    }

    public void LeerUsuario(){
        Propietario p = ApiClient.getApi().obtenerUsuarioActual();
        if( p != null) {
            mPropietario.setValue(p);
        }
    }

    public void CambiarEstadoEdicion(){
        esEditable.setValue(!esEditable.getValue());
    }

    public void GuardarPropietario(Propietario p){
        ApiClient.getApi().actualizarPerfil(p);
        //Propietario propietario = ApiClient.getApi().login(p.getEmail(), p.getContraseña());
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}