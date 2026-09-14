package com.example.store;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {
    EditText edtNombre, edtCategoria, edtDescripcion, edtGarantia, edtPrecio, edtStock;
    Button btnGuardar;

    // 1. Objeto que sirva como canal de comunicación
    RequestQueue requestQueue;

    // 2. EndPoint (dirección que apunta al WS)
    private final String URL = "http://10.246.96.40:3000/productos";

    private void loadUI() {
        edtNombre = findViewById(R.id.edtNombre);
        edtCategoria = findViewById(R.id.edtCategoria);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtGarantia = findViewById(R.id.edtGarantia);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtStock = findViewById(R.id.edtStock);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Desactivamos el botón al iniciar
        btnGuardar.setEnabled(false);

        // Detector de los campos
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                verificarCamposLlenos();
            }
        };

        edtNombre.addTextChangedListener(textWatcher);
        edtCategoria.addTextChangedListener(textWatcher);
        edtDescripcion.addTextChangedListener(textWatcher);
        edtGarantia.addTextChangedListener(textWatcher);
        edtPrecio.addTextChangedListener(textWatcher);
        edtStock.addTextChangedListener(textWatcher);
    }

    // Verificamos que los campos estén completos
    private void verificarCamposLlenos() {
        String nombre = edtNombre.getText().toString().trim();
        String categoria = edtCategoria.getText().toString().trim();
        String descripcion = edtDescripcion.getText().toString().trim();
        String garantia = edtGarantia.getText().toString().trim();
        String precio = edtPrecio.getText().toString().trim();
        String stock = edtStock.getText().toString().trim();

        boolean todosLLenos = !nombre.isEmpty() && !categoria.isEmpty() && !descripcion.isEmpty() && !garantia.isEmpty() && !precio.isEmpty() && !stock.isEmpty();
        btnGuardar.setEnabled(todosLLenos);
    }

    // Envía los datos ingresados en el formulario a la BD a través del WebService
    private void registrarProducto() {
        // Habilitar el canal
        requestQueue = Volley.newRequestQueue(this);

        // 3. ¿Qué dato necesita el WS? - Rpta: JSON
        JSONObject jsonObject = new JSONObject();

        // 4. Asignar datos al JSON
        try {
            jsonObject.put("nombre", edtNombre.getText().toString());
            jsonObject.put("categoria", edtCategoria.getText().toString());
            jsonObject.put("descripcion", edtDescripcion.getText().toString());
            jsonObject.put("garantia", edtGarantia.getText().toString());
            jsonObject.put("precio", edtPrecio.getText().toString());
            jsonObject.put("stock", edtStock.getText().toString());
        } catch (JSONException e) {
            Log.e("Error_JSON", e.toString());
            throw new RuntimeException(e);
        }

        // 5. ¿Qué método utilizaré para enviar los datos? - Rpta: POST
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String mensaje = jsonObject.getString("message");
                            int id = jsonObject.getInt("id");

                            Toast.makeText(getApplicationContext(), mensaje + " - ID: " + id, Toast.LENGTH_SHORT).show();
                            reiniciarDatos();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error al guardar producto", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // 6. Enviar los datos al WS
        requestQueue.add(jsonObjectRequest);
    }

    private void reiniciarDatos() {
        edtNombre.setText("");
        edtCategoria.setText("");
        edtDescripcion.setText("");
        edtGarantia.setText("");
        edtPrecio.setText("");
        edtStock.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        this.loadUI();

        btnGuardar.setOnClickListener(v -> { this.registrarProducto(); });
    }
}