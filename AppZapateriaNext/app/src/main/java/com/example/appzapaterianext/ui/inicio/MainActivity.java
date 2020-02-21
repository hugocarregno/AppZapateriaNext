package com.example.appzapaterianext.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appzapaterianext.R;

public class MainActivity extends AppCompatActivity {
    private EditText usuario, password;
    private MainViewModel mainViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configurarVista();

        final Intent intent = this.getIntent();
        if(intent.hasExtra("cerrar")){
            Toast.makeText(this, "Se dio de baja el empleado", Toast.LENGTH_SHORT).show();

        }
    }

    public void configurarVista() {
        usuario = findViewById(R.id.etUsuario);
        password = findViewById(R.id.etPassword);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.getToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s != "fallo") {
                    Intent i = new Intent(getApplicationContext(), Principal.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void ingresar(View view) {
        String email = usuario.getText().toString();
        String contra = password.getText().toString();

        if (email.equals("") || contra.equals("")) {
            Toast.makeText(getApplicationContext(), "Completar", Toast.LENGTH_LONG).show();
        } else {
            mainViewModel.loginVM(email, contra);
        }
    }


}