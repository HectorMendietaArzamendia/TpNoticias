package com.example.alumno.tpnoticias;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ConnectionThread extends Thread {
    private Handler handler;
    private String url;
    private int noticiasImagen;
    private int position;

    public ConnectionThread(Handler handler, String url, int textoImagen) {
        this.handler = handler;
        this.url = url;
        this.noticiasImagen = textoImagen;
    }

    public ConnectionThread(Handler handler, String url, int textoImagen, int position) {
        this.handler = handler;
        this.url = url;
        this.noticiasImagen = textoImagen;
        this.position = position;
    }

    @Override
    public void run(){
        if (noticiasImagen == MainActivity.NOTICIAS) {
            Log.d("URL", url);
            UrlConnection connection = new UrlConnection(url);
            String xml = connection.getStringRss();
            Message mensaje = new Message();
            XmlParser parser = new XmlParser();
            mensaje.obj = parser.parserXml(xml);
            mensaje.arg1 = MainActivity.NOTICIAS;
            handler.sendMessage(mensaje);
        }
        else {
            if (noticiasImagen == MainActivity.IMAGEN){
                UrlConnection connection = new UrlConnection(url);
                byte[] img = connection.getImagen();
                Message mensaje = new Message();
                mensaje.obj = img;
                mensaje.arg1 = MainActivity.IMAGEN;
                mensaje.arg2 = position;
                handler.sendMessage(mensaje);
            }
        }
    }
}
