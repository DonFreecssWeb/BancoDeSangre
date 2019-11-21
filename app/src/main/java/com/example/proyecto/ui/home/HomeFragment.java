package com.example.proyecto.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyecto.R;
import com.example.proyecto.model.Comentario;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    Comentario comentario;

    FirebaseFirestore mfirebaseFirestore;
    //String usuarioActual = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private int mColumnCount = 1;

    List<Comentario> lista;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_list, container, false);

        recyclerView = view.findViewById(R.id.rv_listaComentarios);
        lista = new ArrayList<>();
        mfirebaseFirestore = FirebaseFirestore.getInstance();

       // Toast.makeText(getContext(), "Solo entra hasta aca", Toast.LENGTH_SHORT).show();


        mfirebaseFirestore.collection("userss").document().collection("posts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String comment;

                                comment = document.getString("comentario");


                                lista.add(new Comentario(comment));
                                Toast.makeText(getContext(), "" + comment, Toast.LENGTH_SHORT).show();

                            }

                            updateData();
                        } else {
                            Toast.makeText(getContext(), "no se pudo", Toast.LENGTH_SHORT).show();
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Algo a fallado"+e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG",e.getMessage());
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(getContext(), "Se canceló", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void updateData() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyHomeRecyclerViewAdapter(lista));
    }
}