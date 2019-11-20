package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.VideoView;

import com.example.proyecto.ui.fragment.RecyclerListaUsuario;

public class RadioActivity extends AppCompatActivity {

    RadioButton rb1, rb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        rb1 = findViewById(R.id.rbDonacion);
        rb2 = findViewById(R.id.rbInformacion);

        VideoView videoView = findViewById(R.id.videoView);
        String video_path = "android.resource://" + getPackageName() + "/" + R.raw.bacosang;
        Uri uri = Uri.parse(video_path);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

    }

    public void Confirmar(View view){

        String cad= "Seleccionado: \n";

        if (rb1.isChecked()==true){

            Intent intent = new Intent(this, NewPost.class);
            startActivity(intent);

        }else {

            Intent intent = new Intent(this, RecyclerListaUsuario.class);
            startActivity(intent);
        }
    }

}
