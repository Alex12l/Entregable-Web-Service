package com.example.store;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {
    ListView lstProductos;
    RequestQueue requestQueue;

    private final String URL = "http://10.246.96.40:3000/productos";

    private void loadUI() {
        lstProductos = findViewById(R.id.lstProductos);
    }

    private void obtenerDatosWS() {
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        renderizarListView(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error", volleyError.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void renderizarListView(JSONArray jsonArray) {
        try {
            ArrayAdapter adapter;
            ArrayList<String> listaProductos = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // Combinamos nombre y precio para que se vea ordenado en la lista
                String infoProducto = jsonObject.getString("nombre") + " - S/ " + jsonObject.getString("precio");
                listaProductos.add(infoProducto);
            }

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaProductos);
            lstProductos.setAdapter(adapter);
        } catch (Exception e) {
            logError(e);
        }
    }

    private void logError(Exception e) {
        Log.e("Error", e.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado);

        loadUI();
        obtenerDatosWS();
    }
}