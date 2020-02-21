package com.example.appzapaterianext.ui.empleado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appzapaterianext.ui.inicio.Principal;
import com.example.appzapaterianext.R;
import com.example.appzapaterianext.models.Empleado;

public class EmpleadoFragment extends Fragment {
    TextView id;
    EditText dni, apellido, nombre, email, password;
    Button aceptar, editar, baja;
    private EmpleadoViewModel empleadoViewModel;
    private Empleado empleadoF = null;
    private SharedPreferences sp;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_empleado, container, false);
        ((Principal) getActivity()).setActionBarTitle("Perfil");
        id = root.findViewById(R.id.tvId);
        dni = root.findViewById(R.id.etDni);
        apellido = root.findViewById(R.id.etApellido);
        nombre = root.findViewById(R.id.etNombre);
        email = root.findViewById(R.id.etEmail);
        password = root.findViewById(R.id.etPassword);

        aceptar = root.findViewById(R.id.btAceptar);
        editar = root.findViewById(R.id.btEditar);
        baja = root.findViewById(R.id.btBaja);

        empleadoViewModel = ViewModelProviders.of(this).get(EmpleadoViewModel.class);

        empleadoViewModel.getEmpleadoMutableLiveData().observe(this, new Observer<Empleado>() {
            @Override
            public void onChanged(Empleado empleado) {

                empleadoF = empleado;

                fijarDatos(empleado);
                sp = getContext().getSharedPreferences("empleado",0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("id",empleado.getId());
                editor.putString("dni",empleado.getDni());
                editor.putString("apellido",empleado.getApellido());
                editor.putString("nombre",empleado.getNombre());
                editor.putString("email",empleado.getEmail());
                editor.putString("password",empleado.getClave());
                editor.putString("estado", String.valueOf(empleado.getEstado()));
                editor.commit();
            }
        });


        empleadoViewModel.leer();

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setTitle("").setMessage("¿Desea guardar los datos?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dni.getText().toString().length()==8){
                            aceptar();
                        }else{
                            Toast.makeText(getContext(), "Ingrese un DNI valido",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        disabledFields();
                    }
                }).show();
            }
        });
        baja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setTitle("").setMessage("¿Desea darse de baja?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        empleadoViewModel.bajaEmpleadoVM(Integer.parseInt(id.getText().toString()));
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
        return root;
    }

    public void editar() {
        dni.setEnabled(true);
        apellido.setEnabled(true);
        nombre.setEnabled(true);
        password.setEnabled(true);

        editar.setVisibility(View.GONE);
        aceptar.setVisibility(View.VISIBLE);
    }

    public void aceptar() {

        empleadoF.setNombre(nombre.getText().toString());
        empleadoF.setApellido(apellido.getText().toString());
        empleadoF.setDni(dni.getText().toString());
        if (!password.getText().toString().equals("")) {
            empleadoF.setClave(password.getText().toString());
        }

        empleadoViewModel.modificacionEmpleadoVM(empleadoF);
    }

    public void fijarDatos(Empleado empleadoSesion) {
        id.setText(String.valueOf(empleadoF.getId()));
        nombre.setText(String.valueOf(empleadoSesion.getNombre()));
        apellido.setText(String.valueOf(empleadoSesion.getApellido()));
        dni.setText(String.valueOf(empleadoSesion.getDni()));
        email.setText(String.valueOf(empleadoSesion.getEmail()));
        password.setText("");

        ((Principal)getActivity()).setTitulo(empleadoSesion.getNombre()+" "+empleadoSesion.getApellido());
        ((Principal)getActivity()).setSubtitulo(empleadoSesion.getEmail());
        disabledFields();
    }

    public void disabledFields(){
        nombre.setEnabled(false);
        apellido.setEnabled(false);
        dni.setEnabled(false);
        password.setEnabled(false);

        editar.setVisibility(View.VISIBLE);
        aceptar.setVisibility(View.GONE);
    }
}