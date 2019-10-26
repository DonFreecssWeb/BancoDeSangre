package com.example.proyecto.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.Usuario;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterListaUsuario extends RecyclerView.Adapter<AdapterListaUsuario.viewHolderDatos> {
    ArrayList<Usuario> usuarios = new ArrayList<>();

    public AdapterListaUsuario(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }



    @NonNull
    @Override
    public AdapterListaUsuario.viewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla_lista_usuario,parent,false);
        return new viewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListaUsuario.viewHolderDatos holder, int position) {
    holder.setUsuarios(usuarios.get(position));
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }


    public static class viewHolderDatos extends RecyclerView.ViewHolder {
        public TextView nombre,apellido,correo,telefono;
        public viewHolderDatos(@NonNull View itemView) {
            super(itemView);

             nombre = itemView.findViewById(R.id.plantilla_lista_usuario_nombre);
             apellido = itemView.findViewById(R.id.plantilla_lista_usuario_apellido);
             correo = itemView.findViewById(R.id.plantilla_lista_usuario_correo);
             telefono = itemView.findViewById(R.id.plantilla_lista_usuario_telefono);
        }
        public  void setUsuarios(Usuario usuario) {

            nombre.setText(usuario.getNombre());
            apellido.setText(usuario.getApellido());
            correo.setText(usuario.getCorreo());
            telefono.setText(usuario.getTelefono());
        }
    }
}
