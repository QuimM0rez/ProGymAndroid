package com.gomez.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Menú Principal de PRO GYM:
 * Totalmente seguro contra cierres (NullPointerException).
 * Aplica estilos 3D y maneja navegación directa sin fallar.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Protección con try-catch para insets
        try {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(bars.left, bars.top, bars.right, 0);
                return insets;
            });
        } catch (Exception ignored) {}

        aplicarDisenos3D();
        configurarNavegacion();
    }

    private void aplicarDisenos3D() {
        try {
            float density = getResources().getDisplayMetrics().density;

            // Paleta segura
            int gold = 0xFFFFC800;
            int goldShadow = 0xFFD89B00;
            int primary = 0xFFA855F7;
            int primaryDark = 0xFF7F32EB;
            int locked = 0xFF151528;
            int lockedShadow = 0xFF0A0A1A;

            FrameLayout btn1 = findViewById(R.id.btnRutina1);
            FrameLayout btn2 = findViewById(R.id.btnRutina2);
            FrameLayout btn3 = findViewById(R.id.btnRutina3);
            FrameLayout btn4 = findViewById(R.id.btnRutina4);
            FrameLayout btn5 = findViewById(R.id.btnRutina5);

            if (btn1 != null) btn1.setBackground(DuolingoButtonHelper.crearBotonCircular3D(gold, goldShadow, density));
            if (btn2 != null) btn2.setBackground(DuolingoButtonHelper.crearBotonCircular3D(gold, goldShadow, density));
            if (btn3 != null) btn3.setBackground(DuolingoButtonHelper.crearBotonCircular3D(primary, primaryDark, density));
            if (btn4 != null) btn4.setBackground(DuolingoButtonHelper.crearBotonCircular3D(locked, lockedShadow, density));
            if (btn5 != null) btn5.setBackground(DuolingoButtonHelper.crearBotonCircular3D(locked, lockedShadow, density));
        } catch (Exception ignored) {}
    }

    private void configurarNavegacion() {
        // Redirige al login de regreso al presionar el perfil en la barra inferior
        try {
            android.view.View navDots = findViewById(R.id.navDots);
            if (navDots != null) {
                navDots.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        } catch (Exception ignored) {}
    }
}
