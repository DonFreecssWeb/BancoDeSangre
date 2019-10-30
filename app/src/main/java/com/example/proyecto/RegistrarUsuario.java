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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity {
EditText nombre,apellido,correo,clave;



private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
nombre = findViewById(R.id.crear_cuenta_nombre);
apellido = findViewById(R.id.crear_cuenta_apellido);
correo = findViewById(R.id.crear_cuenta_corre);
clave = findViewById(R.id.crear_cuenta_clave);

        mAuth = FirebaseAuth.getInstance();


    }
    String usuarioActual;

    public void crear (View view){
        String email = correo.getText().toString();
        String password = clave.getText().toString();
        String name = nombre.getText().toString();
        String lastname = apellido.getText().toString();
        String id = "";

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || lastname.isEmpty()) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrarUsuario.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegistrarUsuario.this, "Authentication OK.",
                                Toast.LENGTH_SHORT).show();



                    } else {
                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegistrarUsuario.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            crearDataFirestore();

        }

    }


    public void crearDataFirestore(){




        Map<String, Object> user = new HashMap<>();
        user.put("nombre", nombre.getText().toString());
        user.put("apellido", apellido.getText().toString());
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
