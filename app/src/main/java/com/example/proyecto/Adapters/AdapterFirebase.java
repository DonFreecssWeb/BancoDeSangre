package com.example.proyecto.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.model.Usuario;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdapterFirebase extends FirestoreRecyclerAdapter<Usuario, AdapterFirebase.AdapterHolder> {



    public AdapterFirebase(FirestoreRecyclerOptions<Usuario> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(AdapterHolder adapterHolder, int i, Usuario usuario) {
        adapterHolder.nombre.setText(usuario.getNombre());
        adapterHolder.apellido.setText(usuario.getApellido());
        adapterHolder.correo.setText(usuario.getCorreo());
        adapterHolder.telefono.setText(usuario.getClave());
    }

    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla_lista_usuario,parent,false);
        return new AdapterHolder(view);
    }

    class AdapterHolder extends RecyclerView.ViewHolder {
    TextView nombre,apellido, correo, telefono;
    public AdapterHolder(@NonNull View itemView) {
        super(itemView);

        nombre = itemView.findViewById(R.id.plantilla_lista_usuario_nombre);
        apellido = itemView.findViewById(R.id.plantilla_lista_usuario_apellido);
        correo = itemView.findViewById(R.id.plantilla_lista_usuario_correo);
        telefono = itemView.findViewById(R.id.plantilla_lista_usuario_telefono);
    }
}
}
