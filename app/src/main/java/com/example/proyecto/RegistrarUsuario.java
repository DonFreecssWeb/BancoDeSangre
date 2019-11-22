package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyecto.ui.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity {
    EditText nombre;
    EditText ape;
    EditText correo;
    EditText clave;
    ImageView imagen;


    private FirebaseAuth mAuth;
    private FirebaseStorage mFirebaseDatabase;
    private StorageReference mStorageReference;
    private String downloadImageUrl;
    private Uri ImageUri;
    private String saveCurrentDate;
    private String saveCurrentTime;
    private String productRandomKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        nombre = findViewById(R.id.crear_cuenta_nombre);
        ape = findViewById(R.id.crear_cuenta_apellido);
        correo = findViewById(R.id.crear_cuenta_correo);
        clave = findViewById(R.id.crear_cuenta_clave);
        imagen = findViewById(R.id.crear_cuenta_imagen);
        //  mAuth = FirebaseAuth.getInstance();

            //Picker imagen
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");

                startActivityForResult(Intent.createChooser(gallery, "Selecciona una imagen"), 1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseDatabase.getInstance().getReference(); //no le habia puesto el getinstance

    }
    //metodo que crea la autentificacion del usuario.
    public void crear(View view) {
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

                        addImagen();
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
    //agregar imagen al STORAGE
    private void addImagen() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate +" "+ saveCurrentTime;



        //final StorageReference mountainsRef = storageRef.child("mountains.jpg");


        imagen.setDrawingCacheEnabled(true);
        imagen.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        final StorageReference filePath = mStorageReference.child(productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putBytes(data);
       //final UploadTask uploTastURLImagen = mountainsRef.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

                 mStorageReference.child(productRandomKey + ".jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        downloadImageUrl = task.getResult().toString();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            crearDataFirestore();   //lleva la data al firestore
                        }
                    }
                });

            }
        });
    }

    //metodo parte de picker imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1){
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

               imagen.animate().rotationY(20);

                imagen.animate().setDuration(5000);

                imagen.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //metodo que salva los datos del usuario
    public void crearDataFirestore() {

        Map<String, Object> user = new HashMap<>();
        user.put("nombre", nombre.getText().toString());
        user.put("apellido", ape.getText().toString());
        user.put("correo", correo.getText().toString());
        user.put("urlImage",downloadImageUrl);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(correo.getText().toString())
                .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RegistrarUsuario.this, "Grabado los datos", Toast.LENGTH_SHORT).show();
            }
        });
                
    }
}
