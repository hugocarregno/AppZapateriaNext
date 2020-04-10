package com.example.appzapaterianext.ui.venta;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appzapaterianext.models.Venta;
import com.example.appzapaterianext.request.ApiClient;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VentaViewModel extends AndroidViewModel {
    private MutableLiveData<String> token;
    private MutableLiveData<List<Venta>> listaVentas;
    private Context context;
    private SharedPreferences sp;


    public VentaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        sp = context.getSharedPreferences("token",0);
    }

    public LiveData<String> getToken() {
        if (token == null) {
            token = new MutableLiveData<>();
        }
        return token;
    }


    public LiveData<List<Venta>> getListaVentas() {
        if (listaVentas == null) {
            listaVentas = new MutableLiveData<>();
        }
        return listaVentas;
    }

    public void getVentasVM(){
        Call<List<Venta>> ventaCall = ApiClient.getMyApiClient().getVentas(sp.getString("token", ""));
        ventaCall.enqueue(new Callback<List<Venta>>() {
            @Override
            public void onResponse(Call<List<Venta>> call, Response<List<Venta>> response) {
                if (response.isSuccessful()) {
                    List<Venta> ventas = response.body();
                    ArrayList<Venta> misVentas = new ArrayList<>();

                    for (Venta vta : ventas) {
                        if(vta.getEstado()==1){
                            misVentas.add(vta);
                        }
                    }

                    listaVentas.postValue(misVentas);
                }
            }

            @Override
            public void onFailure(Call<List<Venta>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void altaVentaVM(Venta venta){

        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");
        Call<Venta> altaVenta = ApiClient.getMyApiClient().altaVenta(accessToken, venta);
        altaVenta.enqueue(new Callback<Venta>() {
            @Override
            public void onResponse(Call<Venta> call, Response<Venta> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se dio de alta una Venta", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Venta> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void actualizarVentaVM(Venta venta){
        Call<Venta> actualizarVenta = ApiClient.getMyApiClient().putVenta(sp.getString("token", ""), venta.getId(), venta);
        actualizarVenta.enqueue(new Callback<Venta>() {
            @Override
            public void onResponse(Call<Venta> call, Response<Venta> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se edito la venta correctamente", Toast.LENGTH_LONG).show();
                    getVentasVM();
                }
            }

            @Override
            public void onFailure(Call<Venta> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void bajaVentaVM(int id){
        Call<Venta> eliminarVenta = ApiClient.getMyApiClient().deleteVenta(sp.getString("token", ""), id);
        eliminarVenta.enqueue(new Callback<Venta>() {
            @Override
            public void onResponse(Call<Venta> call, Response<Venta> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se dio de baja una Venta", Toast.LENGTH_LONG).show();
                    getVentasVM();
                }
            }

            @Override
            public void onFailure(Call<Venta> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

}
