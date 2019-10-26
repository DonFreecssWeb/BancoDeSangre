package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {
EditText nombre,apellido,correo,telefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
nombre = findViewById(R.id.et_nombre);
apellido = findViewById(R.id.et_apellido);
correo = findViewById(R.id.et_correo);
telefono = findViewById(R.id.et_telefono);

    }

    public void Buscar(View view){
        Map<String, Object> user = new HashMap<>();
        user.put("nombre", nombre.getText().toString());
        user.put("apellido", apellido.getText().toString());
        user.put("correo", correo.getText().toString());
        user.put("telefono",telefono.getText().toString());
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(RegistrarActivity.this, "exitoso", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                       // Log.w(TAG, "Error adding document", e);
                    }
                });

        Intent intent = new Intent(this, BuscarActivity.class);
        startActivity(intent);

    }
}
