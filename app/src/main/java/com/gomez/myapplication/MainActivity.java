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

        // Reto activo del día (abre Desafío de Sebastián)
        binding.cardReto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irDesafio = new Intent(MainActivity.this, Desafio.class);
                startActivity(irDesafio);
            }
        });

        binding.btnRutina5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nodo Desafío Titán (abre Desafío de Sebastián)
                Intent irDesafio = new Intent(MainActivity.this, Desafio.class);
                startActivity(irDesafio);
            }
        });

        // paso 4 funcion onclick para la barra inferior
        binding.navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Inicio", Toast.LENGTH_SHORT).show();
            }
        });

        // 2. Icono de Tienda (abre la Tienda de Recompensas)
        binding.navTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irTienda = new Intent(MainActivity.this, TiendaActivity.class);
                startActivity(irTienda);
            }
        });

        binding.navDumbbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Sección Ejercicios", Toast.LENGTH_SHORT).show();
            }
        });

        // 4. Escudo / Ranking Liga (abre Ranking de Quim)
        binding.navShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irRanking = new Intent(MainActivity.this, Ranking.class);
                startActivity(irRanking);
            }
        });

        // 5. Trofeo / Desafíos (abre Desafío del Gimnasio)
        binding.navTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irDesafio = new Intent(MainActivity.this, Desafio.class);
                startActivity(irDesafio);
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
