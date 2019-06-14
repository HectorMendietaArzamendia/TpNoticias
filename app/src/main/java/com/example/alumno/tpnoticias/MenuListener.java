package com.example.alumno.tpnoticias;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import java.util.LinkedList;
import java.util.List;

public class MenuListener implements SearchView.OnQueryTextListener, DialogInterface.OnClickListener {
    private IListener mainListener;
    private List<Noticia> noticias;
    private List<Noticia> auxNoticias = new LinkedList<>();

    public MenuListener() {}

    public MenuListener(IListener mainListener, List<Noticia> noticias) {
        this.mainListener = mainListener;
        this.noticias = noticias;
    }

    public void setMainListener(IListener mainListener) {
        this.mainListener = mainListener;
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
        mainListener.modificarLista(auxNoticias);
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
            mainListener.modificarLista(auxNoticias);
        }
        return false;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (which == AlertDialog.BUTTON_POSITIVE){
            SharedPreferences.Editor editor = MainActivity.pref.edit();
            for (View v: MainActivity.layout.getTouchables()){
                CheckBox cb = (CheckBox) v;
                editor.putBoolean(cb.getText().toString(), cb.isChecked());
            }
            editor.commit();
            mainListener.actualizarVista();
        }
    }
}
