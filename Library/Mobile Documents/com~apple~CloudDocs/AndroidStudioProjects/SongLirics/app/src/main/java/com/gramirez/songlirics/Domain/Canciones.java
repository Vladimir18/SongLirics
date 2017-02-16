package com.gramirez.songlirics.Domain;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.gramirez.songlirics.Cancion;
import com.gramirez.songlirics.Domain.WebApi.RequestClient;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Yisel on 31/07/2016.
 */
public class Canciones {
    Context context;

    public Canciones(Context context) {
        this.context = context;
    }

    public void buscarTopCanciones(final ComunicadorDelegate delegate) throws MalformedURLException {
        URL url = new URL("http://www.musica.com/letras.asp?letras=canciones");
        new RequestClient(1, new ComunicadorDelegate() {
            @Override
            public void onError(Exception e) {
                delegate.onError(e);
            }

            @Override
            public void onSuccess(Object object) {
                ArrayList<Cancion> canciones = new ArrayList<>();
                String inputLine = (String) object;
                String[] letras = inputLine.split("::");
                for (int i = 1; i < letras.length; i++) {
                    String[] artista = letras[i].split("Letras de");
                    String[] nombreArtista = artista[1].split("</a>");
                    String[] nombre = letras[i].split("- ");
                    String[] nombreCancion = nombre[1].split("</a>");
                    String[] enlace = letras[i].split("<a href=.");
                    String[] enlacelink = enlace[1].split(".>");
                    canciones.add(new Cancion(i, nombreArtista[0], nombreCancion[0], "http://www.musica.com/" +enlacelink[0]));
                }
                delegate.onSuccess(canciones);
            }
        }).execute(url);
    }

    public void buscarLetraCancion(String urlCancion, final ComunicadorDelegate delegate) throws MalformedURLException {
        URL url = new URL(urlCancion);
        new RequestClient(2, new ComunicadorDelegate() {
            @Override
            public void onError(Exception e) {
                delegate.onError(e);
            }

            @Override
            public void onSuccess(Object object) {
                delegate.onSuccess(object);
            }
        }).execute(url);
    }

    public void buscarCancion(String nombreCancion, final ComunicadorDelegate delegate) throws MalformedURLException, UnsupportedEncodingException {
        URL url = new URL("http://www.musica.com/letras.asp?q="+ URLEncoder.encode(nombreCancion, "UTF-8") +"&ref=si");
        new RequestClient(3, new ComunicadorDelegate() {
            @Override
            public void onError(Exception e) {
                delegate.onError(e);
            }

            @Override
            public void onSuccess(Object object) {
                WebView webView = new WebView(context);
                ValueCallback<String> valueCallback = new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.i("value", String.valueOf(value));
                    }
                };
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript((String) object, valueCallback);
                }
                Log.i("busqueda", (String) object);
                delegate.onSuccess(object);
            }
        }).execute(url);
    }
}
