package com.example.alumno.tpnoticias;

import java.util.List;

public interface IListener {
    void mostrarWeb(int position);
    void modificarLista(List<Noticia> news);
    void actualizarVista();
}
