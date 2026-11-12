package com.gomez.myapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Menú Principal de PRO GYM:
 * Combina la interfaz gamificada de Duolingo (TopBar con racha, XP, vidas,
 * camino en zigzag con botones circulares 3D y barra inferior de 6 accesos)
 * con el contenido real de PRO GYM: Retos activos diarios y Rutinas por grupos musculares.
 */
public class MainActivity extends AppCompatActivity {

    private FrameLayout btnRutina1, btnRutina2, btnRutina3, btnRutina4, btnRutina5;
    private View contenedorBurbuja;
    private TextView tvRacha, tvXp, tvVidas;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, 0);
            return insets;
        });

        enlazarVistas();
        aplicarDisenos3D();
        iniciarAnimacionBurbuja();
        configurarEventos();
    }

    private void enlazarVistas() {
        btnRutina1 = findViewById(R.id.btnRutina1);
        btnRutina2 = findViewById(R.id.btnRutina2);
        btnRutina3 = findViewById(R.id.btnRutina3);
        btnRutina4 = findViewById(R.id.btnRutina4);
        btnRutina5 = findViewById(R.id.btnRutina5);
        contenedorBurbuja = findViewById(R.id.contenedorBurbuja);
        tvRacha = findViewById(R.id.tvRacha);
        tvXp = findViewById(R.id.tvXp);
        tvVidas = findViewById(R.id.tvVidas);
    }

    private void aplicarDisenos3D() {
        float density = getResources().getDisplayMetrics().density;

        int gold = ContextCompat.getColor(this, R.color.duo_gold);
        int goldShadow = ContextCompat.getColor(this, R.color.duo_gold_shadow);

        int primary = ContextCompat.getColor(this, R.color.progym_primary);
        int primaryDark = ContextCompat.getColor(this, R.color.progym_primary_dark);

        int locked = ContextCompat.getColor(this, R.color.progym_card);
        int lockedShadow = ContextCompat.getColor(this, R.color.progym_nav);

        // Rutinas 1 y 2: Completadas (Dorado 3D)
        btnRutina1.setBackground(DuolingoButtonHelper.crearBotonCircular3D(gold, goldShadow, density));
        btnRutina2.setBackground(DuolingoButtonHelper.crearBotonCircular3D(gold, goldShadow, density));

        // Rutina 3: Actual del día (Morado Neón 3D)
        btnRutina3.setBackground(DuolingoButtonHelper.crearBotonCircular3D(primary, primaryDark, density));

        // Rutinas 4 y 5: Bloqueadas (Gris oscuro 3D)
        btnRutina4.setBackground(DuolingoButtonHelper.crearBotonCircular3D(locked, lockedShadow, density));
        btnRutina5.setBackground(DuolingoButtonHelper.crearBotonCircular3D(locked, lockedShadow, density));
    }

    private void iniciarAnimacionBurbuja() {
        float density = getResources().getDisplayMetrics().density;
        ObjectAnimator anim = ObjectAnimator.ofFloat(contenedorBurbuja, "translationY", 0f, -6f * density, 0f);
        anim.setDuration(1100);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();
    }

    private void configurarEventos() {
        // Clic en Rutina del día (Día de Pierna)
        View.OnClickListener listenerEntrenar = v -> {
            v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(80).withEndAction(() ->
                    v.animate().scaleX(1f).scaleY(1f).setDuration(80).withEndAction(() -> {
                        int xpActual = Integer.parseInt(tvXp.getText().toString()) + 35;
                        tvXp.setText(String.valueOf(xpActual));
                        Toast.makeText(this, "¡Rutina de Piernas iniciada! +35 XP 💪🔥", Toast.LENGTH_SHORT).show();
                    }).start()
            ).start();
        };

        btnRutina3.setOnClickListener(listenerEntrenar);
        contenedorBurbuja.setOnClickListener(listenerEntrenar);

        // Rutinas completadas
        btnRutina1.setOnClickListener(v ->
                Toast.makeText(this, "Rutina de Pecho & Tríceps completada 🏆", Toast.LENGTH_SHORT).show());
        btnRutina2.setOnClickListener(v ->
                Toast.makeText(this, "Rutina de Espalda & Bíceps completada 🏆", Toast.LENGTH_SHORT).show());

        // Rutinas bloqueadas
        View.OnClickListener listenerBloqueado = v ->
                Toast.makeText(this, "🔒 Completa la rutina actual para desbloquear la siguiente", Toast.LENGTH_SHORT).show();
        btnRutina4.setOnClickListener(listenerBloqueado);
        btnRutina5.setOnClickListener(listenerBloqueado);

        // Barra inferior
        findViewById(R.id.navHome).setOnClickListener(v ->
                Toast.makeText(this, "Ruta de Rutinas", Toast.LENGTH_SHORT).show());
        findViewById(R.id.navHeadphones).setOnClickListener(v ->
                Toast.makeText(this, "Música para entrenar 🎧", Toast.LENGTH_SHORT).show());
        findViewById(R.id.navDumbbell).setOnClickListener(v ->
                Toast.makeText(this, "Ejercicios y Técnicas 🏋️‍♂️", Toast.LENGTH_SHORT).show());
        findViewById(R.id.navShield).setOnClickListener(v ->
                Toast.makeText(this, "Ranking de la Liga 🛡️", Toast.LENGTH_SHORT).show());
        findViewById(R.id.navTrophy).setOnClickListener(v ->
                Toast.makeText(this, "Tus Logros y Retos 🏆", Toast.LENGTH_SHORT).show());

        // Cerrar sesión desde el icono de perfil
        findViewById(R.id.navDots).setOnClickListener(v -> {
            prefs.edit().putBoolean(LoginActivity.KEY_SESION, false).apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        });
    }
}
