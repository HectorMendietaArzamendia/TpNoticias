package com.example.alumno.tpnoticias;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;

public class MenuListener implements SearchView.OnQueryTextListener, DialogInterface.OnClickListener {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (which == AlertDialog.BUTTON_POSITIVE){}
    }
}
