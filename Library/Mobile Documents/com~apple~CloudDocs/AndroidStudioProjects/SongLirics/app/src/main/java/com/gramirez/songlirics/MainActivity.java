package com.gramirez.songlirics;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.gramirez.songlirics.Model.MenuItem;
import com.gramirez.songlirics.Utils.ListViewCancionesAdapter;
import com.gramirez.songlirics.Utils.NavDrawerListAdapter;
import com.gramirez.songlirics.Utils.ScrimInsetsFrameLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListViewCancionesAdapter adapter;
    ListView listView, navDrawerListView;
    private ImageButton btnBuscar;
    private ScrimInsetsFrameLayout sifl;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    NavDrawerListAdapter drawerListAdapter;
    private EditText etNombreCancion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

//        etNombreCancion = (EditText) findViewById(R.id.etNombreCancion);
//        btnBuscar = (ImageButton) findViewById(R.id.btnBuscar);
        listView = (ListView) findViewById(R.id.listView);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        sifl = (ScrimInsetsFrameLayout) findViewById(R.id.scrimInsetsFrameLayout);
        navDrawerListView = (ListView) findViewById(R.id.navdrawerlist);

        final ArrayList<MenuItem> array = new ArrayList<>();
        array.add(new MenuItem("First", false));
        array.add(new MenuItem("Second", false));

        drawerListAdapter = new NavDrawerListAdapter(array, getLayoutInflater());
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, array);
        navDrawerListView.setAdapter(drawerListAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

       /* URL url = null;
//        btnBuscar.setOnClickListener(this);
        try {
            new Canciones(this).buscarTopCanciones(new ComunicadorDelegate() {
                @Override
                public void onError(Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(Object object) {
                    adapter = new ListViewCancionesAdapter(MainActivity.this, (ArrayList<Cancion>)object);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(MainActivity.this);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        navDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new FirstFragment();
                        break;
                    case 1:
                        fragment = new SecondFragment();
                        break;
                }

                navDrawerListView.setItemChecked(position, true);
                drawerListAdapter.setSelected(position);

                getSupportActionBar().setTitle(array.get(position).getNombre());

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                drawerLayout.closeDrawer(sifl);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, VerCancionActivity.class);
        intent.putExtra("url", ((Cancion)adapter.getItem(position)).getLetra());
        intent.putExtra("nombreCancion", ((Cancion)adapter.getItem(position)).getNombreCancion());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuscar:
                *//*try {
                    new Canciones(MainActivity.this).buscarCancion(etNombreCancion.getText().toString(), new ComunicadorDelegate() {
                        @Override
                        public void onError(Exception e) {

                        }

                        @Override
                        public void onSuccess(Object object) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }*//*
                Intent intent = new Intent(MainActivity.this, ResultadosBusquedaActivity.class);
                try {
                    intent.putExtra("urlBusqueda", "http://www.musica.com/letras.asp?q="+ URLEncoder.encode(etNombreCancion.getText().toString(), "UTF-8") +"&ref=si");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                break;

        }
    }*/
}


//        webView = (WebView) findViewById(R.id.webView);
//        webView.loadUrl("http://www.musica.com/letras.asp?letras=canciones");
//
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        //webView.setWebChromeClient();
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;
//            }
//        });