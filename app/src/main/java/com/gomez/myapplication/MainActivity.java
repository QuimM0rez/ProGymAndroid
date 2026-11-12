package com.gomez.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/** Pantalla de inicio (se muestra después de iniciar sesión). */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);

        // Recibe el correo enviado desde el Login por el Intent
        String correo = getIntent().getStringExtra(LoginActivity.KEY_CORREO);
        if (correo == null) correo = prefs.getString(LoginActivity.KEY_CORREO, "atleta");
        String nombre = correo.contains("@") ? correo.substring(0, correo.indexOf('@')) : correo;

        TextView tvHola = findViewById(R.id.tvHola);
        tvHola.setText(getString(R.string.home_hola, nombre));

        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(v -> {
            prefs.edit().putBoolean(LoginActivity.KEY_SESION, false).apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
