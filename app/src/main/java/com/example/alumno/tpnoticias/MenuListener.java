package com.example.alumno.tpnoticias;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;

import java.util.LinkedList;
import java.util.List;

public class MenuListener implements SearchView.OnQueryTextListener, DialogInterface.OnClickListener {
    private IListener main;
    private List<Noticia> noticias;
    private List<Noticia> auxNoticias = new LinkedList<>();

    public MenuListener() {}

    public MenuListener(IListener main, List<Noticia> noticias) {
        this.main = main;
        this.noticias = noticias;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        auxNoticias.clear();
        query = query.toLowerCase();
        for (Noticia n : noticias){
            if (n.getTitulo().toLowerCase().contains(query)){
                auxNoticias.add(n);
            }
        }
        main.modificarLista(auxNoticias);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() >= 3){
            auxNoticias.clear();
            newText = newText.toLowerCase();
            for (Noticia n : noticias){
                if (n.getTitulo().toLowerCase().contains(newText)){
                    auxNoticias.add(n);
                }
            }
            main.modificarLista(auxNoticias);
        }
        return false;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (which == AlertDialog.BUTTON_POSITIVE){
           // MainActivity.actualizar("");
        }
    }
}
