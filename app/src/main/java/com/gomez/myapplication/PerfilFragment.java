package com.gomez.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PerfilFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        SharedPreferences prefs = requireActivity().getSharedPreferences(LoginActivity.PREFS, Context.MODE_PRIVATE);
        String correo = prefs.getString(LoginActivity.KEY_CORREO, "atleta@progym.com");
        String nombre = correo.contains("@") ? correo.substring(0, correo.indexOf('@')) : correo;
        String inicial = nombre.isEmpty() ? "P" : nombre.substring(0, 1).toUpperCase();

        int racha = prefs.getInt("progym_racha", 3);
        int xp = prefs.getInt("progym_xp", 210);
        int nivelActual = prefs.getInt("progym_nivel_actual", 4);

        TextView tvInicial = root.findViewById(R.id.tvPerfilInicial);
        TextView tvNombre = root.findViewById(R.id.tvPerfilNombre);
        TextView tvCorreo = root.findViewById(R.id.tvPerfilCorreo);
        TextView tvRacha = root.findViewById(R.id.tvPerfilRacha);
        TextView tvXp = root.findViewById(R.id.tvPerfilXp);
        TextView tvNiveles = root.findViewById(R.id.tvPerfilNiveles);
        Button btnCerrarSesion = root.findViewById(R.id.btnPerfilCerrarSesion);

        tvInicial.setText(inicial);
        tvNombre.setText(nombre);
        tvCorreo.setText(correo);
        tvRacha.setText(String.valueOf(racha));
        tvXp.setText(String.valueOf(xp));
        tvNiveles.setText(String.valueOf(Math.max(0, nivelActual - 1)));

        btnCerrarSesion.setOnClickListener(v -> {
            prefs.edit().putBoolean(LoginActivity.KEY_SESION, false).apply();
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return root;
    }
}
