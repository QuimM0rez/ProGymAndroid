package com.gomez.myapplication;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;

/**
 * Genera drawables con efecto de botón 3D con borde y sombra inferior
 * característicos de Duolingo (con la paleta neón de PRO GYM).
 */
public class DuolingoButtonHelper {

    public static Drawable crearBoton3D(int colorFondo, int colorSombra, int radioDp, int grosorSombraDp, float density) {
        int r = (int) (radioDp * density);
        int sombra = (int) (grosorSombraDp * density);

        // Capa inferior (Sombra 3D)
        GradientDrawable capaInferior = new GradientDrawable();
        capaInferior.setShape(GradientDrawable.RECTANGLE);
        capaInferior.setCornerRadius(r);
        capaInferior.setColor(colorSombra);

        // Capa superior (Frente del botón)
        GradientDrawable capaSuperior = new GradientDrawable();
        capaSuperior.setShape(GradientDrawable.RECTANGLE);
        capaSuperior.setCornerRadius(r);
        capaSuperior.setColor(colorFondo);

        LayerDrawable ld = new LayerDrawable(new Drawable[]{capaInferior, capaSuperior});
        // La capa superior deja ver 'sombra' píxeles de la inferior abajo
        ld.setLayerInset(1, 0, 0, 0, sombra);
        return ld;
    }

    public static Drawable crearBotonCircular3D(int colorFondo, int colorSombra, float density) {
        int sombra = (int) (7 * density);

        GradientDrawable capaInferior = new GradientDrawable();
        capaInferior.setShape(GradientDrawable.OVAL);
        capaInferior.setColor(colorSombra);

        GradientDrawable capaSuperior = new GradientDrawable();
        capaSuperior.setShape(GradientDrawable.OVAL);
        capaSuperior.setColor(colorFondo);

        LayerDrawable ld = new LayerDrawable(new Drawable[]{capaInferior, capaSuperior});
        ld.setLayerInset(1, 0, 0, 0, sombra);
        return ld;
    }

    public static Drawable crearAnilloActual(int colorAnillo, float density) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(Color.TRANSPARENT);
        gd.setStroke((int) (4 * density), colorAnillo);
        return gd;
    }
}
