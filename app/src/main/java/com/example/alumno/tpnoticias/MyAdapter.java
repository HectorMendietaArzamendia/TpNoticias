package com.example.alumno.tpnoticias;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter <MyViewHolder>{
    private IListener listener;
    private List<Noticia> noticias;
    private Handler handler;
    private Locale espArg = new Locale("es", "AR");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EE, d MMMM yyyy", espArg);

    public MyAdapter(IListener listener, List<Noticia> noticias, Handler handler) {
        this.listener = listener;
        this.noticias = noticias;
        this.handler = handler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticia_layout, parent, false);
        return new MyViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Noticia noticia = noticias.get(position);
        holder.setPosition(position);

        holder.tvFecha.setText(dateFormat.format(noticia.getFecha()));

        String titulo = noticia.getTitulo();
        if (titulo.length() > 50){
            holder.tvTitulo.setText(titulo.substring(0, 50).concat("..."));
        }
        else { holder.tvTitulo.setText(titulo); }

        String descripcion = noticia.getDescripcion();
        if (descripcion != null){
            if (descripcion.length() > 50){
                holder.tvDescripcion.setText(descripcion.substring(0, 50).concat("..."));
            }
            else { holder.tvDescripcion.setText(descripcion); }
        }

        String link = noticia.getLink();
        if (link != null){
            if (link.length() > 50){
                holder.tvLink.setText(link.substring(0, 50).concat("..."));
            }
            else { holder.tvLink.setText(link); }
        }

        byte[] img = noticia.getImagen();
        if (img != null) {
            holder.ivImagen.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.length));
        }
        else {
            String urlImagen = noticia.getUrlImagen();
            if (urlImagen != null){
                ConnectionThread connection = new ConnectionThread(handler, urlImagen, MainActivity.IMAGEN, position);
                connection.start();
            }
        }
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }
}
