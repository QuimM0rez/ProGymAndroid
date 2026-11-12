package com.gomez.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RankingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ranking, container, false);

        SharedPreferences prefs = requireActivity().getSharedPreferences(LoginActivity.PREFS, Context.MODE_PRIVATE);
        String correo = prefs.getString(LoginActivity.KEY_CORREO, "Darlinson");
        String nombre = correo.contains("@") ? correo.substring(0, correo.indexOf('@')) : correo;
        int xp = prefs.getInt("progym_xp", 210);

        TextView tvNombre = root.findViewById(R.id.tvRankingUsuario);
        TextView tvXp = root.findViewById(R.id.tvRankingXp);

        tvNombre.setText(nombre + " (Tú)");
        tvXp.setText(xp + " XP");

        return root;
    }
}
