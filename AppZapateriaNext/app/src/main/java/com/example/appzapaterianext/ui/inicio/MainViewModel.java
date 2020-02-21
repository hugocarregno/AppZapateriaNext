package com.example.appzapaterianext.ui.inicio;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appzapaterianext.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<String> token;
    private Context context;

    public MainViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getToken() {
        if (token == null) {
            token = new MutableLiveData<>();
        }
        return token;
    }

    public void loginVM(final String usuario, final String password) {
        Call<String> datos = ApiClient.getMyApiClient().login(usuario, password);
        datos.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String resultado = response.body();
                    token.postValue(resultado);
                    SharedPreferences sp = context.getSharedPreferences("token", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    String t = "Bearer " + resultado;
                    editor.putString("token", t);
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                token.postValue("fallo");
            }
        });
    }
}
