package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlosantoniomiyashirotsukazan on 28/03/18.
 */

public class RegistroRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://www.mydsystems.com/estacionamientomovil/RegistroEstacionamiento.php";

    private Map<String, String> params;

    public RegistroRequest( String monto, String pendientepago, String condicion,
                                   String placa, String tipodocumento, String numerodocumento,Integer tipovehiculo_id, String fecha,
                            String hora_ingreso,String minuto_ingreso,String hora_salida,String minuto_salida,
                                   Response.Listener<String> listener)

    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("monto", monto);
        params.put("pendientepago", pendientepago);
        params.put("condicion", condicion);
        params.put("placa", placa);
        params.put("tipodocumento", tipodocumento);
        params.put("numerodocumento", numerodocumento);
        params.put("tipovehiculo_id", tipovehiculo_id+"");
        params.put("fecha", fecha);
        params.put("hora_ingreso", hora_ingreso);
        params.put("minuto_ingreso", minuto_ingreso);
        params.put("hora_salida", hora_salida);
        params.put("minuto_salida", minuto_salida);

    }

    public Map<String, String> getParams() {
        return params;
    }
}

