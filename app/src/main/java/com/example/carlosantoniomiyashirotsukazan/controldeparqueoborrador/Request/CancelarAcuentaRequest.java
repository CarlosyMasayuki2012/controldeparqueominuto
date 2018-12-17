package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CancelarAcuentaRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://www.mydsystems.com/estacionamientomovil/PagarEstacionamientoAcumulado1.php";

    private Map<String, String> params;

    public CancelarAcuentaRequest(String id_estacionamiento,String condicion,
                                 Response.Listener <String> listener)

    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("id_estacionamiento", id_estacionamiento);
        params.put("condicion", condicion);
    }

    public Map <String, String> getParams() {
        return params;
    }
}