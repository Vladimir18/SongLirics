package com.gramirez.songlirics;

/**
 * Created by Yisel on 27/03/2016.
 */
public class Cancion {
    private String artista, nombreCancion, letra;
    private int id;

    public Cancion(int id, String artista, String nombreCancion, String letra) {
        this.id = id;
        this.artista = artista;
        this.nombreCancion = nombreCancion;
        this.letra = letra;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
