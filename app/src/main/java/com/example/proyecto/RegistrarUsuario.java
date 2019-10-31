package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.auth.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity {
EditText nombre;
EditText ape;
EditText correo;
EditText clave;





private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

nombre = findViewById(R.id.crear_cuenta_nombre);
ape = findViewById(R.id.crear_cuenta_apellido);
correo = findViewById(R.id.crear_cuenta_correo);
clave = findViewById(R.id.crear_cuenta_clave);

        //  mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();

    }

    public void crear (View view) {
        String email = correo.getText().toString();
        String password = clave.getText().toString();
        String name = nombre.getText().toString();
        String lastname = ape.getText().toString();


        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || lastname.isEmpty()) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrarUsuario.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegistrarUsuario.this, "Cuenta creada exitosamente",
                                Toast.LENGTH_SHORT).show();
                        crearDataFirestore();
                        Intent intent = new Intent(RegistrarUsuario.this, Login.class);
                        startActivity(intent);

                    } else {
                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegistrarUsuario.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }



    }

    public void crearDataFirestore(){




        Map<String, Object> user = new HashMap<>();
        user.put("nombre", nombre.getText().toString());
        user.put("apellido", ape.getText().toString());
        user.put("correo", correo.getText().toString());



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });

    }
}
