package com.example.alumno.tpnoticias;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

public class SetupDialog extends DialogFragment {
    private IListener mainListener;

    public void setMain(IListener mainListener) {
        this.mainListener = mainListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getResources().getText(R.string.setup));
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_layout, null);
        builder.setView(v);
        MenuListener listener = new MenuListener();
        listener.setMainListener(mainListener);
        MainActivity.layout = v.findViewById(R.id.checkLayout);
        for (View vi: MainActivity.layout.getTouchables()){
            CheckBox cb = (CheckBox) vi;
            String url = cb.getText().toString();
            cb.setChecked(MainActivity.pref.getBoolean(url, true));
        }
        builder.setNegativeButton("Cancelar", listener);
        builder.setPositiveButton("Aceptar", listener);
        return builder.create();
    }
}
