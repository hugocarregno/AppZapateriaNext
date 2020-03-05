package com.example.appzapaterianext.request;

import com.example.appzapaterianext.models.DetalleVenta;
import com.example.appzapaterianext.models.Empleado;
import com.example.appzapaterianext.models.Venta;
import com.example.appzapaterianext.models.Zapatilla;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiClient {
    private static final String PATH = "http://192.168.0.21:45455/api/";
    private static MyApiInterface myApiInterface;

    public static MyApiInterface getMyApiClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myApiInterface = retrofit.create(MyApiInterface.class);
        return myApiInterface;
    }

    public interface MyApiInterface {
        @POST("Empleados/login")
        Call<String> login(@Query("Email") String email, @Query("Clave") String password);
        @GET("Empleados")
        Call<Empleado> getEmpleados(@Header("Authorization") String token);
        @GET("Empleados/{id}")
        Call<Empleado> getEmpleado(@Header("Authorization") String token, @Path("id") int groupId);
        @PUT("Empleados/{id}")
        Call<Empleado> actualizar(@Header("Authorization") String token, @Path("id") int groupId, @Body Empleado empleado);
        @DELETE("Empleados/{id}")
        Call<Empleado> bajaEmpleado(@Header("Authorization") String token, @Path("id") int groupId);

        @GET("Zapatillas")
        Call<List<Zapatilla>> getZapatillas(@Header("Authorization") String token);
        @POST("Zapatillas")
        Call<Zapatilla> altaZapatilla(@Header("Authorization") String token, @Body Zapatilla zapatilla);
        @PUT("Zapatillas/{id}")
        Call<Zapatilla> putZapatilla(@Header("Authorization") String token, @Path("id") int id, @Body Zapatilla zapatilla);
        @DELETE("Zapatillas/{id}")
        Call<Zapatilla> deleteZapatilla(@Header("Authorization") String token, @Path("id") int id);



        @GET("Ventas")
        Call<List<Venta>> getVentas(@Header("Authorization") String token);
        @POST("Ventas")
        Call<Venta> altaVenta(@Header("Authorization") String token, @Body Venta venta);
        @PUT("Ventas/{id}")
        Call<Venta> putVenta(@Header("Authorization") String token, @Path("id") int id, @Body Venta venta);
        @DELETE("Ventas/{id}")
        Call<Venta> deleteVenta(@Header("Authorization") String token, @Path("id") int id);
    }

}
