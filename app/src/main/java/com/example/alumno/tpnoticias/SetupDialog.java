package com.example.alumno.tpnoticias;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class SetupDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getText(R.string.setup));
        builder.setView(LayoutInflater.from(getActivity()).inflate(R.layout.dialog_layout, null));
        MenuListener listener = new MenuListener();
        builder.setNegativeButton("Cancelar", listener);
        builder.setPositiveButton("Aceptar", listener);
        return builder.create();
    }
}
