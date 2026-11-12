package com.gomez.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment implements RutaAdapter.OnNivelClickListener {

    private static final String PREF_RACHA = "progym_racha";
    private static final String PREF_XP = "progym_xp";
    private static final String PREF_VIDAS = "progym_vidas";
    private static final String PREF_NIVEL_ACTUAL = "progym_nivel_actual";

    private TextView tvRacha, tvXp, tvVidas;
    private RecyclerView rvRuta;
    private RutaAdapter adapter;
    private SharedPreferences prefs;

    private int racha, xp, vidas, nivelActual;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);

        prefs = requireActivity().getSharedPreferences(LoginActivity.PREFS, Context.MODE_PRIVATE);
        cargarDatos();

        tvRacha = root.findViewById(R.id.tvRacha);
        tvXp = root.findViewById(R.id.tvXp);
        tvVidas = root.findViewById(R.id.tvVidas);
        rvRuta = root.findViewById(R.id.rvRuta);

        actualizarTopBar();
        configurarRecyclerView();

        return root;
    }

    private void cargarDatos() {
        racha = prefs.getInt(PREF_RACHA, 3);
        xp = prefs.getInt(PREF_XP, 210);
        vidas = prefs.getInt(PREF_VIDAS, 5);
        nivelActual = prefs.getInt(PREF_NIVEL_ACTUAL, 4); // Por defecto en nivel 4
    }

    private void guardarDatos() {
        prefs.edit()
                .putInt(PREF_RACHA, racha)
                .putInt(PREF_XP, xp)
                .putInt(PREF_VIDAS, vidas)
                .putInt(PREF_NIVEL_ACTUAL, nivelActual)
                .apply();
    }

    private void actualizarTopBar() {
        tvRacha.setText(String.valueOf(racha));
        tvXp.setText(String.valueOf(xp));
        tvVidas.setText(String.valueOf(vidas));
    }

    private void configurarRecyclerView() {
        rvRuta.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ElementoRuta> elementos = generarRutaProgym();
        adapter = new RutaAdapter(elementos, nivelActual, this);
        rvRuta.setAdapter(adapter);

        // Hace scroll suave hasta el nivel actual
        rvRuta.post(() -> {
            for (int i = 0; i < elementos.size(); i++) {
                if (elementos.get(i).tipo == ElementoRuta.TIPO_NIVEL && elementos.get(i).nivelId == nivelActual) {
                    rvRuta.smoothScrollToPosition(Math.max(0, i - 1));
                    break;
                }
            }
        });
    }

    private List<ElementoRuta> generarRutaProgym() {
        Context ctx = requireContext();
        List<ElementoRuta> lista = new ArrayList<>();

        // UNIDAD 1: Morado Neón (Pecho y Tríceps)
        int u1Pri = ContextCompat.getColor(ctx, R.color.unidad_morado);
        int u1Osc = ContextCompat.getColor(ctx, R.color.unidad_morado_oscuro);
        lista.add(ElementoRuta.crearUnidad(1, "Pecho de Acero", "Fundamentos de empuje y fuerza", u1Pri, u1Osc));
        lista.add(ElementoRuta.crearNivel(1, "Press Plano", "3 series x 10 reps con barra", R.drawable.ic_dumbbell, 15, 0.0f));
        lista.add(ElementoRuta.crearNivel(2, "Inclinado", "Aperturas con mancuernas", R.drawable.ic_dumbbell, 20, 0.6f));
        lista.add(ElementoRuta.crearNivel(3, "Fondos", "Paralelas con peso corporal", R.drawable.ic_star, 25, 0.3f));

        // UNIDAD 2: Cian Neón (Espalda y Bíceps)
        int u2Pri = ContextCompat.getColor(ctx, R.color.unidad_cian);
        int u2Osc = ContextCompat.getColor(ctx, R.color.unidad_cian_oscuro);
        lista.add(ElementoRuta.crearUnidad(2, "Espalda Imparable", "Dominadas y densidad dorsal", u2Pri, u2Osc));
        lista.add(ElementoRuta.crearNivel(4, "Dominadas", "Dominadas pronas al fallo técnico", R.drawable.ic_star, 30, -0.4f));
        lista.add(ElementoRuta.crearNivel(5, "Remo con Barra", "Tracción explosiva 4 x 8 reps", R.drawable.ic_dumbbell, 25, -0.7f));
        lista.add(ElementoRuta.crearNivel(6, "Jalón al Pecho", "Enfoque en dorsal ancho", R.drawable.ic_dumbbell, 20, 0.0f));
        lista.add(ElementoRuta.crearNivel(7, "Curl Martillo", "Pico de bíceps y antebrazo", R.drawable.ic_trophy, 35, 0.5f));

        // UNIDAD 3: Naranja Fuego (Pierna y Potencia)
        int u3Pri = ContextCompat.getColor(ctx, R.color.unidad_naranja);
        int u3Osc = ContextCompat.getColor(ctx, R.color.unidad_naranja_oscuro);
        lista.add(ElementoRuta.crearUnidad(3, "Día de Pierna", "Construye tu base sólida", u3Pri, u3Osc));
        lista.add(ElementoRuta.crearNivel(8, "Sentadilla", "Sentadilla trasera profunda", R.drawable.ic_dumbbell, 30, 0.2f));
        lista.add(ElementoRuta.crearNivel(9, "Prensa 45°", "Fuerza máxima en cuádriceps", R.drawable.ic_dumbbell, 25, -0.5f));
        lista.add(ElementoRuta.crearNivel(10, "Peso Muerto", "Cadena posterior implacable", R.drawable.ic_fire, 40, 0.0f));

        // UNIDAD 4: Rosa Neón (Cardio y Desafío Final)
        int u4Pri = ContextCompat.getColor(ctx, R.color.unidad_rosa);
        int u4Osc = ContextCompat.getColor(ctx, R.color.unidad_rosa_oscuro);
        lista.add(ElementoRuta.crearUnidad(4, "Cardio HIIT & Core", "Quema grasa y define tu abdomen", u4Pri, u4Osc));
        lista.add(ElementoRuta.crearNivel(11, "Sprints 100m", "Intervalos de alta intensidad", R.drawable.ic_run, 30, 0.6f));
        lista.add(ElementoRuta.crearNivel(12, "Plancha 3 Min", "Estabilidad central absoluta", R.drawable.ic_bolt, 25, 0.0f));
        lista.add(ElementoRuta.crearNivel(13, "Examen PRO GYM", "Prueba de titán: todas las zonas", R.drawable.ic_trophy, 50, -0.4f));

        return lista;
    }

    @Override
    public void onNivelClick(ElementoRuta nivel, int unidadColor, String unidadTitulo) {
        if (nivel.estado == 0) {
            Toast.makeText(getContext(), R.string.ruta_bloqueado, Toast.LENGTH_SHORT).show();
            return;
        }

        mostrarBottomSheetNivel(nivel, unidadColor, unidadTitulo);
    }

    private void mostrarBottomSheetNivel(ElementoRuta nivel, int unidadColor, String unidadTitulo) {
        if (getContext() == null) return;

        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View sheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_nivel, null);
        sheet.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_card));

        ImageView ivIcono = sheet.findViewById(R.id.ivHojaIcono);
        TextView tvUnidad = sheet.findViewById(R.id.tvHojaUnidad);
        TextView tvTitulo = sheet.findViewById(R.id.tvHojaTitulo);
        TextView tvXp = sheet.findViewById(R.id.tvHojaXp);
        TextView tvDesc = sheet.findViewById(R.id.tvHojaDescripcion);
        Button btnEmpezar = sheet.findViewById(R.id.btnEmpezar);

        ivIcono.setImageResource(nivel.iconoResId);
        ivIcono.setColorFilter(unidadColor);

        tvUnidad.setText(unidadTitulo);
        tvUnidad.setTextColor(unidadColor);

        tvTitulo.setText(nivel.nombreNivel);
        tvXp.setText(getString(R.string.nivel_xp, nivel.xp));
        tvDesc.setText(nivel.descripcionNivel);

        float density = getResources().getDisplayMetrics().density;
        btnEmpezar.setBackground(DuolingoButtonHelper.crearBoton3D(unidadColor, ContextCompat.getColor(requireContext(), R.color.progym_primary_dark), 28, 6, density));

        if (nivel.estado == 2) {
            btnEmpezar.setText(getString(R.string.nivel_practicar, nivel.xp));
        } else {
            btnEmpezar.setText(getString(R.string.nivel_empezar, nivel.xp));
        }

        btnEmpezar.setOnClickListener(v -> {
            dialog.dismiss();
            completarNivel(nivel);
        });

        dialog.setContentView(sheet);
        dialog.show();
    }

    private void completarNivel(ElementoRuta nivel) {
        xp += nivel.xp;
        if (nivel.estado == 1) { // Solo si era el nivel actual, avanza la ruta
            nivelActual++;
            racha++;
        }
        guardarDatos();
        actualizarTopBar();
        configurarRecyclerView();

        Toast.makeText(getContext(), getString(R.string.nivel_completado, nivel.xp), Toast.LENGTH_LONG).show();
    }
}
