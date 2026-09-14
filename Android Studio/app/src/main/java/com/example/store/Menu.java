package com.example.store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    Button btnRegistrar, btnListar, btnBuscar;

    private void LoadUI(){
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnListar = findViewById(R.id.btnListar);
        btnBuscar = findViewById(R.id.btnBuscar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        LoadUI();

        btnRegistrar.setOnClickListener(view -> {
            Intent intent = new Intent(Menu.this, Registro.class);
            startActivity(intent);
        });
        btnListar.setOnClickListener(view -> {
            Intent intent = new Intent(Menu.this, Listado.class);
            startActivity(intent);
        });
        btnBuscar.setOnClickListener(view -> {
            Intent intent = new Intent(Menu.this, Buscador.class);
            startActivity(intent);
        });

    }
}