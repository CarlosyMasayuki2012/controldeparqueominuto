package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ListarVehiculosRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://www.mydsystems.com/pagarEstacionamiento1.php";

    private Map<String, String> params;

    public ListarVehiculosRequest( String tipovehiculoporplaca,
                       Response.Listener <String> listener)
    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("tipovehiculoporplaca", tipovehiculoporplaca);
    }

    public Map <String, String> getParams() {
        return params;
    }
}