package com.example.store;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class Buscador extends AppCompatActivity {

    EditText edtIdB, edtNombreB, edtCategoriaB, edtDescripcionB, edtGarantiaB, edtPrecioB, edtStockB;
    Button btnBuscar, btnActualizar, btnEliminar, btnReiniciar;

    RequestQueue requestQueue;
    private final String URL = "http://10.246.96.40:3000/productos";

    private void loadUI() {
        edtIdB = findViewById(R.id.edtIdB);
        edtNombreB = findViewById(R.id.edtNombreB);
        edtCategoriaB = findViewById(R.id.edtCategoriaB);
        edtDescripcionB = findViewById(R.id.edtDescripcionB);
        edtGarantiaB = findViewById(R.id.edtGarantiaB);
        edtPrecioB = findViewById(R.id.edtPrecioB);
        edtStockB = findViewById(R.id.edtStockB);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnReiniciar = findViewById(R.id.btnReiniciar);

        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void validarError(int statusCode, String errorJSON) {
        if (statusCode == 404) {
            try {
                JSONObject jsonObject = new JSONObject(errorJSON);
                String mensajeError = jsonObject.getString("message");
                Toast.makeText(getApplicationContext(), "Error en WS: " + mensajeError, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void buscarProducto() {
        if (edtIdB.getText().toString().isEmpty()) {
            edtIdB.setError("Campo requerido");
            edtIdB.requestFocus();
            return;
        }

        requestQueue = Volley.newRequestQueue(this);
        String endPoint = URL + "/" + edtIdB.getText().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                endPoint,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            edtNombreB.setText(jsonObject.getString("nombre"));
                            edtCategoriaB.setText(jsonObject.getString("categoria"));
                            edtDescripcionB.setText(jsonObject.getString("descripcion"));
                            edtGarantiaB.setText(jsonObject.getString("garantia"));
                            edtPrecioB.setText(jsonObject.getString("precio"));
                            edtStockB.setText(jsonObject.getString("stock"));

                            btnActualizar.setEnabled(true);
                            btnEliminar.setEnabled(true);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        NetworkResponse response = volleyError.networkResponse;
                        if (response != null && response.data != null) {
                            int statusCode = response.statusCode;
                            String errorJSON = new String(response.data);
                            validarError(statusCode, errorJSON);
                        }
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void actualizarDatos() {
        requestQueue = Volley.newRequestQueue(this);
        String endPoint = URL + "/" + edtIdB.getText().toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", edtNombreB.getText().toString());
            jsonObject.put("categoria", edtCategoriaB.getText().toString());
            jsonObject.put("descripcion", edtDescripcionB.getText().toString());
            jsonObject.put("garantia", edtGarantiaB.getText().toString());
            jsonObject.put("precio", edtPrecioB.getText().toString());
            jsonObject.put("stock", edtStockB.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                endPoint,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        NetworkResponse response = volleyError.networkResponse;
                        if (response != null && response.data != null) {
                            int statusCode = response.statusCode;
                            String errorJSON = new String(response.data);
                            validarError(statusCode, errorJSON);
                        }
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void validarAccion(String accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Store Perú");
        builder.setMessage("¿Seguro de " + accion + "?");
        builder.setPositiveButton("Si", (DialogInterface a, int b) -> {
            if (accion.equalsIgnoreCase("eliminar")) this.eliminarDatos();
            if (accion.equalsIgnoreCase("actualizar")) this.actualizarDatos();
        });
        builder.setNegativeButton("NO", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void eliminarDatos() {
        requestQueue = Volley.newRequestQueue(this);
        String endPoint = URL + "/" + edtIdB.getText().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                endPoint,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String message = null;
                        try {
                            message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            reiniciarDatos();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        NetworkResponse response = volleyError.networkResponse;
                        if (response != null && response.data != null) {
                            int statusCode = response.statusCode;
                            String errorJSON = new String(response.data);
                            validarError(statusCode, errorJSON);
                        }
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void reiniciarDatos() {
        edtIdB.setText("");
        edtNombreB.setText("");
        edtCategoriaB.setText("");
        edtDescripcionB.setText("");
        edtGarantiaB.setText("");
        edtPrecioB.setText("");
        edtStockB.setText("");

        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        edtIdB.requestFocus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscador);

        this.loadUI();
        btnBuscar.setOnClickListener(v -> { this.buscarProducto(); });
        btnActualizar.setOnClickListener(v -> { this.validarAccion("actualizar"); });
        btnEliminar.setOnClickListener(v -> { this.validarAccion("eliminar"); });
        btnReiniciar.setOnClickListener(v -> { this.reiniciarDatos(); });
    }
}