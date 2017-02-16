package com.gramirez.songlirics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.gramirez.songlirics.Domain.Canciones;
import com.gramirez.songlirics.Domain.ComunicadorDelegate;

import java.net.MalformedURLException;

public class VerCancionActivity extends AppCompatActivity {
    TextView tvNombreCancion, tvLetraCancion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cancion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvNombreCancion = (TextView) findViewById(R.id.tvTituloCancion);
        tvLetraCancion = (TextView) findViewById(R.id.tvLetraCancion);
        Bundle data = getIntent().getExtras();
        tvNombreCancion.setText(data.getString("nombreCancion"));
        try {
            new Canciones(this).buscarLetraCancion(data.getString("url"), new ComunicadorDelegate() {
                @Override
                public void onError(Exception e) {
                    Toast.makeText(VerCancionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(Object object) {
                    tvLetraCancion.setText(((String) object).replace("<br>", "\n"));
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(VerCancionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
