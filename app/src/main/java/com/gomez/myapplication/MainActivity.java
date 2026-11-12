package com.gomez.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gomez.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // paso 1 declaro la variable binding
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // paso 2 asigno en la variable el activity
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // paso 3 seteo o configuro
        setContentView(binding.getRoot());

        // paso 4 funcion onclick para las rutinas
        binding.btnRutina1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Rutina 1", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnRutina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Rutina 2", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnRutina3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Rutina 3", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnRutina4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Rutina 4", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnRutina5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Rutina 5", Toast.LENGTH_SHORT).show();
            }
        });

        // paso 4 funcion onclick para la barra inferior
        binding.navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Inicio", Toast.LENGTH_SHORT).show();
            }
        });

        binding.navHeadphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Sección Audio", Toast.LENGTH_SHORT).show();
            }
        });

        binding.navDumbbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Sección Ejercicios", Toast.LENGTH_SHORT).show();
            }
        });

        binding.navShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Sección Ranking", Toast.LENGTH_SHORT).show();
            }
        });

        binding.navTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Sección Trofeos", Toast.LENGTH_SHORT).show();
            }
        });

        binding.navDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirige al Login al presionar el perfil
                Intent irLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(irLogin);
                finish();
            }
        });
    }
}
