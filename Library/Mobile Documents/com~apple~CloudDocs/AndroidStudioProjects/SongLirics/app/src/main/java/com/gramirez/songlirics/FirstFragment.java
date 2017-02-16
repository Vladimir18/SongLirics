package com.gramirez.songlirics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gramirez.songlirics.Domain.Canciones;
import com.gramirez.songlirics.Domain.ComunicadorDelegate;
import com.gramirez.songlirics.Utils.ListViewCancionesAdapter;

import java.net.URL;
import java.util.ArrayList;

public class FirstFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view = null;
    private ListViewCancionesAdapter adapter;
    private ListView listView;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) view.findViewById(R.id.listView);
        URL url = null;
//        btnBuscar.setOnClickListener(this);
        try {
            new Canciones(getActivity()).buscarTopCanciones(new ComunicadorDelegate() {
                @Override
                public void onError(Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(Object object) {
                    adapter = new ListViewCancionesAdapter(getActivity(), (ArrayList<Cancion>)object);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(FirstFragment.this);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), VerCancionActivity.class);
        intent.putExtra("url", ((Cancion)adapter.getItem(position)).getLetra());
        intent.putExtra("nombreCancion", ((Cancion)adapter.getItem(position)).getNombreCancion());
        startActivity(intent);
    }
}
