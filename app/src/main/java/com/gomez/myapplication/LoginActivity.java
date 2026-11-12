package com.gomez.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Pantalla de inicio de sesión de PRO GYM.
 * Conceptos usados: Activity, findViewById, eventos onClick, validaciones,
 * TextWatcher, SharedPreferences, Intent, Toast y animaciones.
 */
public class LoginActivity extends AppCompatActivity {

    // Nombre del archivo de preferencias y sus claves
    public static final String PREFS = "progym_prefs";
    public static final String KEY_SESION = "sesion_activa";
    public static final String KEY_CORREO = "correo";
    public static final String KEY_RECORDAR = "recordar";

    // Usuario de prueba (mientras se conecta la base de datos / API)
    private static final String CORREO_DEMO = "admin@progym.com";
    private static final String CONTRASENA_DEMO = "123456";

    private TextInputLayout tilCorreo, tilContrasena;
    private TextInputEditText etCorreo, etContrasena;
    private MaterialCheckBox cbRecordarme;
    private Button btnLogin;
    private ProgressBar pbCargando;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        // Si ya inició sesión antes, va directo al inicio
        if (prefs.getBoolean(KEY_SESION, false)) {
            irAlInicio(prefs.getString(KEY_CORREO, ""));
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        enlazarVistas();
        cargarCorreoGuardado();
        configurarEventos();
        animarEntrada();
    }

    private void enlazarVistas() {
        tilCorreo = findViewById(R.id.tilCorreo);
        tilContrasena = findViewById(R.id.tilContrasena);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        cbRecordarme = findViewById(R.id.cbRecordarme);
        btnLogin = findViewById(R.id.btnLogin);
        pbCargando = findViewById(R.id.pbCargando);
    }

    /** Si el usuario marcó "Recordarme", se rellena el correo. */
    private void cargarCorreoGuardado() {
        if (prefs.getBoolean(KEY_RECORDAR, false)) {
            etCorreo.setText(prefs.getString(KEY_CORREO, ""));
            cbRecordarme.setChecked(true);
        }
    }

    private void configurarEventos() {
        btnLogin.setOnClickListener(v -> iniciarSesion());

        // Presionar "Listo" en el teclado también inicia sesión
        etContrasena.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                iniciarSesion();
                return true;
            }
            return false;
        });

        // Quita el mensaje de error mientras el usuario escribe
        etCorreo.addTextChangedListener(new LimpiarError(tilCorreo));
        etContrasena.addTextChangedListener(new LimpiarError(tilContrasena));

        TextView tvOlvide = findViewById(R.id.tvOlvide);
        TextView tvRegistrate = findViewById(R.id.tvRegistrate);
        tvOlvide.setOnClickListener(v -> mostrarToast(getString(R.string.msg_proximamente)));
        tvRegistrate.setOnClickListener(v -> mostrarToast(getString(R.string.msg_proximamente)));
    }

    /** Valida los campos y simula la autenticación. */
    private void iniciarSesion() {
        String correo = texto(etCorreo);
        String contrasena = texto(etContrasena);

        if (!validar(correo, contrasena)) return;

        mostrarCargando(true);

        // Simula una petición al servidor (1.2 segundos)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            mostrarCargando(false);

            if (correo.equalsIgnoreCase(CORREO_DEMO) && contrasena.equals(CONTRASENA_DEMO)) {
                guardarSesion(correo);
                mostrarToast(getString(R.string.msg_bienvenida));
                irAlInicio(correo);
            } else {
                tilContrasena.setError(getString(R.string.error_credenciales));
                sacudir(findViewById(R.id.cardLogin));
            }
        }, 1200);
    }

    private boolean validar(String correo, String contrasena) {
        boolean valido = true;

        if (correo.isEmpty()) {
            tilCorreo.setError(getString(R.string.error_correo_vacio));
            valido = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilCorreo.setError(getString(R.string.error_correo_invalido));
            valido = false;
        }

        if (contrasena.isEmpty()) {
            tilContrasena.setError(getString(R.string.error_contrasena_vacia));
            valido = false;
        } else if (contrasena.length() < 6) {
            tilContrasena.setError(getString(R.string.error_contrasena_corta));
            valido = false;
        }

        if (!valido) sacudir(findViewById(R.id.cardLogin));
        return valido;
    }

    private void guardarSesion(String correo) {
        prefs.edit()
                .putBoolean(KEY_SESION, true)
                .putBoolean(KEY_RECORDAR, cbRecordarme.isChecked())
                .putString(KEY_CORREO, correo)
                .apply();
    }

    private void irAlInicio(String correo) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(KEY_CORREO, correo);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish(); // Para que "atrás" no regrese al login
    }

    // ---------- Utilidades de interfaz ----------

    private void mostrarCargando(boolean cargando) {
        pbCargando.setVisibility(cargando ? View.VISIBLE : View.GONE);
        btnLogin.setText(cargando ? "" : getString(R.string.login_boton));
        btnLogin.setEnabled(!cargando);
    }

    private void animarEntrada() {
        int[] ids = {R.id.ivLogo, R.id.tvTitulo, R.id.tvSubtitulo, R.id.cardLogin, R.id.tvFrase};
        for (int i = 0; i < ids.length; i++) {
            View v = findViewById(ids[i]);
            v.setAlpha(0f);
            v.setTranslationY(60f);
            v.animate().alpha(1f).translationY(0f)
                    .setStartDelay(120L * i).setDuration(500).start();
        }
    }

    private void sacudir(View v) {
        v.animate().translationX(18f).setDuration(50).withEndAction(() ->
                v.animate().translationX(-18f).setDuration(50).withEndAction(() ->
                        v.animate().translationX(10f).setDuration(50).withEndAction(() ->
                                v.animate().translationX(0f).setDuration(50).start()
                        ).start()
                ).start()
        ).start();
    }

    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private String texto(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    /** TextWatcher reutilizable que borra el error del campo. */
    private static class LimpiarError implements TextWatcher {
        private final TextInputLayout til;

        LimpiarError(TextInputLayout til) { this.til = til; }

        @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) { }
        @Override public void onTextChanged(CharSequence s, int a, int b, int c) { til.setError(null); }
        @Override public void afterTextChanged(Editable s) { }
    }
}
