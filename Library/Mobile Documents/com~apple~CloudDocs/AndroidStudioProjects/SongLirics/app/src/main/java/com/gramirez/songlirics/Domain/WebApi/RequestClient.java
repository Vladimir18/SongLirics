package com.gramirez.songlirics.Domain.WebApi;

import android.os.AsyncTask;

import com.gramirez.songlirics.Domain.ComunicadorDelegate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yisel on 30/07/2016.
 */
public class RequestClient extends AsyncTask<URL, Void,BufferedReader> {
    private final String USER_AGENT = "Movil";
    ComunicadorDelegate delegate;
    Exception error;
    int requestCode;
    String resultado = "";

    public RequestClient(int requestCode, ComunicadorDelegate delegate) {
        this.requestCode = requestCode;
        this.delegate = delegate;
    }

    @Override
    protected BufferedReader doInBackground(URL... params) {
        BufferedReader bf = null;
        try {
            String inputLine;
            boolean isFound = false;
            HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bf = new BufferedReader(new InputStreamReader(conn.getInputStream(), "iso-8859-1"));
                switch (requestCode) {
                    case 1:
                        while ((inputLine = bf.readLine()) != null && !isFound) {
                            if (inputLine.contains("Letras De Canciones Más Visitadas")) {
                                isFound = true;
                                resultado = inputLine;
                            }
                        }
                        break;
                    case 2:
                        boolean isLetraFound = false;
                        while ((inputLine = bf.readLine()) != null && !isFound) {
                            if (inputLine.contains("<p><font style=line-height:20px;font-size:14px;font-family:arial,tahoma,verdana>")) {
                                isLetraFound = true;
//                                Log.i("found", "pasé por aqui: "+inputLine);
                                resultado += inputLine.substring(inputLine.lastIndexOf(">") + 1 );
                                continue;
                            }
                            if (isLetraFound) {
                                if (inputLine.contains("</font></p></td><td width=160>")) {
                                    isLetraFound = false;
                                    isFound = true;
                                    resultado += inputLine.substring(0, inputLine.indexOf("</font></p><p>", 0));
                                } else {
                                    resultado += inputLine;
                                }
                            }
                        }
                        break;
                    case 3:
                        while ((inputLine = bf.readLine()) != null) {
                            resultado += inputLine;
                        }
                        break;
                }
            } else {
                StringBuilder result = new StringBuilder();
                bf = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                while ((inputLine = bf.readLine()) != null) {
                    result.append(inputLine);
                }
                bf.close();
                error = new Exception(result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            error = e;
        }
        return bf;
    }

    @Override
    protected void onPostExecute(BufferedReader result) {
        if (error == null && resultado != null) {
            delegate.onSuccess(resultado);
        } else {
            delegate.onError(error);
        }
        super.onPostExecute(result);
    }
}
