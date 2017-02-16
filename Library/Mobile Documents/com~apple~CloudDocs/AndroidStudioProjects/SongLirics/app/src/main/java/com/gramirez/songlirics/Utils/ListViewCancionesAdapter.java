package com.gramirez.songlirics.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gramirez.songlirics.Cancion;
import com.gramirez.songlirics.R;

import java.util.ArrayList;

/**
 * Created by Gilbert Ramírez on 15/08/2016.
 */
public class ListViewCancionesAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Cancion> canciones;

    public ListViewCancionesAdapter(Context context, ArrayList<Cancion> lista) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        canciones = lista;
    }

    @Override
    public int getCount() {
        return canciones.size();
    }

    @Override
    public Object getItem(int position) {
        return canciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return canciones.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.canciones_listview_row, null);
        }
        Cancion cancion = canciones.get(position);
        TextView tvNumero = (TextView) convertView.findViewById(R.id.tvNumero);
        TextView tvNombreCancion = (TextView) convertView.findViewById(R.id.tvNombreCancion);
        TextView tvNombreArtista = (TextView) convertView.findViewById(R.id.tvNombreArtista);

        tvNumero.setText(String.valueOf(position+1));
        tvNombreCancion.setText(cancion.getNombreCancion());
        tvNombreArtista.setText(cancion.getArtista());
        return convertView;
    }
}
