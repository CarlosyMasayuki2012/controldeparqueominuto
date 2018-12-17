package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PagarAcumuladoRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://www.mydsystems.com/estacionamientomovil/PagarEstacionamientoAcumulado.php";

    private Map<String, String> params;

    public PagarAcumuladoRequest(String Monto, String pendientepago,  String tipodocumento, String numerodocumento,String id_estacionamiento,String condicion,
                                 Response.Listener <String> listener)

    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("Monto", Monto);
        params.put("pendientepago", pendientepago);
        params.put("tipodocumento", tipodocumento);
        params.put("numerodocumento", numerodocumento);
        params.put("id_estacionamiento", id_estacionamiento);
        params.put("condicion", condicion);
    }

    public Map <String, String> getParams() {
        return params;
    }
}