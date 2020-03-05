package com.example.appzapaterianext.ui.detalleVenta;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appzapaterianext.models.DetalleVenta;
import com.example.appzapaterianext.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleVentaViewModel extends AndroidViewModel {

    private MutableLiveData<List<DetalleVenta>> listaDetalleVenta;
    private Context context;

    public DetalleVentaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<DetalleVenta>> getListaDetalleVentas() {
        if (listaDetalleVenta == null) {
            listaDetalleVenta = new MutableLiveData<>();
        }
        return listaDetalleVenta;
    }
/*
    public void altaDetalleVentasVM(List<DetalleVenta> listado){

        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");
        Call<String> altaVentaDetalle = ApiClient.getMyApiClient().altaDetalleVentas(accessToken, listado);
        altaVentaDetalle.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se dio de alta una Venta", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
*/
}