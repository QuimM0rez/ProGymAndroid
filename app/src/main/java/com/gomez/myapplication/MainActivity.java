package com.gomez.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Pantalla principal con navegación estilo Duolingo:
 * - Inicio: Ruta de entrenamiento en zigzag (niveles completados, actual con rebote, bloqueados)
 * - Misiones: Retos y metas diarias
 * - Ranking: Tabla de posiciones de la liga con tus compañeros (Quim, Dustin, Sebastian)
 * - Perfil: Estadísticas de racha, XP, niveles superados y cierre de sesión
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // La barra inferior se maneja sola
            return insets;
        });

        bottomNav = findViewById(R.id.bottomNav);
        configurarNavegacion();

        // Carga la pantalla de Inicio por defecto
        if (savedInstanceState == null) {
            cambiarFragmento(new InicioFragment());
        }
    }

    private void configurarNavegacion() {
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragmento = null;

            if (id == R.id.nav_inicio) {
                fragmento = new InicioFragment();
            } else if (id == R.id.nav_misiones) {
                fragmento = new MisionesFragment();
            } else if (id == R.id.nav_ranking) {
                fragmento = new RankingFragment();
            } else if (id == R.id.nav_perfil) {
                fragmento = new PerfilFragment();
            }

            if (fragmento != null) {
                cambiarFragmento(fragmento);
                return true;
            }
            return false;
        });
    }

    private void cambiarFragmento(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorFragmentos, fragment)
                .commit();
    }
}
