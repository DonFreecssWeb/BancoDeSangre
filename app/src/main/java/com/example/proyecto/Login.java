package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText clave,correo;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inicializamos objeto
        mAuth = FirebaseAuth.getInstance();

        clave = findViewById(R.id.login_et_clave);
        correo = findViewById(R.id.crear_cuenta_correo);



    }



    public void login(View view) {
        String email = correo.getText().toString();
        String password = clave.getText().toString();

        if (email.isEmpty() || password.isEmpty() ){
            Toast.makeText(this, "Debe ingresar un usuario y contraseña", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email.toLowerCase(),password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Log.d("TAG", "signInWithEmail:success");

                        Intent intent = new Intent(Login.this, ContenedorActivity.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                        Toast.makeText(Login.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }


    }
    public void Registrar(View view){


        Intent intent = new Intent(this, RegistrarUsuario.class);
        startActivity(intent);

    }
}
