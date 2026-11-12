package com.gomez.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    FrameLayout btnRutina1, btnRutina2, btnRutina3, btnRutina4, btnRutina5;
    FrameLayout navHome, navHeadphones, navDumbbell, navShield, navTrophy, navDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar botones de las rutinas del camino
        btnRutina1 = findViewById(R.id.btnRutina1);
        btnRutina2 = findViewById(R.id.btnRutina2);
        btnRutina3 = findViewById(R.id.btnRutina3);
        btnRutina4 = findViewById(R.id.btnRutina4);
        btnRutina5 = findViewById(R.id.btnRutina5);

        // Enlazar botones de la barra inferior
        navHome = findViewById(R.id.navHome);
        navHeadphones = findViewById(R.id.navHeadphones);
        navDumbbell = findViewById(R.id.navDumbbell);
        navShield = findViewById(R.id.navShield);
        navTrophy = findViewById(R.id.navTrophy);
        navDots = findViewById(R.id.navDots);

        // OnClick de cada botón de rutinas (listos para redirigir cuando estén las pantallas)
        btnRutina1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí irá el Intent hacia la pantalla de Rutina 1
                Toast.makeText(MainActivity.this, "Rutina 1", Toast.LENGTH_SHORT).show();
            }
        });

        btnRutina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí irá el Intent hacia la pantalla de Rutina 2
                Toast.makeText(MainActivity.this, "Rutina 2", Toast.LENGTH_SHORT).show();
            }
        });

        btnRutina3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí irá el Intent hacia la pantalla de Rutina 3
                Toast.makeText(MainActivity.this, "Rutina 3", Toast.LENGTH_SHORT).show();
            }
        });

        btnRutina4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí irá el Intent hacia la pantalla de Rutina 4
                Toast.makeText(MainActivity.this, "Rutina 4", Toast.LENGTH_SHORT).show();
            }
        });

        btnRutina5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí irá el Intent hacia la pantalla de Rutina 5
                Toast.makeText(MainActivity.this, "Rutina 5", Toast.LENGTH_SHORT).show();
            }
        });

        // OnClick de cada botón de la barra inferior
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicio
            }
        });

        navHeadphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí irá el Intent a Música/Audio
                Toast.makeText(MainActivity.this, "Sección Audio", Toast.LENGTH_SHORT).show();
            }
        });

        navDumbbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí irá el Intent a Ejercicios
                Toast.makeText(MainActivity.this, "Sección Ejercicios", Toast.LENGTH_SHORT).show();
            }
        });

        navShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí irá el Intent a Ranking
                Toast.makeText(MainActivity.this, "Sección Ranking", Toast.LENGTH_SHORT).show();
            }
        });

        navTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí irá el Intent a Trofeos
                Toast.makeText(MainActivity.this, "Sección Trofeos", Toast.LENGTH_SHORT).show();
            }
        });

        navDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirige al Login al presionar el perfil
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
