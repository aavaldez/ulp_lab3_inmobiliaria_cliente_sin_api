package com.a2valdez.ulp_lab3_inmobiliaria_cliente.ui.inquilinos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.a2valdez.ulp_lab3_inmobiliaria_cliente.R;
import com.a2valdez.ulp_lab3_inmobiliaria_cliente.modelo.Inmueble;
import com.a2valdez.ulp_lab3_inmobiliaria_cliente.modelo.Inquilino;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.Serializable;
import java.util.List;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolder> {
    private List<Inmueble> inmuebles;
    private Context contexto;
    private LayoutInflater li;
    public InquilinoAdapter(List<Inmueble> inmuebles, Context contexto, LayoutInflater li) {
        this.inmuebles = inmuebles;
        this.contexto = contexto;
        this.li = li;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.item_inquilino, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.direccion.setText(inmuebles.get(position).getDireccion());
        Glide.with(contexto).load(inmuebles.get(position).getImagen()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imagen);
    }
    @Override
    public int getItemCount() {
        return inmuebles.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView direccion;
        private ImageView imagen;
        private Button ver;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvItemInquilinoDireccion);
            imagen = itemView.findViewById(R.id.ivItemInquilino);
            ver = itemView.findViewById(R.id.btItemInquilinoVer);
            ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Inmueble i = inmuebles.get(getLayoutPosition());
                    bundle.putSerializable("inmueble", (Serializable)i);
                    Navigation.findNavController(view).navigate(R.id.inquilinoFragment, bundle);
                }
            });
        }
    }
}
