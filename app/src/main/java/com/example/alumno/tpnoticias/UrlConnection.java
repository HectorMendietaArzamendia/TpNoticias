package com.example.alumno.tpnoticias;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnection {
    private String strUrl;

    public UrlConnection(String strUrl) {
        this.strUrl = strUrl;
    }

    public String getStringRss(){
        URL url = null;
        try{
            url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            if (response == 200){
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = is.read(buffer)) != -1){
                    baos.write(buffer, 0, length);
                }
                is.close();
                return new String(baos.toByteArray(), "UTF-8");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getImagen(){
        URL url = null;
        try{
            url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            if (response == 200){
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = is.read(buffer)) != -1){
                    baos.write(buffer, 0, length);
                }
                is.close();
                return baos.toByteArray();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
