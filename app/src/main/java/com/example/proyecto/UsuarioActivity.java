package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class UsuarioActivity extends AppCompatActivity {
TextView nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        nombre = findViewById(R.id.plantilla_lista_usuario_nombre);

        Bundle bundle = getIntent().getExtras();
             
        if (bundle!= null){
            Usuario usuario = (Usuario) bundle.getSerializable("a");
            nombre.setText(usuario.getNombre());
           
        }else{
            Toast.makeText(this, "Bundle no esta lleno", Toast.LENGTH_SHORT).show();
        }
            

       
    }
}
