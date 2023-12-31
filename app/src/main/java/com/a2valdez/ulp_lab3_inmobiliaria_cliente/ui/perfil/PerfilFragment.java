package com.a2valdez.ulp_lab3_inmobiliaria_cliente.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a2valdez.ulp_lab3_inmobiliaria_cliente.R;
import com.a2valdez.ulp_lab3_inmobiliaria_cliente.databinding.FragmentPerfilBinding;
import com.a2valdez.ulp_lab3_inmobiliaria_cliente.modelo.Propietario;
import com.google.android.material.navigation.NavigationView;

public class PerfilFragment extends Fragment {
    private FragmentPerfilBinding binding;
    private PerfilViewModel mv;

    Propietario propietarioActual = null;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        mv = new ViewModelProvider(this).get(PerfilViewModel.class);
        View root = binding.getRoot();
        mv.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                propietarioActual = propietario;
                binding.etPerfilId.setText(String.valueOf(propietario.getId()));
                binding.etPerfilDni.setText(String.valueOf(propietario.getDni()));
                binding.etPerfilApellido.setText(propietario.getApellido());
                binding.etPerfilNombre.setText(propietario.getNombre());
                binding.etPerfilEmail.setText(propietario.getEmail());
                binding.etPerfilPassword.setText(propietario.getContraseña());
                binding.etPerfilTelefono.setText(propietario.getTelefono());
                binding.ivPerfilAvatar.setImageResource(propietario.getAvatar());
            }
        });
        mv.getMEsEditable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean esEditable) {
                binding.etPerfilDni.setEnabled(esEditable);
                binding.etPerfilApellido.setEnabled(esEditable);
                binding.etPerfilNombre.setEnabled(esEditable);
                binding.etPerfilEmail.setEnabled(esEditable);
                binding.etPerfilPassword.setEnabled(esEditable);
                binding.etPerfilTelefono.setEnabled(esEditable);

                binding.btPerfilEditar.setVisibility(esEditable ? View.GONE : View.VISIBLE);
                binding.btPerfilGuardar.setVisibility(esEditable ? View.VISIBLE : View.GONE);
            }
        });
        binding.btPerfilGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crear Propietario
                Propietario p = new Propietario(Integer.parseInt(binding.etPerfilId.getText().toString()),
                        Long.parseLong(binding.etPerfilDni.getText().toString()),
                        binding.etPerfilNombre.getText().toString(),
                        binding.etPerfilApellido.getText().toString(),
                        binding.etPerfilEmail.getText().toString(),
                        binding.etPerfilPassword.getText().toString(),
                        binding.etPerfilTelefono.getText().toString(),
                        propietarioActual.getAvatar());
                mv.GuardarPropietario(p);
            }
        });
        binding.btPerfilEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.CambiarEstadoEdicion();
            }
        });
        mv.LeerUsuario();
        return root;
    }
}