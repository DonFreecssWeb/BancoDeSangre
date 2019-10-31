package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.proyecto.Adapters.AdapterListaUsuario;
import com.example.proyecto.R;
import com.example.proyecto.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Collection;

public class RecyclerListaUsuario extends AppCompatActivity {

    ArrayList<Usuario> usuarios = new ArrayList<>();;

    RecyclerView recyclerView;
    RecyclerView.Adapter madapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_lista_usuario);
        recyclerView = findViewById(R.id.rv_listaUsuario);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String cor = document.getString("correo");
                                String nom = document.getString("nombre");
                                String ape = document.getString("apellido");
                                String tel = document.getString("telefono");

                                usuarios.add(new Usuario(nom, ape, cor, tel));


                                //  Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            updateData();
                        } else {

                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });



    }
    public  void updateData(){
        madapter = new AdapterListaUsuario(usuarios);
        recyclerView.setAdapter(madapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
