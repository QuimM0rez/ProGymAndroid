package com.gomez.myapplication;

public class ElementoRuta {
    public static final int TIPO_UNIDAD = 0;
    public static final int TIPO_NIVEL = 1;

    public int tipo;

    // Campos de Unidad
    public int numeroUnidad;
    public String tituloUnidad;
    public String descripcionUnidad;
    public int colorPrimario;
    public int colorOscuro;

    // Campos de Nivel
    public int nivelId;
    public String nombreNivel;
    public String descripcionNivel;
    public int iconoResId;
    public int xp;
    public int estado; // 0 = Bloqueado, 1 = Actual (activo), 2 = Completado
    public float desplazamientoX; // -1 (izq), 0 (centro), +1 (der) para zigzag

    public static ElementoRuta crearUnidad(int num, String titulo, String desc, int colorPri, int colorOsc) {
        ElementoRuta e = new ElementoRuta();
        e.tipo = TIPO_UNIDAD;
        e.numeroUnidad = num;
        e.tituloUnidad = titulo;
        e.descripcionUnidad = desc;
        e.colorPrimario = colorPri;
        e.colorOscuro = colorOsc;
        return e;
    }

    public static ElementoRuta crearNivel(int id, String nombre, String desc, int icono, int xp, float offset) {
        ElementoRuta e = new ElementoRuta();
        e.tipo = TIPO_NIVEL;
        e.nivelId = id;
        e.nombreNivel = nombre;
        e.descripcionNivel = desc;
        e.iconoResId = icono;
        e.xp = xp;
        e.desplazamientoX = offset;
        e.estado = 0; // Se calcula en el adaptador según progreso
        return e;
    }
}
