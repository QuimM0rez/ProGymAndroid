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

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS = "progym_prefs";
    public static final String KEY_SESION = "sesion_activa";
    public static final String KEY_CORREO = "correo";
    public static final String KEY_RECORDAR = "recordar";

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

        // Si ya hay sesión activa, va directo al menú principal
        if (prefs.getBoolean(KEY_SESION, false)) {
            irAlMenuPrincipal(prefs.getString(KEY_CORREO, "atleta@progym.com"));
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

    private void cargarCorreoGuardado() {
        if (prefs.getBoolean(KEY_RECORDAR, false)) {
            etCorreo.setText(prefs.getString(KEY_CORREO, ""));
            cbRecordarme.setChecked(true);
        }
    }

    private void configurarEventos() {
        btnLogin.setOnClickListener(v -> iniciarSesion());

        etContrasena.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                iniciarSesion();
                return true;
            }
            return false;
        });

        etCorreo.addTextChangedListener(new LimpiarError(tilCorreo));
        etContrasena.addTextChangedListener(new LimpiarError(tilContrasena));

        TextView tvOlvide = findViewById(R.id.tvOlvide);
        TextView tvRegistrate = findViewById(R.id.tvRegistrate);
        tvOlvide.setOnClickListener(v -> Toast.makeText(this, R.string.msg_proximamente, Toast.LENGTH_SHORT).show());
        tvRegistrate.setOnClickListener(v -> Toast.makeText(this, R.string.msg_proximamente, Toast.LENGTH_SHORT).show());
    }

    private void iniciarSesion() {
        String correo = texto(etCorreo);
        String contrasena = texto(etContrasena);

        if (!validar(correo, contrasena)) return;

        mostrarCargando(true);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            mostrarCargando(false);

            if (correo.equalsIgnoreCase(CORREO_DEMO) && contrasena.equals(CONTRASENA_DEMO)) {
                guardarSesion(correo);
                Toast.makeText(this, R.string.msg_bienvenida, Toast.LENGTH_SHORT).show();
                irAlMenuPrincipal(correo);
            } else {
                tilContrasena.setError(getString(R.string.error_credenciales));
                sacudir(findViewById(R.id.cardLogin));
            }
        }, 1000);
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

    private void irAlMenuPrincipal(String correo) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(KEY_CORREO, correo);
        startActivity(intent);
        finish();
    }

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
            v.setTranslationY(50f);
            v.animate().alpha(1f).translationY(0f)
                    .setStartDelay(100L * i).setDuration(450).start();
        }
    }

    private void sacudir(View v) {
        v.animate().translationX(16f).setDuration(50).withEndAction(() ->
                v.animate().translationX(-16f).setDuration(50).withEndAction(() ->
                        v.animate().translationX(8f).setDuration(50).withEndAction(() ->
                                v.animate().translationX(0f).setDuration(50).start()
                        ).start()
                ).start()
        ).start();
    }

    private String texto(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    private static class LimpiarError implements TextWatcher {
        private final TextInputLayout til;
        LimpiarError(TextInputLayout til) { this.til = til; }
        @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) { }
        @Override public void onTextChanged(CharSequence s, int a, int b, int c) { til.setError(null); }
        @Override public void afterTextChanged(Editable s) { }
    }
}
