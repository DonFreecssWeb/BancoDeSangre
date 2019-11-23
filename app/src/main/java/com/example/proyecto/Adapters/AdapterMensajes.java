package com.example.proyecto.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.model.Mensaje;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterMensajes  extends RecyclerView.Adapter<HolderMensaje> {

    List<Mensaje> listMensaje = new ArrayList<>();

    private Context context;

    public AdapterMensajes(Context context) {
        this.context = context;
    }

    public void addMensaje (Mensaje m){
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size()); //Si no se pone esto no aparece nada
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_mensajes,parent,false);

        return new HolderMensaje(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensaje holder, int position) {
            holder.getNombre().setText(listMensaje.get(position).getNombre());
            holder.getMensaje().setText(listMensaje.get(position).getMensaje());
            holder.getHora().setText(listMensaje.get(position).getHora());
            //si es 2 es que acaba de enviar una foto por mensaje

            if (listMensaje.get(position).getType_mensaje().equals("2")){
                holder.getFotoMensaje().setVisibility(View.VISIBLE);
                holder.getMensaje().setVisibility(View.VISIBLE);
                //ahora descargamos la imagen
                Picasso.with(context).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoMensaje());

            }else if(listMensaje.get(position).getType_mensaje().equals("1")){
                //si es 1 solo debe estar visible el mensaje y no la foto
                holder.getFotoMensaje().setVisibility(View.GONE);
                holder.getMensaje().setVisibility(View.VISIBLE);
            }
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
