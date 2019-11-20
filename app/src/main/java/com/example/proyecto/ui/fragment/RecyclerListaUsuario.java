package com.example.proyecto.ui.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto.Adapters.AdapterListaUsuario;
import com.example.proyecto.R;
import com.example.proyecto.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

public class RecyclerListaUsuario extends Fragment {

    ArrayList<Usuario> usuarios = new ArrayList<>();;

    RecyclerView recyclerView;
    RecyclerView.Adapter madapter;

    String nombre;
    String apellido;
    String correo;
    String urlimagen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recycler_lista_usuario,container,false);


        recyclerView = view.findViewById(R.id.rv_listaUsuario);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                nombre = document.getString("nombre");
                                apellido = document.getString("correo");
                                correo = document.getString("apellido");
                                urlimagen = document.getString("urlImage");

                                usuarios.add(new Usuario(nombre, apellido, correo, urlimagen));


                                //  Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            updateData();
                        } else {

                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });


        return view;
    }

    public  void updateData(){
        madapter = new AdapterListaUsuario(getContext(),usuarios);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(madapter);
    }


/*    public boolean onCreateOptionsMenu(Menu menu) {
        updateData();
        getMenuInflater().inflate(R.menu.topmenu,menu);
        MenuItem seachItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) seachItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String s) {
                return false;
            }


            public boolean onQueryTextChange(String s) {

                adaptador.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }*/





}
