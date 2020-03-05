package com.example.appzapaterianext.ui.inicio;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.appzapaterianext.R;
import com.example.appzapaterianext.models.DetalleVenta;


import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.appzapaterianext.models.Venta;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView titulo, subtitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);
        titulo = hView.findViewById(R.id.titulo);
        subtitulo = hView.findViewById(R.id.subtitulo);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_zapatilla, R.id.nav_empleado, R.id.nav_cerrarSesion)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void setTitulo(String titulo){
        this.titulo.setText(titulo);
    }
    public void setSubtitulo(String subtitulo){
        this.subtitulo.setText(subtitulo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_carrito:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_venta);
                return true;
            case R.id.action_cerrar:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_cerrarSesion);
                return true;
            case R.id.action_empleado:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_empleado);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void saveArrayList(ArrayList<DetalleVenta> list, String key){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("carro",0);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<DetalleVenta> getArrayList(String key){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("carro",0);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<DetalleVenta>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
