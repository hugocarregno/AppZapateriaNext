package com.example.appzapaterianext.ui.empleado;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appzapaterianext.models.Empleado;
import com.example.appzapaterianext.request.ApiClient;
import com.example.appzapaterianext.ui.inicio.MainActivity;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpleadoViewModel extends AndroidViewModel {

    private MutableLiveData<String> token;
    private MutableLiveData<Empleado> empleadoMutableLiveData;
    private Context context;
    private SharedPreferences sp;

    public EmpleadoViewModel(@NonNull Application application) {
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

    public LiveData<Empleado> getEmpleadoMutableLiveData() {
        if (empleadoMutableLiveData == null) {
            empleadoMutableLiveData = new MutableLiveData<>();
        }
        return empleadoMutableLiveData;
    }

    public void leer() {
        Call<Empleado> empleadoCall = ApiClient.getMyApiClient().getEmpleados(sp.getString("token", ""));
        empleadoCall.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if (response.isSuccessful()) {
                    Empleado empleado = response.body();
                    empleadoMutableLiveData.postValue(empleado);

                }
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getEmpleadoVM(int id){
        Call<Empleado> empleadoCall = ApiClient.getMyApiClient().getEmpleado(sp.getString("token", ""), id);
        empleadoCall.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if (response.isSuccessful()) {
                    Empleado empleado = response.body();
                    empleadoMutableLiveData.postValue(empleado);

                }
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void modificacionEmpleadoVM(Empleado empleado) {
        Call<Empleado> empActualizado = ApiClient.getMyApiClient().actualizar(sp.getString("token", ""), empleado.getId(), empleado);
        empActualizado.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se actualizaron los datos del empleado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void bajaEmpleadoVM(int id){
        Call<Empleado> eliminarEmpleado = ApiClient.getMyApiClient().bajaEmpleado(sp.getString("token", ""), id);
        eliminarEmpleado.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if (response.isSuccessful()) {
                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("cerrar","1");
                    context.startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}