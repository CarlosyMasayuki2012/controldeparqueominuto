package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PagoRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://www.mydsystems.com/estacionamientomovil/PagarEstacionamiento.php";

    private Map<String, String> params;

    public PagoRequest(String fechasalida,String hora_salida, String minuto_salida,
                       String Monto,  String tipodocumento, String numerodocumento,String id_estacionamiento,
                       String pendientepago,Response.Listener<String>listener)

    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("fechasalida", 	fechasalida);
        params.put("hora_salida", 	hora_salida);
        params.put("minuto_salida", minuto_salida );
        params.put("Monto", Monto);
        params.put("tipodocumento", tipodocumento);
        params.put("numerodocumento", numerodocumento);
        params.put("pendientepago", pendientepago);
        params.put("id_estacionamiento", id_estacionamiento);
    }

    public Map <String, String> getParams() {
        return params;
    }
}