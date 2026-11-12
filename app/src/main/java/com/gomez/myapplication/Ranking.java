package com.gomez.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gomez.myapplication.databinding.ActivityRankingBinding;

public class Ranking extends AppCompatActivity {

    // paso 1 declaro la variable binding
    private ActivityRankingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // paso 2 asigno en la variable el activity
        binding = ActivityRankingBinding.inflate(getLayoutInflater());

        // paso 3 seteo o configuro
        setContentView(binding.getRoot());

        // paso 4 funcion onclick
        // Botón volver
        binding.btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irMain = new Intent(Ranking.this, MainActivity.class);
                startActivity(irMain);
                finish();
            }
        });

        // Barra inferior: Botón Home
        binding.navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irMain = new Intent(Ranking.this, MainActivity.class);
                startActivity(irMain);
                finish();
            }
        });

        // Barra inferior: Botón Tienda
        binding.navTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irTienda = new Intent(Ranking.this, TiendaActivity.class);
                startActivity(irTienda);
                finish();
            }
        });

        binding.navDumbbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irMain = new Intent(Ranking.this, MainActivity.class);
                startActivity(irMain);
                finish();
            }
        });

        binding.navShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Ranking.this, "Ya estás en la Liga Ranking", Toast.LENGTH_SHORT).show();
            }
        });

        // Barra inferior: Botón Trofeo / Desafíos
        binding.navTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irDesafio = new Intent(Ranking.this, Desafio.class);
                startActivity(irDesafio);
            }
        });

        // Barra inferior: Botón Perfil / Salir
        binding.navDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irLogin = new Intent(Ranking.this, LoginActivity.class);
                startActivity(irLogin);
                finish();
            }
        });
    }
}
