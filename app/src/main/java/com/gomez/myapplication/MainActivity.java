package com.gomez.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Menú Principal de PRO GYM (Requerimiento de la instructora):
 * Interfaz visual con botones circulares 3D y navegación limpia.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, 0);
            return insets;
        });

        aplicarDisenos3D();
        configurarNavegacion();
    }

    private void aplicarDisenos3D() {
        float density = getResources().getDisplayMetrics().density;

        int gold = ContextCompat.getColor(this, R.color.duo_gold);
        int goldShadow = ContextCompat.getColor(this, R.color.duo_gold_shadow);

        int primary = ContextCompat.getColor(this, R.color.progym_primary);
        int primaryDark = ContextCompat.getColor(this, R.color.progym_primary_dark);

        int locked = ContextCompat.getColor(this, R.color.progym_card);
        int lockedShadow = ContextCompat.getColor(this, R.color.progym_nav);

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
    }

    private void configurarNavegacion() {
        // Redirige al login al presionar el perfil o cerrar sesión
        findViewById(R.id.navDots).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
