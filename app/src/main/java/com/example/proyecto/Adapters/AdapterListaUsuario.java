package com.example.proyecto.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.Usuario;

import java.util.ArrayList;
import java.util.Collection;

public class AdapterListaUsuario extends RecyclerView.Adapter<AdapterListaUsuario.viewHolderDatos> implements Filterable   {
    ArrayList<Usuario> usuarios = new ArrayList<>();
    ArrayList<Usuario> usuariosFull;
    public AdapterListaUsuario(ArrayList<Usuario> usuarios) {

        this.usuarios = usuarios;
        this.usuariosFull = new ArrayList<>(usuarios);
    }
    public AdapterListaUsuario(){}


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

    @Override
    public Filter getFilter() {
        return seachFilter;
    }

    private Filter seachFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Usuario> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(usuariosFull);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Usuario item : usuariosFull) {
                    if (item.getCorreo().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            usuarios.clear();
            usuarios.addAll((Collection<? extends Usuario>) filterResults.values);
            notifyDataSetChanged();

        }
    };


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
            telefono.setText(usuario.getClave());
        }
    }


}
