package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlosantoniomiyashirotsukazan on 2/04/18.
 */

public class PagarFragmentRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL =
            "http://www.mydsystems.com/pagarEstacionamiento.php";

    private Map<String,String> params;

    public PagarFragmentRequest(String Id_Estacionamiento,String Condicion,String Hora_Salida,String Minuto_Salida,String Monto, Response.Listener<String>listener)

    {
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();

        params.put("Id_Estacionamiento",Id_Estacionamiento);
        params.put("Condicion",Condicion);
        params.put("Hora_Salida",Hora_Salida);
        params.put("Minuto_Salida",Minuto_Salida);
        params.put("Monto",Monto);

    }
    public Map<String,String> getParams(){
        return params;
    }
}