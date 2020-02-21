package com.example.appzapaterianext.ui.venta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appzapaterianext.R;
import com.example.appzapaterianext.models.Venta;

import java.text.SimpleDateFormat;
import java.util.List;

public class ItemVentaAdapter extends ArrayAdapter<Venta> {
    private Context context;
    private List<Venta> listaVentas;
    private LayoutInflater inflater;
    private Button btnBorrar,btnModificar;
    private SharedPreferences sp;

    public ItemVentaAdapter(@NonNull Context context, int resource, @NonNull List<Venta> ventas, LayoutInflater inflater, Button borrar, Button modificar) {
        super(context, resource,ventas);
        this.context = context;
        this.listaVentas=ventas;
        this.inflater=inflater;
        this.btnBorrar= borrar;
        this.btnModificar = modificar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=convertView;
        if(itemView==null){
            itemView=inflater.inflate(R.layout.item_venta,parent,false);
        }
        final Venta venta =listaVentas.get(position);
        final Button btnEditar = itemView.findViewById(R.id.btnEditar);
        final Button btnEliminar = itemView.findViewById(R.id.btnEliminar);
        final TextView tvId = itemView.findViewById(R.id.tvId);
        final EditText etFecha = itemView.findViewById(R.id.etFecha);
        final EditText etTiempo = itemView.findViewById(R.id.etTiempo);
        final EditText etCliente = itemView.findViewById(R.id.etCliente);
        final EditText etMontoTotal = itemView.findViewById(R.id.etMontoTotal);
        final TextView tvEmpleadoId= itemView.findViewById(R.id.tvEmpleadoId);

        tvId.setText(venta.getId()+"");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(venta.getFecha());
        etFecha.setText(strDate);

        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
        String strTime = sdfTime.format(venta.getFecha());
        etTiempo.setText(strTime);

        etCliente.setText(venta.getCliente());
        etMontoTotal.setText(String.valueOf(venta.getMontoTotal()));
        tvEmpleadoId.setText(venta.getIdEmpleado()+"");

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = context.getSharedPreferences("editVta",0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("id",Integer.parseInt(tvId.getText().toString()));
                editor.putString("fecha",etFecha.getText().toString());
                editor.putString("tiempo",etTiempo.getText().toString());
                editor.putString("cliente",etCliente.getText().toString());
                editor.putFloat("montoTotal",Float.parseFloat(etMontoTotal.getText().toString()));
                editor.putInt("empleadoId", Integer.parseInt(tvEmpleadoId.getText().toString()));
                editor.commit();
                btnModificar.callOnClick();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Eliminar")
                        .setMessage("¿Dar de baja esta venta?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sp = context.getSharedPreferences("delvta",0);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("id",Integer.parseInt(tvId.getText().toString()));
                                editor.commit();
                                btnEliminar.setVisibility(View.GONE);
                                btnBorrar.callOnClick();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }

        });

        return itemView;
    }
}
