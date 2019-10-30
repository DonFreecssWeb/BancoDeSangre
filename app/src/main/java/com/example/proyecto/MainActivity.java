package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
private EditText correo,clave;

private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.crear_cuenta_corre);
        clave = findViewById(R.id.login_et_clave);

        mAuth = FirebaseAuth.getInstance();

    }

    public void Registrar(View view){


        Intent intent = new Intent(this, RegistrarUsuario.class);
        startActivity(intent);

    }

    public void Buscar(View view){
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

                                //if (correo.getText().toString().equals(cor) && clave.getText().toString().equals(nom)){

                                    //Usuario usuario = new Usuario(cor,ape,cor,tel);
                                    //Bundle bundle = new Bundle();
                                    //bundle.putSerializable("a",usuario);

                                    Intent intent = new Intent(MainActivity.this, RecyclerListaUsuario.class);
                                    //intent.putExtras(bundle);

                                    startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                                    //Toast.makeText(MainActivity.this, "correo y nombre validos"+ape, Toast.LENGTH_SHORT).show();
                                //}
//                                else {
//                                    Toast.makeText(MainActivity.this, "correo y nombre invalidos", Toast.LENGTH_SHORT).show();
//                                }
                              //  Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {

                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

}
