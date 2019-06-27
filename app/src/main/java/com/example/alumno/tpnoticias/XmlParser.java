package com.example.alumno.tpnoticias;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class XmlParser {
    private List<Noticia> noticias = new LinkedList<>();
    private Noticia noticia = null;

    public List<Noticia> parserXml(String xml){
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(xml));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT){
                if (event == XmlPullParser.START_TAG){
                    String tagName = parser.getName();
                    if ("item".equals(tagName)){
                        noticia = new Noticia();
                    }
                    if ("title".equals(tagName)){
                        if (noticia != null) noticia.setTitulo(parser.nextText());
                    }
                    if ("description".equals(tagName)){
                        if (noticia != null) noticia.setDescripcion(parser.nextText().replace("| ", ""));
                    }
                    if ("link".equals(tagName)){
                        String link = parser.nextText();
                        if (noticia != null && link != null){
                            if (!link.contains("http")){ noticia.setLink("http://www.telam.com.ar".concat(link)); }
                            else { noticia.setLink(link);}
                        }
                    }
                    if ("pubDate".equals(tagName)){
                        if (noticia != null) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EE, d MMM yyyy HH:mm:ss z", Locale.US);
                            noticia.setFecha(dateFormat.parse(parser.nextText()));
                        }
                    }
                    if ("enclosure".equals(tagName)){
                        if (noticia != null) noticia.setUrlImagen(parser.getAttributeValue(null, "url"));
                    }
                    if ("url".equals(tagName)){ //Cronica
                        if (noticia != null) noticia.setUrlImagen(parser.nextText());
                    }
                }
                if (event == XmlPullParser.END_TAG){
                    if ("item".equals(parser.getName())){
                        if (noticia != null) noticias.add(noticia);
                    }
                }
                event = parser.next();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return noticias;
    }
}
