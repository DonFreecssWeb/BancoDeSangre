package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContenedorActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor);

        BottomNavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.navigation_home:

                navController.navigate(R.id.homeFragment);
                break;
            case R.id.contactos_nav:

                navController.navigate(R.id.recyclerListaUsuario);
                break;
        }

        return false;
    }
}
