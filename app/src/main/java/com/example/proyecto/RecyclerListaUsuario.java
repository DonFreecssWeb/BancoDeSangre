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

import com.example.proyecto.Adapters.AdapterFirebase;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class RecyclerListaUsuario extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference listaUsuarioRef = db.collection("users");

    private AdapterFirebase adapterFirebase;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_lista_usuario);
        FloatingActionButton btnAddNote = findViewById(R.id.btn_add_post);
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecyclerListaUsuario.this,NewPost.class));
            }
        });



    Query query = listaUsuarioRef.orderBy("correo", Query.Direction.ASCENDING);

    FirestoreRecyclerOptions<Usuario> options = new FirestoreRecyclerOptions.Builder<Usuario>()
                .setQuery(query,Usuario.class)
                .build();


        adapterFirebase = new AdapterFirebase(options);

        recyclerView = findViewById(R.id.rv_listaUsuario);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterFirebase);


/*
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

                });*/



    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterFirebase.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterFirebase.stopListening();
    }

    /* public boolean onCreateOptionsMenu(Menu menu) {
        updateData();
        getMenuInflater().inflate(R.menu.menu_save_comment,menu);
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
