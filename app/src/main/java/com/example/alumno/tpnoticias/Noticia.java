package com.example.alumno.tpnoticias;

import android.support.annotation.NonNull;

import java.util.Date;

public class Noticia implements Comparable<Noticia> {
    private Date fecha = new Date();
    private String titulo;
    private String descripcion;
    private String link;
    private String urlImagen;
    private byte[] imagen;

    public Date getFecha() { return fecha; }

    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    public String getUrlImagen() { return urlImagen; }

    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }

    public byte[] getImagen() { return imagen; }

    public void setImagen(byte[] imagen) { this.imagen = imagen; }

    @Override
    public int compareTo(@NonNull Noticia noticia) {
        return fecha.compareTo(noticia.getFecha()) * -1;
    }
}
