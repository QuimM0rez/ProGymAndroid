package com.gomez.myapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RutaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnNivelClickListener {
        void onNivelClick(ElementoRuta nivel, int unidadColor, String unidadTitulo);
    }

    private final List<ElementoRuta> elementos;
    private final int nivelProgreso; // Último nivel desbloqueado (actual)
    private final OnNivelClickListener listener;

    public RutaAdapter(List<ElementoRuta> elementos, int nivelProgreso, OnNivelClickListener listener) {
        this.elementos = elementos;
        this.nivelProgreso = nivelProgreso;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return elementos.get(position).tipo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ElementoRuta.TIPO_UNIDAD) {
            View v = inflater.inflate(R.layout.item_unidad, parent, false);
            return new UnidadViewHolder(v);
        } else {
            View v = inflater.inflate(R.layout.item_nivel, parent, false);
            return new NivelViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ElementoRuta item = elementos.get(position);
        Context ctx = holder.itemView.getContext();
        float density = ctx.getResources().getDisplayMetrics().density;

        if (holder instanceof UnidadViewHolder) {
            UnidadViewHolder uvh = (UnidadViewHolder) holder;
            uvh.tvNumero.setText(ctx.getString(R.string.ruta_unidad, item.numeroUnidad));
            uvh.tvTitulo.setText(item.tituloUnidad);
            uvh.tvDesc.setText(item.descripcionUnidad);
            uvh.card.setBackground(DuolingoButtonHelper.crearBoton3D(item.colorPrimario, item.colorOscuro, 18, 6, density));
        } else if (holder instanceof NivelViewHolder) {
            NivelViewHolder nvh = (NivelViewHolder) holder;

            // Determinar color de la unidad padre
            int colorUnidad = ContextCompat.getColor(ctx, R.color.progym_primary);
            int colorUnidadOscuro = ContextCompat.getColor(ctx, R.color.progym_primary_dark);
            String unidadTitulo = "";
            for (int i = position - 1; i >= 0; i--) {
                if (elementos.get(i).tipo == ElementoRuta.TIPO_UNIDAD) {
                    colorUnidad = elementos.get(i).colorPrimario;
                    colorUnidadOscuro = elementos.get(i).colorOscuro;
                    unidadTitulo = elementos.get(i).tituloUnidad;
                    break;
                }
            }

            // Calcular estado: 0 bloqueado, 1 actual, 2 completado
            int estado;
            if (item.nivelId < nivelProgreso) {
                estado = 2; // Completado
            } else if (item.nivelId == nivelProgreso) {
                estado = 1; // Actual
            } else {
                estado = 0; // Bloqueado
            }
            item.estado = estado;

            // Zigzag Duolingo: desplazar contenedor en X
            int maxOffsetPx = (int) (64 * density);
            nvh.contenedorNodo.setTranslationX(item.desplazamientoX * maxOffsetPx);

            nvh.tvNombre.setText(item.nombreNivel);

            // Estilos según el estado
            if (estado == 2) {
                // Completado: Dorado Neón con ícono check o estrella
                int colorOro = ContextCompat.getColor(ctx, R.color.oro);
                int colorOroOsc = ContextCompat.getColor(ctx, R.color.oro_oscuro);
                nvh.btnNodo.setBackground(DuolingoButtonHelper.crearBotonCircular3D(colorOro, colorOroOsc, density));
                nvh.ivIcono.setImageResource(R.drawable.ic_check);
                nvh.ivIcono.setColorFilter(Color.WHITE);
                nvh.vAnillo.setVisibility(View.INVISIBLE);
                nvh.tvBurbuja.setVisibility(View.GONE);
            } else if (estado == 1) {
                // Actual: Color neón de la unidad con anillo y burbuja "EMPEZAR" animada
                nvh.btnNodo.setBackground(DuolingoButtonHelper.crearBotonCircular3D(colorUnidad, colorUnidadOscuro, density));
                nvh.ivIcono.setImageResource(item.iconoResId);
                nvh.ivIcono.setColorFilter(Color.WHITE);

                nvh.vAnillo.setVisibility(View.VISIBLE);
                nvh.vAnillo.setBackground(DuolingoButtonHelper.crearAnilloActual(colorUnidad, density));

                nvh.tvBurbuja.setVisibility(View.VISIBLE);
                nvh.tvBurbuja.setText(R.string.ruta_empezar);
                nvh.tvBurbuja.setTextColor(colorUnidad);

                // Animación de rebote sutil en la burbuja
                if (nvh.animadorBurbuja == null) {
                    nvh.animadorBurbuja = ObjectAnimator.ofFloat(nvh.tvBurbuja, "translationY", 0f, -8f * density, 0f);
                    nvh.animadorBurbuja.setDuration(1200);
                    nvh.animadorBurbuja.setRepeatCount(ValueAnimator.INFINITE);
                    nvh.animadorBurbuja.start();
                }
            } else {
                // Bloqueado: Gris oscuro
                int colorBloq = ContextCompat.getColor(ctx, R.color.bloqueado);
                int colorBloqOsc = ContextCompat.getColor(ctx, R.color.bloqueado_oscuro);
                int colorIcono = ContextCompat.getColor(ctx, R.color.bloqueado_icono);
                nvh.btnNodo.setBackground(DuolingoButtonHelper.crearBotonCircular3D(colorBloq, colorBloqOsc, density));
                nvh.ivIcono.setImageResource(item.iconoResId);
                nvh.ivIcono.setColorFilter(colorIcono);
                nvh.vAnillo.setVisibility(View.INVISIBLE);
                nvh.tvBurbuja.setVisibility(View.GONE);
            }

            int finalColorUnidad = colorUnidad;
            String finalUnidadTitulo = unidadTitulo;
            nvh.btnNodo.setOnClickListener(v -> {
                // Pequeño efecto al tocar
                v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(80).withEndAction(() ->
                        v.animate().scaleX(1f).scaleY(1f).setDuration(80).withEndAction(() -> {
                            if (listener != null) {
                                listener.onNivelClick(item, finalColorUnidad, finalUnidadTitulo);
                            }
                        }).start()
                ).start();
            });
        }
    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }

    static class UnidadViewHolder extends RecyclerView.ViewHolder {
        LinearLayout card;
        TextView tvNumero, tvTitulo, tvDesc;

        UnidadViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardUnidad);
            tvNumero = itemView.findViewById(R.id.tvUnidadNumero);
            tvTitulo = itemView.findViewById(R.id.tvUnidadTitulo);
            tvDesc = itemView.findViewById(R.id.tvUnidadDesc);
        }
    }

    static class NivelViewHolder extends RecyclerView.ViewHolder {
        LinearLayout contenedorNodo;
        TextView tvBurbuja, tvNombre;
        FrameLayout btnNodo;
        ImageView ivIcono;
        View vAnillo;
        ObjectAnimator animadorBurbuja;

        NivelViewHolder(@NonNull View itemView) {
            super(itemView);
            contenedorNodo = itemView.findViewById(R.id.contenedorNodo);
            tvBurbuja = itemView.findViewById(R.id.tvBurbuja);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            btnNodo = itemView.findViewById(R.id.btnNodo);
            ivIcono = itemView.findViewById(R.id.ivIcono);
            vAnillo = itemView.findViewById(R.id.vAnillo);
        }
    }
}
