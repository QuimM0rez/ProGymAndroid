package com.gomez.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gomez.myapplication.databinding.ActivityTiendaBinding;

public class TiendaActivity extends AppCompatActivity {

    // paso 1 declaro la variable binding
    private ActivityTiendaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // paso 2 asigno en la variable el activity
        binding = ActivityTiendaBinding.inflate(getLayoutInflater());

        // paso 3 seteo o configuro
        setContentView(binding.getRoot());

        // paso 4 funcion onclick
        // Botón volver
        binding.btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irMain = new Intent(TiendaActivity.this, MainActivity.class);
                startActivity(irMain);
                finish();
            }
        });

        // Botón canjear puntos del encabezado
        binding.btnCanjearPuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TiendaActivity.this, "Tienes 750 puntos disponibles para canjear", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón canjear botella
        binding.btnCanjearBotella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TiendaActivity.this, "¡Canjeaste Botella Deportiva por 500 puntos!", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón canjear toalla
        binding.btnCanjearToalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TiendaActivity.this, "¡Canjeaste Toalla de Gimnasio por 300 puntos!", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón canjear creatina
        binding.btnCanjearCreatina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TiendaActivity.this, "¡Canjeaste Creatina Monohidrato por 1200 puntos!", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón canjear proteína
        binding.btnCanjearProteina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TiendaActivity.this, "¡Canjeaste Proteína Whey por 1500 puntos!", Toast.LENGTH_SHORT).show();
            }
        });

        // Barra inferior: Botón Home regresa al MainActivity
        binding.navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irMain = new Intent(TiendaActivity.this, MainActivity.class);
                startActivity(irMain);
                finish();
            }
        });

        // 2. Icono de Tienda (actual)
        binding.navTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TiendaActivity.this, "Ya estás en la Tienda de Recompensas", Toast.LENGTH_SHORT).show();
            }
        });

        binding.navDumbbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irMain = new Intent(TiendaActivity.this, MainActivity.class);
                startActivity(irMain);
                finish();
            }
        });

        binding.navShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TiendaActivity.this, "Sección Ranking", Toast.LENGTH_SHORT).show();
            }
        });

        // 5. Trofeo / Desafíos (abre Desafío del Gimnasio)
        binding.navTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irDesafio = new Intent(TiendaActivity.this, Desafio.class);
                startActivity(irDesafio);
            }
        });

        binding.navDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irLogin = new Intent(TiendaActivity.this, LoginActivity.class);
                startActivity(irLogin);
                finish();
            }
        });
    }
}
