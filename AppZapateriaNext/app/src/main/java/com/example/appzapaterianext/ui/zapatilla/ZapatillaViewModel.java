package com.example.appzapaterianext.ui.zapatilla;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appzapaterianext.models.Zapatilla;
import com.example.appzapaterianext.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZapatillaViewModel extends AndroidViewModel {

    private MutableLiveData<List<Zapatilla>> listaZapatillas;
    private Context context;

    public ZapatillaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public LiveData<List<Zapatilla>> getListaZapatillas() {
        if (listaZapatillas == null) {
            listaZapatillas = new MutableLiveData<>();
        }
        return listaZapatillas;
    }

    public void leer() {
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");
        Call<List<Zapatilla>> zapatillaCall = ApiClient.getMyApiClient().getZapatillas(accessToken);
        zapatillaCall.enqueue(new Callback<List<Zapatilla>>() {
            @Override
            public void onResponse(Call<List<Zapatilla>> call, Response<List<Zapatilla>> response) {
                if (response.isSuccessful()) {
                    List<Zapatilla> zapatilla = response.body();
                    ArrayList<Zapatilla> misZapatillas = new ArrayList<>();

                    for (Zapatilla zap : zapatilla) {
                        if(zap.getEstado()==1){
                            misZapatillas.add(zap);
                        }
                    }
                    listaZapatillas.postValue(misZapatillas);
                }
            }

            @Override
            public void onFailure(Call<List<Zapatilla>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void altaZapatillaVM(Zapatilla zapatilla) {
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<Zapatilla> altaZapatilla = ApiClient.getMyApiClient().altaZapatilla(accessToken, zapatilla);
        altaZapatilla.enqueue(new Callback<Zapatilla>() {
            @Override
            public void onResponse(Call<Zapatilla> call, Response<Zapatilla> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se dio de alta una Zapatilla", Toast.LENGTH_LONG).show();
                    leer();
                }
            }

            @Override
            public void onFailure(Call<Zapatilla> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void actualizarZapatillaVM(Zapatilla zapatilla){
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<Zapatilla> actualizarZapatilla = ApiClient.getMyApiClient().putZapatilla(accessToken, zapatilla.getId(), zapatilla);
        actualizarZapatilla.enqueue(new Callback<Zapatilla>() {
            @Override
            public void onResponse(Call<Zapatilla> call, Response<Zapatilla> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se edito la zapatilla correctamente", Toast.LENGTH_LONG).show();
                    leer();
                }
            }

            @Override
            public void onFailure(Call<Zapatilla> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void bajaZapatillaVM(int id){
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<Zapatilla> eliminarZapatilla = ApiClient.getMyApiClient().deleteZapatilla(accessToken, id);
        eliminarZapatilla.enqueue(new Callback<Zapatilla>() {
            @Override
            public void onResponse(Call<Zapatilla> call, Response<Zapatilla> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se dio de baja una zapatilla", Toast.LENGTH_LONG).show();
                    leer();
                }
            }

            @Override
            public void onFailure(Call<Zapatilla> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}