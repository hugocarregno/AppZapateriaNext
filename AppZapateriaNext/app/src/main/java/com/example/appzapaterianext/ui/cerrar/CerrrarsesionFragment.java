package com.example.appzapaterianext.ui.cerrar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.appzapaterianext.R;

public class CerrrarsesionFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences sp;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CerrrarsesionFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CerrrarsesionFragment newInstance(String param1, String param2) {
        CerrrarsesionFragment fragment = new CerrrarsesionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AlertDialog.Builder(getContext())
                .setTitle("Salir")
                .setMessage("¿Desea Cerrar Sesión?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sp = getContext().getSharedPreferences("empleado",0);
                        sp.edit().remove("empleado").commit();
                        sp = getContext().getSharedPreferences("editVta",0);
                        sp.edit().remove("editVta").commit();
                        sp = getContext().getSharedPreferences("delvta",0);
                        sp.edit().remove("delvta").commit();
                        sp = getContext().getSharedPreferences("editZap",0);
                        sp.edit().remove("editZap").commit();
                        sp = getContext().getSharedPreferences("delZap",0);
                        sp.edit().remove("delZap").commit();
                        sp = getContext().getSharedPreferences("carro",0);
                        sp.edit().remove("carro").commit();
                        System.exit(0);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cerrarsesion, container, false);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}