package com.example.appzapaterianext.ui.zapatilla;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appzapaterianext.R;
import com.example.appzapaterianext.models.DetalleVenta;
import com.example.appzapaterianext.models.Zapatilla;
import com.example.appzapaterianext.ui.inicio.Principal;
import java.util.ArrayList;
import java.util.List;
import static android.view.View.VISIBLE;

public class ItemZapatillaAdapter extends ArrayAdapter<Zapatilla> {
    private Context context;
    private List<Zapatilla> lista;
    private ArrayList<DetalleVenta> carro;
    private DetalleVenta detalleVenta = null;
    private LayoutInflater li;
    private Button btnBorrar;
    private Button btnVender;
    private Button btnModificar;
    private Button btnNuevo;
    private Button btnVaciarCarrito;
    private Button refrescar;
    private SharedPreferences sp;


    public ItemZapatillaAdapter(@NonNull Context context, int resource, @NonNull List<Zapatilla> objects, LayoutInflater li, Button modificar, Button eliminar, Button vender, Button nuevo, Button vaciarCarrito, Button refrescar) {
        super(context, resource, objects);
        this.context = context;
        this.lista = objects;
        this.carro = new ArrayList<>();
        this.li = li;
        this.btnModificar = modificar;
        this.btnBorrar = eliminar;
        this.btnVender = vender;
        this.btnNuevo = nuevo;
        this.btnVaciarCarrito = vaciarCarrito;
        this.refrescar = refrescar;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = li.inflate(R.layout.item_zapatilla, parent, false);
        }
        Zapatilla item = lista.get(position);
        final Button btnEditar = itemView.findViewById(R.id.btnEditar);
        final Button btnEliminar = itemView.findViewById(R.id.btnEliminar);
        final TextView tvId = itemView.findViewById(R.id.tvId);
        final EditText etMarca = itemView.findViewById(R.id.etMarca);
        final EditText etModelo = itemView.findViewById(R.id.etModelo);
        final EditText etTalle = itemView.findViewById(R.id.etTalle);
        final EditText etStock = itemView.findViewById(R.id.etStock);
        final EditText etPrecio = itemView.findViewById(R.id.etPrecio);
        final TextView tvEstado = itemView.findViewById(R.id.tvEstado);
        CheckBox cb = itemView.findViewById(R.id.cbAgregar);
        final Button btnMas = itemView.findViewById(R.id.btnMas);
        final Button btnMenos = itemView.findViewById(R.id.btnMenos);
        final EditText etCant = itemView.findViewById(R.id.etCant);
        TextView tvDescripcion = itemView.findViewById(R.id.tvDescripcion);

        btnEditar.setVisibility(VISIBLE);
        btnEliminar.setVisibility(VISIBLE);
        cb.setVisibility(VISIBLE);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked() == true) {
                    etCant.setEnabled(true);
                    btnMas.setEnabled(true);
                    btnMenos.setEnabled(true);

                    detalleVenta = new DetalleVenta();
                    detalleVenta.setCantidad(1);
                    detalleVenta.setPrecio(lista.get(position).getPrecio());
                    detalleVenta.setZapatilla(lista.get(position));

                    carro.add(detalleVenta);

                } else if (!carro.isEmpty() && buttonView.isChecked() == false) {
                    etCant.setText("1");
                    etCant.setEnabled(false);
                    btnMas.setEnabled(false);
                    btnMenos.setEnabled(false);

                    for (int i = 0; i < carro.size(); i++) {
                        if(carro.get(i).getZapatilla() == lista.get(position)){
                            carro.remove(i);
                        }
                    }

                }
                    if(carro.size()>0){
                      btnVender.setVisibility(VISIBLE);
                    }else{
                      btnVender.setVisibility(View.GONE);
                      btnVaciarCarrito.setVisibility(View.GONE);

                    }
            }
        });

        tvId.setText(item.getId() + "");
        etMarca.setText(item.getMarca());
        etModelo.setText(item.getModelo());
        etTalle.setText(item.getTalle()+"");
        etStock.setText(item.getStock()+"");
        etPrecio.setText(item.getPrecio()+"");
        tvEstado.setText(item.getEstado()+"");
        tvDescripcion.setText("Marca: "+etMarca.getText().toString()+" Modelo: "+etModelo.getText().toString()+" Talle: "+etTalle.getText().toString()+" Stock: "+etStock.getText().toString());

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = context.getSharedPreferences("editZap",0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("id",Integer.parseInt(tvId.getText().toString()));
                editor.putString("marca",etMarca.getText().toString());
                editor.putString("modelo",etModelo.getText().toString());
                editor.putInt("talle",Integer.parseInt(etTalle.getText().toString()));
                editor.putInt("stock",Integer.parseInt(etStock.getText().toString()));
                editor.putFloat("precio",Float.parseFloat(etPrecio.getText().toString()));
                editor.commit();
                btnModificar.callOnClick();

            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Eliminar")
                        .setMessage("¿Dar de baja esta zapatilla?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sp = context.getSharedPreferences("delZap",0);
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
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(etStock.getText().toString()) == Integer.parseInt(etCant.getText().toString())){
                    Toast.makeText(getContext(),"No hay mas stock", Toast.LENGTH_LONG).show();
                }else{
                    int incremento=Integer.parseInt(etCant.getText().toString())+1;
                    etCant.setText(incremento+"");

                    for(DetalleVenta dv: carro){
                        if(dv.getZapatilla() == (lista.get(position))){
                            dv.setCantidad(incremento);
                        }
                    }
                }
            }
        });
        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(etCant.getText().toString())==1){
                    Toast.makeText(getContext(),"El minimo a comprar es 1", Toast.LENGTH_LONG).show();
                }else{
                    int decremento = Integer.parseInt(etCant.getText().toString())-1;
                    etCant.setText(decremento+"");

                    for(DetalleVenta dv: carro){
                        if(dv.getZapatilla() == (lista.get(position))){
                            dv.setCantidad(decremento);
                        }
                    }
                }
            }
        });

        btnVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Principal) getContext()).saveArrayList(carro,"carro");
                btnVaciarCarrito.setVisibility(VISIBLE);
                btnVender.setVisibility(View.GONE);
            }
        });

        btnVaciarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Principal) getContext()).saveArrayList(null,"carro");
                btnVaciarCarrito.setVisibility(View.GONE);
                refrescar.callOnClick();
                Toast.makeText(context,"Se vacio el carrito", Toast.LENGTH_SHORT).show();
            }
        });
        return itemView;
    }

}


