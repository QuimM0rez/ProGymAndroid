package com.gomez.myapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
 * Menú principal idéntico a la captura de Duolingo:
 * - Barra superior rosa pastel con Bandera, Racha (12), Gemas (500) y Vidas (5).
 * - Camino en zigzag:
 *     * 3 Círculos dorados completados con ✔️.
 *     * Personaje acostado en el suelo jugando consola rosa/celeste.
 *     * Burbuja "EMPEZAR" rebotando sobre el círculo rosa actual (con libro).
 *     * Círculos grises bloqueados con candado 🔒.
 * - Barra inferior con los 6 iconos idénticos: Home seleccionado, audífonos, mancuerna, escudo, trofeo y tres puntos.
 */
public class MainActivity extends AppCompatActivity {

    private FrameLayout nodo1, nodo2, nodo3, nodo4, nodo5, nodo6;
    private View contenedorBurbuja;

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

        enlazarVistas();
        aplicarDisenos3D();
        iniciarAnimacionBurbuja();
        configurarClics();
    }

    private void enlazarVistas() {
        nodo1 = findViewById(R.id.nodo1);
        nodo2 = findViewById(R.id.nodo2);
        nodo3 = findViewById(R.id.nodo3);
        nodo4 = findViewById(R.id.nodo4);
        nodo5 = findViewById(R.id.nodo5);
        nodo6 = findViewById(R.id.nodo6);
        contenedorBurbuja = findViewById(R.id.contenedorBurbuja);
    }

    private void aplicarDisenos3D() {
        float density = getResources().getDisplayMetrics().density;

        int gold = ContextCompat.getColor(this, R.color.duo_gold);
        int goldShadow = ContextCompat.getColor(this, R.color.duo_gold_shadow);

        int pink = ContextCompat.getColor(this, R.color.duo_pink_current);
        int pinkShadow = ContextCompat.getColor(this, R.color.duo_pink_shadow);

        int locked = ContextCompat.getColor(this, R.color.duo_locked);
        int lockedShadow = ContextCompat.getColor(this, R.color.duo_locked_shadow);

        // Nodos dorados (completados)
        nodo1.setBackground(DuolingoButtonHelper.crearBotonCircular3D(gold, goldShadow, density));
        nodo2.setBackground(DuolingoButtonHelper.crearBotonCircular3D(gold, goldShadow, density));
        nodo3.setBackground(DuolingoButtonHelper.crearBotonCircular3D(gold, goldShadow, density));

        // Nodo rosa actual (con libro)
        nodo4.setBackground(DuolingoButtonHelper.crearBotonCircular3D(pink, pinkShadow, density));

        // Nodos grises (bloqueados)
        nodo5.setBackground(DuolingoButtonHelper.crearBotonCircular3D(locked, lockedShadow, density));
        nodo6.setBackground(DuolingoButtonHelper.crearBotonCircular3D(locked, lockedShadow, density));
    }

    private void iniciarAnimacionBurbuja() {
        float density = getResources().getDisplayMetrics().density;
        ObjectAnimator anim = ObjectAnimator.ofFloat(contenedorBurbuja, "translationY", 0f, -6f * density, 0f);
        anim.setDuration(1200);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();
    }

    private void configurarClics() {
        // Clic en el botón rosa actual
        View.OnClickListener listenerEmpezar = v -> {
            v.animate().scaleX(0.92f).scaleY(0.92f).setDuration(80).withEndAction(() ->
                    v.animate().scaleX(1f).scaleY(1f).setDuration(80).withEndAction(() ->
                            Toast.makeText(this, "¡Iniciando lección de PRO GYM! 🔥", Toast.LENGTH_SHORT).show()
                    ).start()
            ).start();
        };

        nodo4.setOnClickListener(listenerEmpezar);
        contenedorBurbuja.setOnClickListener(listenerEmpezar);

        // Nodos completados
        View.OnClickListener listenerCompletado = v ->
                Toast.makeText(this, "Nivel completado con éxito 🏆", Toast.LENGTH_SHORT).show();

        nodo1.setOnClickListener(listenerCompletado);
        nodo2.setOnClickListener(listenerCompletado);
        nodo3.setOnClickListener(listenerCompletado);

        // Nodos bloqueados
        View.OnClickListener listenerBloqueado = v ->
                Toast.makeText(this, "🔒 Completa el nivel anterior para desbloquear", Toast.LENGTH_SHORT).show();

        nodo5.setOnClickListener(listenerBloqueado);
        nodo6.setOnClickListener(listenerBloqueado);

        // Barra inferior
        findViewById(R.id.navHome).setOnClickListener(v ->
                Toast.makeText(this, "Ruta principal", Toast.LENGTH_SHORT).show());
        findViewById(R.id.navHeadphones).setOnClickListener(v ->
                Toast.makeText(this, "Historias / Audio", Toast.LENGTH_SHORT).show());
        findViewById(R.id.navDumbbell).setOnClickListener(v ->
                Toast.makeText(this, "Entrenamiento / Práctica", Toast.LENGTH_SHORT).show());
        findViewById(R.id.navShield).setOnClickListener(v ->
                Toast.makeText(this, "Ligas / Misiones", Toast.LENGTH_SHORT).show());
        findViewById(R.id.navTrophy).setOnClickListener(v ->
                Toast.makeText(this, "Logros y Trofeos", Toast.LENGTH_SHORT).show());
        findViewById(R.id.navDots).setOnClickListener(v ->
                Toast.makeText(this, "Más opciones", Toast.LENGTH_SHORT).show());
    }
}
