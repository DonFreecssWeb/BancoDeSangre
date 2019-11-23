package com.example.proyecto.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto.Adapters.AdapterMensajes;
import com.example.proyecto.R;
import com.example.proyecto.interfaces.MyCallback;
import com.example.proyecto.model.Mensaje;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class ChatBoard extends AppCompatActivity implements View.OnClickListener {
    private ImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private Button btnEnviar;
private ImageButton btnEnviarFoto;

    private AdapterMensajes adapter;

    private FirebaseFirestore database;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private CollectionReference collectionReference;
    private static final int PHOTO_SEND = 1; //para no repetir el 1 y no olvidarse es el requestCode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_board);

        fotoPerfil = findViewById(R.id.fotoPerfil);
        nombre = findViewById(R.id.nombre);
        rvMensajes = findViewById(R.id.rv_Mensajes);
        txtMensaje = findViewById(R.id.txtMensaje);
        btnEnviar = findViewById(R.id.btnEnviarMensaje);
        btnEnviarFoto = findViewById(R.id.btnEnviarFoto);

        database = FirebaseFirestore.getInstance();
        collectionReference = database.collection("chat");
        storage = FirebaseStorage.getInstance();



        adapter = new AdapterMensajes(this);

        LinearLayoutManager l = new LinearLayoutManager(this);

        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(this);
        btnEnviarFoto.setOnClickListener(this);


        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });
//"P4kbJ5KAV1zRWXhckAdn"
        //                    m.setNombre(documentSnapshot.getString("nombre"));
//                    m.setFotoPerfil(documentSnapshot.getString("fotoPerfil"));
//                    m.setHora(documentSnapshot.getString("hora"));
//                    m.setMensaje(documentSnapshot.getString("mensaje"));
//                    m.setType_mensaje(documentSnapshot.getString("type_mensaje"));
//                    m.setUrlFoto(documentSnapshot.getString("urlFoto"));

   /* collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            if (task.isSuccessful()){
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    Mensaje m = documentSnapshot.toObject(Mensaje.class).;
                    adapter.addMensaje(m);

                }

            }
        }
    });*/

   collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
       @Override
       public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

           for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges())
           {
               switch (documentChange.getType()){
                   case ADDED:
                       Mensaje m = documentChange.getDocument().toObject(Mensaje.class);
                       adapter.addMensaje(m);
               }
           }
       }
   });


    }

    private void setScrollBar(){
        rvMensajes.scrollToPosition(adapter.getItemCount());
    }

//1 por que es un mensaje y no una foto
    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.btnEnviarMensaje:
        //adapter.addMensaje(new Mensaje(txtMensaje.getText().toString(),nombre.getText().toString(),"","1","00:00"));
        collectionReference.document().set(new Mensaje(txtMensaje.getText().toString(),nombre.getText().toString(),"","1","00:00"));
        txtMensaje.setText("");
        break;
    case  R.id.btnEnviarFoto:
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/jpeg");
        i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
       startActivityForResult(Intent.createChooser(i,"Selecciona una foto"),PHOTO_SEND);

        break;
}

    }
//Çuando hallamos elegido un intent ya sea salido de la aplicacion o vuelva a entrar, nos devuelva un resultado,
    //en este caso el 1 del requestCode, node debe devolver el 1 cuando seleccionemos la imagen correctamente
    //el requestcode va a ser el mismo numero de arriba siempre y cuando lo hallamos seleccionado correctamente
    //resultcode es para que el resultado sea correcto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_SEND && resultCode == RESULT_OK){ //imagen seleccionado correctamente y resultado es OK
            Uri u = data.getData();
            storageReference = storage.getReference("imagenes_chat");
            //obtenemos como la key de la foto para que todas sea diferentes a las demas
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());

           fotoReferencia.putFile(u).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
               @Override
               public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                   return fotoReferencia.getDownloadUrl();
               }
           }).addOnCompleteListener(new OnCompleteListener<Uri>() {
               @Override
               public void onComplete(@NonNull Task<Uri> task) {
                   Uri downloadUrl = task.getResult();
                   Mensaje m = new Mensaje("Jorge te ha enviado una foto", downloadUrl.toString(), nombre.getText().toString(), "", "2", "00:00");
                   //enviamos mensaje a la persona
                   collectionReference.document().set(m);
                   adapter.notifyDataSetChanged();
               }
           });
        }
    }
}
