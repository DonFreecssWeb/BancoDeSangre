package com.example.proyecto.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
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


        String usuarioActual = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DocumentReference comentarioReference = mfirebaseFirestore.collection("users").document();


        mfirebaseFirestore.collectionGroup("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Toast.makeText(getContext(), "Entra", Toast.LENGTH_SHORT).show();


                if (task.isSuccessful()){

                    String comentario;
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        comentario = documentSnapshot.getString("comentario");
                        lista.add(new Comentario(comentario));

                    }
                }
                updateData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "falla", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void updateData() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyHomeRecyclerViewAdapter(lista));
    }
}