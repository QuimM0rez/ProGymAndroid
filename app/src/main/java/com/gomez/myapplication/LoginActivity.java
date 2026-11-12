package com.gomez.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gomez.myapplication.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    // paso 1 declaro la variable binding
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // paso 2 asigno en la variable el activity
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        // paso 3 seteo o configuro
        setContentView(binding.getRoot());

        // paso 4 funcion onclick
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irSiguiente = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(irSiguiente);
            }
        });
    }
}
