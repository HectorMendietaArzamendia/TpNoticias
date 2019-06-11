package com.example.alumno.tpnoticias;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private IListener listener;
    private int position;
    TextView tvFecha;
    TextView tvTitulo;
    TextView tvDescripcion;
    ImageView ivImagen;
    TextView tvLink;
    CardView cvNoticia;

    public MyViewHolder(View itemView, IListener listener) {
        super(itemView);
        this.listener = listener;
        tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
        tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
        tvDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcion);
        ivImagen = (ImageView) itemView.findViewById(R.id.ivImagen);
        tvLink = (TextView) itemView.findViewById(R.id.tvLink);
        cvNoticia = (CardView) itemView.findViewById(R.id.cvNoticia);
        cvNoticia.setOnClickListener(this);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View view) {
        listener.mostrarWeb(position);
    }
}
