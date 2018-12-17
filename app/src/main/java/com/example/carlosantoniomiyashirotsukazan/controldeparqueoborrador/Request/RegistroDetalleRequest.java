package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroDetalleRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://www.mydsystems.com/estacionamientomovil/RegistroDetalleEstacionamiento.php";

    private Map<String, String> params;

    public RegistroDetalleRequest( String fecha_ingreso, String hora_ingreso,
                           String minuto_ingreso, String hora_salida, String minuto_salida,Integer estacionamiento_id,
                           Response.Listener<String> listener)

    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("fecha_ingreso", fecha_ingreso);
        params.put("hora_ingreso", hora_ingreso + "");
        params.put("minuto_ingreso", minuto_ingreso + "");
        params.put("hora_salida", hora_salida + "");
        params.put("minuto_salida", minuto_salida + "");
        params.put("estacionamiento_id", estacionamiento_id + "");

    }

    public Map<String, String> getParams() {
        return params;
    }
}



