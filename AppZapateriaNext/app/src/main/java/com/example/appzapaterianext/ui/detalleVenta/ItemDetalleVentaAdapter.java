package com.example.appzapaterianext.ui.detalleVenta;

import android.content.Context;
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
import com.example.appzapaterianext.models.DetalleVenta;

import java.util.List;

public class ItemDetalleVentaAdapter extends ArrayAdapter<DetalleVenta> {
    private Context context;
    private List<DetalleVenta> listaDetalle;
    private LayoutInflater inflater;

    public ItemDetalleVentaAdapter(@NonNull Context context, int resource, @NonNull List<DetalleVenta> detalles, LayoutInflater inflater) {
        super(context, resource,detalles);
        this.context = context;
        this.listaDetalle=detalles;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=convertView;
        if(itemView==null){
            itemView=inflater.inflate(R.layout.item_zapatilla,parent,false);
        }
        DetalleVenta item =listaDetalle.get(position);
        final TextView tvId = itemView.findViewById(R.id.tvId);
        final EditText etMarca = itemView.findViewById(R.id.etMarca);
        final EditText etModelo = itemView.findViewById(R.id.etModelo);
        final EditText etTalle = itemView.findViewById(R.id.etTalle);
        final EditText etStock = itemView.findViewById(R.id.etStock);
        final EditText etPrecio = itemView.findViewById(R.id.etPrecio);
        final TextView tvEstado = itemView.findViewById(R.id.tvEstado);
        final TextView tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        final Button btnMas = itemView.findViewById(R.id.btnMas);
        final Button btnMenos = itemView.findViewById(R.id.btnMenos);
        final EditText etCant = itemView.findViewById(R.id.etCant);

        tvId.setText(item.getIdZapatilla()+ "");
        etMarca.setText(item.getZapatilla().getMarca());
        etModelo.setText(item.getZapatilla().getModelo());
        etTalle.setText(item.getZapatilla().getTalle()+"");
        etStock.setText(item.getZapatilla().getStock()+"");
        etPrecio.setText(item.getZapatilla().getPrecio()+"");
        tvEstado.setText(item.getZapatilla().getEstado()+"");
        etCant.setText(item.getCantidad()+"");
        tvDescripcion.setText("Marca: "+etMarca.getText().toString()+" Modelo: "+etModelo.getText().toString()+" Talle: "+etTalle.getText().toString()+" Stock: "+etStock.getText().toString());

        return itemView;
    }
}
