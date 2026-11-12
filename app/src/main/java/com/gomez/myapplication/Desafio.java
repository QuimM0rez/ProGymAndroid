package com.gomez.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gomez.myapplication.databinding.ActivityDesafioBinding;

public class Desafio extends AppCompatActivity {

    // paso 1 declaro la variable binding
    private ActivityDesafioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // paso 2 asigno en la variable el activity
        binding = ActivityDesafioBinding.inflate(getLayoutInflater());

        // paso 3 seteo o configuro
        setContentView(binding.getRoot());

        // paso 4 funcion onclick
        binding.btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnVolverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
