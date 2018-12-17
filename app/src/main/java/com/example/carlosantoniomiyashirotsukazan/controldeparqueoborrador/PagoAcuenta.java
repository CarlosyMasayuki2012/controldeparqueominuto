package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request.PagarAcumuladoRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Consultar.redondearDecimales;

public class PagoAcuenta extends AppCompatActivity {

    EditText etmontopagoacuenta;
    Button btnpagoacuenta,btncancelaracumulado;
    Double monto,montoNeto,IGV,diferencia;
    String et,montocobrado,pendientepago,DocumentoSeleccionado,numDoc,textToPrint,cadenaimpresion,placa;
    Boolean Cancelado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_acuenta);

        etmontopagoacuenta = findViewById(R.id.etmontoacuenta);
        btnpagoacuenta = findViewById(R.id.btnpagaracuenta);
        btncancelaracumulado= findViewById(R.id.btncancelaracumulado);

        monto = getIntent().getExtras().getDouble("monto");
        et = getIntent().getExtras().getString("et");
        DocumentoSeleccionado = getIntent().getExtras().getString("DocumentoSeleccionado");
        numDoc = getIntent().getExtras().getString("numDoc");
        cadenaimpresion = getIntent().getExtras().getString("cadenaimpresion");
        placa = getIntent().getExtras().getString("placa");

        btnpagoacuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etmontopagoacuenta.getText().toString().equals("")){

                    Toast.makeText(PagoAcuenta.this, "Por favor ingrese el monto", Toast.LENGTH_SHORT).show();
                }else {
                    PagarACuenta();
                }

            }
        });
        btncancelaracumulado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PagoAcuenta.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void PagarACuenta() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");


                    if (success){
                        // Hace la impresion en la etiquetera USB

                        Cancelado = true;

                        ImprimirUsb();
                        Intent pantalla1 = new Intent(PagoAcuenta.this,
                                MainActivity.class);
                        startActivity(pantalla1);
                        finish();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                PagoAcuenta.this);
                        builder.setMessage("Ya se registró el pago")
                                .setNegativeButton("Regresar",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        montocobrado = monto.toString();
        montoNeto = redondearDecimales(monto/1.18);
        IGV = redondearDecimales(monto - montoNeto);
        diferencia = redondearDecimales(monto - Double.valueOf(etmontopagoacuenta.getText().toString()));
        pendientepago = diferencia.toString();
        Double Aux = redondearDecimales(Double.valueOf(etmontopagoacuenta.getText().toString()));
        montocobrado = Aux.toString();
        Toast.makeText(this, montocobrado, Toast.LENGTH_SHORT).show();

        PagarAcumuladoRequest pagarAcumuladoRequest = new PagarAcumuladoRequest(
                montocobrado,pendientepago,DocumentoSeleccionado,numDoc,et,"Pendiente", responseListener);

        RequestQueue queue = Volley.newRequestQueue(PagoAcuenta.this);
        queue.add(pagarAcumuladoRequest);

    }

    private void ImprimirUsb(){


            if (DocumentoSeleccionado.equals("BOLETA")) {

                textToPrint = "<BIG><BOLD><CENTER>PLAYA SAN ANDRES<BR>" + "<BR>" +
                        "<CENTER> Jr. Huallaga # 839 - Cercado de Lima " + "<BR>" + "<BR>" + "<BR>" +
                        "<LEFT> PLACA:              " + placa + "<BR>" + ".<BR>" +
                        "<LEFT> MONTO A PAGAR:       S/ " + monto+"0" + "<BR>" + "<BR>" +
                        cadenaimpresion + "<BR>"  ;

                if (Cancelado){

                    textToPrint = textToPrint + "<LEFT> EL MONTO PARCIAL PAGADO ES DE :  S/ " + montocobrado + "<BR>" + "<BR>"+
                            "<LEFT> EL MONTO PENDIENTE A PAGAR ES DE :  S/ " + pendientepago + "<BR>" + "<BR>";

                }
                textToPrint = textToPrint + "<CUT>";
            } else {

                textToPrint = "<BIG><BOLD><CENTER>PLAYA SAN ANDRES<BR>" + "<BR>" +
                        "<CENTER> Jr. Huallaga # 839 - Cercado de Lima " + "<BR>" + "<BR>" + "<BR>" +
                        "<LEFT> PLACA:              " + placa  + "<BR>" + ".<BR>" +
                        "<LEFT> SUBTOTAL:            S/ " + montoNeto + "<BR>" + "<BR>" +
                        "<LEFT> IGV:                 S/ " + IGV.toString() + "<BR>" + "<BR>" +
                        "<LEFT> MONTO A PAGAR:       S/ " + monto+"0" + "<BR>" + "<BR>" +
                        cadenaimpresion + "<BR>"  ;

                if (Cancelado){

                    textToPrint = textToPrint + "<LEFT> EL MONTO PARCIAL PAGADO ES DE :  S/ " + montocobrado + "<BR>" + "<BR>"+
                            "<LEFT> EL MONTO PENDIENTE A PAGAR ES DE :  S/ " + pendientepago + "<BR>" + "<BR>";

                }
                textToPrint = textToPrint + "<CUT>";
            }


        try {

            Intent intent = new Intent("pe.diegoveloper.printing");
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, textToPrint);
            startActivity(intent);

        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=pe.diegoveloper.printerserverapp"));
            startActivity(intent);
        }
    }
}
