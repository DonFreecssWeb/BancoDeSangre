package com.example.proyecto.ui.home;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyecto.R;
import com.example.proyecto.model.Comentario;


import java.util.ArrayList;
import java.util.List;


public class MyHomeRecyclerViewAdapter extends RecyclerView.Adapter<MyHomeRecyclerViewAdapter.ViewHolder> {

    private final List<Comentario> mValues;

    public MyHomeRecyclerViewAdapter(List<Comentario> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.comentario.setText(mValues.get(position).getComentario());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView comentario;



        public ViewHolder(View view) {
            super(view);

            comentario = view.findViewById(R.id.comentarioTexto);

        }
    }
}
