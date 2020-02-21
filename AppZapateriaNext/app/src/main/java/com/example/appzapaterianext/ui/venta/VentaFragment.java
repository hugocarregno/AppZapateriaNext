package com.example.appzapaterianext.ui.venta;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.appzapaterianext.R;
import com.example.appzapaterianext.models.Venta;
import com.example.appzapaterianext.ui.inicio.Principal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class VentaFragment extends Fragment {
    private VentaViewModel ventaViewModel;
    private ArrayAdapter<Venta> adapter;
    private ListView lvVentas;
    private Venta ventaF=null;
    private Button btnNuevo;
    private Button btnGuardar;
    private Button btnBorrar;
    private Button btnModificar;
    private EditText etId, etFecha, etTiempo, etCliente, etMontoTotal;
    private TextView tvEmpleadoId, tvFecha, tvTiempo, tvDniCliente, tvMontoTotal;
    private SharedPreferences sp;
    private Calendar fechaElegida;
    private int year, month, day, hour, minute, second;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_venta, container, false);
        lvVentas= root.findViewById(R.id.listadoVentas);
        btnNuevo= root.findViewById(R.id.btnNuevo);
        btnGuardar= root.findViewById(R.id.btnGuardar);
        btnBorrar= root.findViewById(R.id.btnBorrar);
        btnModificar= root.findViewById(R.id.btnModificar);
        etId= root.findViewById(R.id.etId);
        etFecha= root.findViewById(R.id.etFecha);
        etTiempo= root.findViewById(R.id.etTiempo);
        etCliente= root.findViewById(R.id.etCliente);
        etMontoTotal= root.findViewById(R.id.etMontoTotal);
        tvEmpleadoId= root.findViewById(R.id.tvEmpleadoId);
        tvFecha= root.findViewById(R.id.tvFecha);
        tvTiempo= root.findViewById(R.id.tvTiempo);
        tvDniCliente= root.findViewById(R.id.tvCliente);
        tvMontoTotal= root.findViewById(R.id.tvMontoTotal);

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_zapatilla);
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getContext().getSharedPreferences("delvta",0);
                int id = sp.getInt("id",-1);
                ventaViewModel.bajaVentaVM(id);
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFecha.setVisibility(View.VISIBLE);
                tvTiempo.setVisibility(View.VISIBLE);
                tvDniCliente.setVisibility(View.VISIBLE);
                tvMontoTotal.setVisibility(View.VISIBLE);
                btnNuevo.setVisibility(v.GONE);
                btnGuardar.setVisibility(v.VISIBLE);
                lvVentas.setVisibility(v.GONE);
                ((Principal) getActivity()).setActionBarTitle("Editar Venta");
                sp = getContext().getSharedPreferences("editVta",0);
                int id = sp.getInt("id",-1);
                String fecha = sp.getString("fecha","-1");
                String tiempo = sp.getString("tiempo","-1");
                String cliente = sp.getString("cliente","-1");
                double montoTotal =(double) sp.getFloat("montoTotal",-1);
                int empleadoId = sp.getInt("empleadoId",-1);

                etFecha.setVisibility(v.VISIBLE);
                etTiempo.setVisibility(v.VISIBLE);
                etCliente.setVisibility(v.VISIBLE);
                etMontoTotal.setVisibility(v.VISIBLE);

                etId.setText(id+"");
                etFecha.setText(fecha);
                etTiempo.setText(tiempo);
                etCliente.setText(cliente);
                etMontoTotal.setText(montoTotal+"");
                tvEmpleadoId.setText(empleadoId+"");

                try{
                    fechaElegida = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
                    fechaElegida.setTime(sdf.parse(etFecha.getText().toString()+" "+etTiempo.getText().toString()));

                    year = fechaElegida.get(Calendar.YEAR);
                    month = fechaElegida.get(Calendar.MONTH);
                    day = fechaElegida.get(Calendar.DATE);
                    hour = fechaElegida.get(Calendar.HOUR);
                    minute = fechaElegida.get(Calendar.MINUTE);
                    second = fechaElegida.get(Calendar.SECOND);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                };
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etFecha.getText().toString().equals("") || etTiempo.getText().toString().equals("") ||
                        etCliente.getText().toString().equals("") || etMontoTotal.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Completar", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        ventaF = new Venta();
                        ventaF.setId(Integer.parseInt(etId.getText().toString()));

                        fechaElegida.set(year,month,day,hour,minute,second);

                        ventaF.setFecha(fechaElegida.getTime());

                        ventaF.setIdEmpleado(Integer.parseInt(tvEmpleadoId.getText().toString()));
                        ventaF.setCliente(etCliente.getText().toString());
                        ventaF.setMontoTotal(Double.parseDouble(etMontoTotal.getText().toString()));
                        ventaF.setEstado(Byte.parseByte("1"));

                        ventaViewModel.actualizarVentaVM(ventaF);

                        btnGuardar.setVisibility(v.GONE);
                        btnNuevo.setVisibility(v.VISIBLE);
                        etId.setVisibility(v.GONE);
                        etFecha.setVisibility(v.GONE);
                        etTiempo.setVisibility(v.GONE);
                        etCliente.setVisibility(v.GONE);
                        etMontoTotal.setVisibility(v.GONE);
                        lvVentas.setVisibility(v.VISIBLE);
                        ((Principal) getActivity()).setActionBarTitle("Ventas");
                        tvFecha.setVisibility(View.GONE);
                        tvTiempo.setVisibility(View.GONE);
                        tvDniCliente.setVisibility(View.GONE);
                        tvMontoTotal.setVisibility(View.GONE);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        });


        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.alertDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                        mes++;
                        etFecha.setText(""+dia+"/"+mes+"/"+anio);
                        mes--;
                        year = anio;
                        month = mes;
                        day = dia;
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        etTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),R.style.alertDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hora, int minutos) {
                        etTiempo.setText(hora+":"+minutos+":00");
                        hour = hora;
                        minute = minutos;
                        second = 00;
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });

        ventaViewModel = ViewModelProviders.of(this).get(VentaViewModel.class);

        ventaViewModel.getListaVentas().observe(this, new Observer<List<Venta>>() {
            @Override
            public void onChanged(List<Venta> ventas) {
                adapter = new ItemVentaAdapter(getContext(), R.layout.item_venta, ventas, getLayoutInflater(), btnBorrar, btnModificar);
                lvVentas.setAdapter(adapter);
            }
        });
        ventaViewModel.getVentasVM();

        return root;
    }
}
