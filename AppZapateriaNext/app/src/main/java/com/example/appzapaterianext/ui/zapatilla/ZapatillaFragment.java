package com.example.appzapaterianext.ui.zapatilla;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appzapaterianext.R;
import com.example.appzapaterianext.models.Zapatilla;
import com.example.appzapaterianext.ui.inicio.Principal;

import java.util.List;

public class ZapatillaFragment extends Fragment {
    private ZapatillaViewModel zapatillaViewModel;
    private Zapatilla zapatillaF = null;
    private ListView lvZapatillas;
    private ArrayAdapter<Zapatilla> adapter;
    private Button btnVender;
    private Button btnNuevo;
    private Button btnModificar;
    private Button btnGuardar;
    private Button btnBorrar;
    private Button btnVaciarCarrito;
    private Button btnRefrescar;
    private EditText etNuevoId;
    private EditText etMarca;
    private EditText etModelo;
    private EditText etTalle;
    private EditText etStock;
    private EditText etPrecio;
    private EditText etEstado;
    private SharedPreferences sp;


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_zapatilla, container, false);
        ((Principal) getActivity()).setActionBarTitle("Zapatillas");
        //definir bontes o elementos
        btnVender = root.findViewById(R.id.btnVender);
        btnNuevo = root.findViewById(R.id.btnNuevo);
        btnModificar = root.findViewById(R.id.btnModificar);
        btnGuardar = root.findViewById(R.id.btnGuardar);
        btnBorrar = root.findViewById(R.id.btnBorrar);
        btnVaciarCarrito = root.findViewById(R.id.btnVaciarCarrito);
        btnRefrescar = root.findViewById(R.id.btnRefrescar);
        lvZapatillas = root.findViewById(R.id.listadoZapatillas);
        etNuevoId = root.findViewById(R.id.etNuevoId);
        etMarca = root.findViewById(R.id.etNuevaMarca);
        etModelo = root.findViewById(R.id.etNuevoModelo);
        etTalle = root.findViewById(R.id.etNuevoTalle);
        etStock = root.findViewById(R.id.etNuevoStock);
        etPrecio = root.findViewById(R.id.etNuevoPrecio);
        etEstado = root.findViewById(R.id.etNuevoEstado);

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Principal) getActivity()).setActionBarTitle("Nueva Zapatilla");

                btnNuevo.setVisibility(v.GONE);
                btnGuardar.setVisibility(v.VISIBLE);
                lvZapatillas.setVisibility(v.GONE);

                etMarca.setVisibility(v.VISIBLE);
                etModelo.setVisibility(v.VISIBLE);
                etTalle.setVisibility(v.VISIBLE);
                etStock.setVisibility(v.VISIBLE);
                etPrecio.setVisibility(v.VISIBLE);
                etNuevoId.setText("");
                etMarca.setText("");
                etModelo.setText("");
                etPrecio.setText("");
                etTalle.setText("");
                etStock.setText("");

            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNuevo.setVisibility(v.GONE);
                btnGuardar.setVisibility(v.VISIBLE);
                lvZapatillas.setVisibility(v.GONE);
                ((Principal) getActivity()).setActionBarTitle("Editar Zapatilla");
                sp = getContext().getSharedPreferences("editZap",0);
                int id = sp.getInt("id",-1);
                String marca = sp.getString("marca","-1");
                String modelo = sp.getString("modelo","-1");
                int talle = sp.getInt("talle",-1);
                int stock = sp.getInt("stock",-1);
                double precio =(double) sp.getFloat("precio",-1);

                etMarca.setVisibility(v.VISIBLE);
                etModelo.setVisibility(v.VISIBLE);
                etTalle.setVisibility(v.VISIBLE);
                etStock.setVisibility(v.VISIBLE);
                etPrecio.setVisibility(v.VISIBLE);

                etNuevoId.setText(id+"");
                etMarca.setText(marca);
                etModelo.setText(modelo);
                etTalle.setText(talle+"");
                etStock.setText(stock+"");
                etPrecio.setText(precio+"");

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etMarca.getText().toString().equals("") || etModelo.getText().toString().equals("") ||
                        etTalle.getText().toString().equals("") || etStock.getText().toString().equals("") ||
                        etPrecio.getText().toString().equals("") || etEstado.getText().toString().equals("") ){
                    Toast.makeText(getContext(), "Completar", Toast.LENGTH_LONG).show();
                }else{
                    ((Principal) getActivity()).setActionBarTitle("Zapatillas");
                    zapatillaF = new Zapatilla();
                    zapatillaF.setMarca(etMarca.getText().toString());
                    zapatillaF.setModelo(etModelo.getText().toString());
                    zapatillaF.setTalle(Integer.parseInt(etTalle.getText().toString()));
                    zapatillaF.setStock(Integer.parseInt(etStock.getText().toString()));
                    zapatillaF.setPrecio(Double.parseDouble(etPrecio.getText().toString()));
                    zapatillaF.setEstado(Byte.parseByte("1"));
                    if(etNuevoId.getText().toString().equals("")){
                        zapatillaViewModel.altaZapatillaVM(zapatillaF);
                    }else{
                        zapatillaF.setId(Integer.parseInt(etNuevoId.getText().toString()));
                        zapatillaViewModel.actualizarZapatillaVM(zapatillaF);
                    }

                    btnGuardar.setVisibility(v.GONE);
                    btnNuevo.setVisibility(v.VISIBLE);
                    etMarca.setVisibility(v.GONE);
                    etModelo.setVisibility(v.GONE);
                    etTalle.setVisibility(v.GONE);
                    etStock.setVisibility(v.GONE);
                    etPrecio.setVisibility(v.GONE);
                    lvZapatillas.setVisibility(v.VISIBLE);
                }
            }
        });
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getContext().getSharedPreferences("delZap",0);
                int id = sp.getInt("id",-1);
                zapatillaViewModel.bajaZapatillaVM(id);
            }
        });

        btnRefrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zapatillaViewModel.leer();
            }
        });

        zapatillaViewModel = ViewModelProviders.of(this).get(ZapatillaViewModel.class);
        zapatillaViewModel.getListaZapatillas().observe(this, new Observer<List<Zapatilla>>() {
            @Override
            public void onChanged(List<Zapatilla> zapatillas) {
                adapter = new ItemZapatillaAdapter(getContext(), R.layout.item_zapatilla, zapatillas, getLayoutInflater(), btnModificar, btnBorrar, btnVender, btnNuevo, btnVaciarCarrito, btnRefrescar);
                lvZapatillas.setAdapter(adapter);
            }
        });
        zapatillaViewModel.leer();

        return root;
    }

}

