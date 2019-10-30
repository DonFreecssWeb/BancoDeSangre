package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewPost extends AppCompatActivity {

    FirebaseUser usuarioActual;
    EditText comentario;
    private FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        comentario = findViewById(R.id.post_description);
    }



    public void saveNote(View view) {
        String comment = comentario.getText().toString();
        if (comment.trim().isEmpty()) {
            Toast.makeText(this, "Inserte un comentario", Toast.LENGTH_SHORT).show();
        }
        usuarioActual = FirebaseAuth.getInstance().getCurrentUser();

    mFirestore.collection("users").document(usuarioActual.getUid()).collection("posts").add(new Comentario(comment)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
            Toast.makeText(NewPost.this, "Comentario adherido", Toast.LENGTH_SHORT).show();
            finish();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(NewPost.this, "Ha fallado la insercion", Toast.LENGTH_SHORT).show();
        }
    });
       /* CollectionReference reference = FirebaseFirestore.getInstance().collection("users");

        reference.add(new Comentario(comment));

        Toast.makeText(this, "Comentario adherido", Toast.LENGTH_SHORT).show();
        finish();*/
        //
/*  mFirestore = FirebaseFirestore.getInstance();
        Map<String, String> userMap = new HashMap<>();

        userMap.put("comentario", comment);

        mFirestore.collection("users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {


            }
        });*/




    }
}
