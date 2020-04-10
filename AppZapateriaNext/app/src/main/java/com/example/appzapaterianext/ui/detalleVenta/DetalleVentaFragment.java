package com.example.appzapaterianext.ui.detalleVenta;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.appzapaterianext.R;
import com.example.appzapaterianext.models.DetalleVenta;
import com.example.appzapaterianext.models.Empleado;
import com.example.appzapaterianext.models.Venta;
import com.example.appzapaterianext.ui.inicio.Principal;
import com.example.appzapaterianext.ui.venta.VentaFragment;
import com.example.appzapaterianext.ui.venta.VentaViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetalleVentaFragment extends Fragment {

    private DetalleVentaViewModel detalleVentaViewModel;
    private VentaViewModel ventaViewModel;
    private ArrayList<DetalleVenta> carrito;
    private ListView lvCarrito;
    private Button btnVenta, btnVaciarCarrito;
    private EditText etDniCliente;
    private TextView tvTotal;
    private Venta venta;
    private ArrayAdapter<DetalleVenta> adapter;
    private SharedPreferences sp;
    private List<DetalleVenta> listadoDetalle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        detalleVentaViewModel =
                ViewModelProviders.of(this).get(DetalleVentaViewModel.class);

        View root = inflater.inflate(R.layout.fragment_detalleventa, container, false);

        etDniCliente = root.findViewById(R.id.etDniCliente);
        btnVenta = root.findViewById(R.id.btnVenta);
        btnVaciarCarrito = root.findViewById(R.id.btnVaciarCarrito);
        tvTotal = root.findViewById(R.id.tvTotal);
        lvCarrito= root.findViewById(R.id.listadoCarrito);
        carrito = ((Principal) getActivity()).getArrayList("carro");

        if(carrito==null){
            Toast.makeText(getContext(), "No tienes nada en el carrito", Toast.LENGTH_LONG).show();
            btnVenta.setVisibility(View.GONE);
            etDniCliente.setVisibility(View.GONE);
        }else{
            listadoDetalle = new ArrayList<>();
            for (DetalleVenta detalleVenta: carrito) {
                listadoDetalle.add(detalleVenta);
            }
            adapter = new ItemDetalleVentaAdapter(getContext(), R.layout.item_zapatilla, listadoDetalle, getLayoutInflater());
            lvCarrito.setAdapter(adapter);

            Empleado empleado = new Empleado();
            sp = getContext().getSharedPreferences("empleado",0);
            empleado.setId(sp.getInt("id",-1));
            empleado.setDni(sp.getString("dni","-1"));
            empleado.setApellido(sp.getString("apellido","-1"));
            empleado.setNombre(sp.getString("nombre","-1"));
            empleado.setEmail(sp.getString("email","-1"));
            empleado.setClave(sp.getString("password","-1"));
            empleado.setEstado(Byte.parseByte(sp.getString("estado", "-1")));
            //cargo datos al objeto ventas
            venta = new Venta();
            venta.setEstado((byte) 1);
            venta.setFecha(new Date());
            venta.setIdEmpleado(empleado.getId());
            venta.setEmpleado(empleado);
            venta.setMontoTotal(0.0);
            //lo seteo a list detalle venta
            for(DetalleVenta dv: carrito){
                venta.setMontoTotal(venta.getMontoTotal()+dv.getZapatilla().getPrecio()*dv.getCantidad());
            }
            tvTotal.setText('$'+String.valueOf(venta.getMontoTotal()));
            for(DetalleVenta dv: carrito){
                dv.setVenta(venta);
            }
            btnVaciarCarrito.setVisibility(View.VISIBLE);
        }

        btnVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etDniCliente.getText().toString().length()==8){
                    venta.setCliente(etDniCliente.getText().toString());
                    listadoDetalle = new ArrayList<>();
                    for (DetalleVenta detalleVenta: carrito) {
                        detalleVenta.getZapatilla().setStock(detalleVenta.getZapatilla().getStock()-detalleVenta.getCantidad());
                        listadoDetalle.add(detalleVenta);
                    }
                    venta.setDetalles(listadoDetalle);
                    //ventaViewModel.altaVentaVM(venta);
                    //detalleVentaViewModel.altaDetalleVentasVM(listadoDetalle);
                    //eliminar sp
                    carrito=null;
                    ((Principal) getActivity()).saveArrayList(carrito, "carro");
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_venta);
                }else{
                    Toast.makeText(getContext(), "Ingrese DNI del cliente",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnVaciarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Principal) getContext()).saveArrayList(null,"carro");
                btnVaciarCarrito.setVisibility(View.GONE);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_detalleVenta);
            }
        });

        return root;
    }

}