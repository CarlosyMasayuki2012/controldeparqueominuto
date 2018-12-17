package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlosantoniomiyashirotsukazan on 1/04/18.
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://www.mydsystems.com/estacionamientomovil/LoginSystem.php";

    private Map<String,String> params;

    public LoginRequest(String usuario, String clave, Response.Listener<String>listener)

    {
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("Usuario",usuario);
        params.put("Clave",clave);
    }
    public Map<String,String> getParams(){
        return params;
    }
}
