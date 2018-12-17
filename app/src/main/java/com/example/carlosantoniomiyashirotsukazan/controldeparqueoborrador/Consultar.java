package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Entidades.Estacionamiento;
import com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request.CancelarAcuentaRequest;
import com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request.PagarAcumuladoRequest;
import com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request.PagoRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class Consultar extends AppCompatActivity {

    TextView tvmonto,tvmuestramonto;
    EditText etidestacionamiento,etadicional,etnumdocumento;
    String fechaingreso, fechasalida, url,condicion = "",
            tarifavehiculo,nombretipovehiculo,cadenaimpresion = "",et="",numDoc,DocumentoSeleccionado,
            montocobrado, textToPrint,PrintHoras,PrintMinutos,Mont,placa,pendientepago="0";
    Double monto = 0.0,montoNeto,IGV, AcumulaMonto = 0.00;
    Integer dia ,mes ,year,HorasTranscurridas,HorasTranscurridas1, MinutosTranscurridos,hora_ingreso,minuto_ingreso,hora_salida,minuto_salida;
    Integer horaingreso,horasalida,minutoingreso,minutosalida,horaactual,minutoactual;
    Button btnaceptar,btncancelar,btnacumular,btnacuenta,btnpagaracuenta;
    CheckBox cbchifa;
    Spinner spdocumento;
    ArrayList<Estacionamiento> listaestacionamiento;
    Estacionamiento estacionamiento;
    Boolean Cancelado=false,CondicionPago=true,acuenta = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        tvmonto = findViewById(R.id.tvmonto);
        etidestacionamiento = findViewById(R.id.etidestacionamiento);
        btnaceptar = findViewById(R.id.btnAceptar);
        btncancelar = findViewById(R.id.btnCancel);
        btnacumular = findViewById(R.id.btnacumular);
        cbchifa = findViewById(R.id.cbChifa);
        etadicional = findViewById(R.id.etAdicional);
        etnumdocumento = findViewById(R.id.etnumerodocument);
        spdocumento = findViewById(R.id.spDocumento);
        tvmuestramonto = findViewById(R.id.tvmuestramonto);
        btnacuenta = findViewById(R.id.btnacuenta);
        btnpagaracuenta  = findViewById(R.id.btnpagaracuenta);
        estacionamiento = new Estacionamiento();
        listaestacionamiento = new ArrayList<>();


        //  Calculo de la fecha y horas actuales
        Calendar fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH) + 1;
        year = fecha.get(Calendar.YEAR);
        horaactual = fecha.get(Calendar.HOUR_OF_DAY);
        minutoactual = fecha.get(Calendar.MINUTE);
        fechasalida = year.toString() + "-" + mes.toString() + "-" + dia.toString();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this,R.array.documentoopciones,R.layout.spinner_layout);
        spdocumento.setAdapter(adapter);

        etidestacionamiento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()==12) {
                    et =etidestacionamiento.getText().toString();
                    Long Auxidestacionamiento = Long.valueOf(et);
                    et = Auxidestacionamiento.toString();
                    etnumdocumento.requestFocus();
                    listar();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        spdocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DocumentoSeleccionado = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et =etidestacionamiento.getText().toString();
                Long Auxidestacionamiento = Long.valueOf(et);
                et = Auxidestacionamiento.toString();
                if (etnumdocumento.getText().toString().equals("") && listaestacionamiento.get(0).getCondicion().equals("Sin Pagar")){
                    Toast.makeText(Consultar.this, "POR FAVOR INGRESE EL NÚMERO DE LA"+ DocumentoSeleccionado, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Consultar.this, "Paso1", Toast.LENGTH_SHORT).show();
                    RegistrarPago();
                }
            }
        });
        btnacumular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et =etidestacionamiento.getText().toString();
                Long Auxidestacionamiento = Long.valueOf(et);
                et = Auxidestacionamiento.toString();

                if (etnumdocumento.getText().toString().equals("")){

                    Toast.makeText(Consultar.this, "POR FAVOR INGRESE EL NÚMERO DE LA "+ DocumentoSeleccionado, Toast.LENGTH_SHORT).show();

                }else{

                    PagarAcumulado();

                }


            }
        });
        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Consultar.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnacuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et =etidestacionamiento.getText().toString();
                Long Auxidestacionamiento = Long.valueOf(et);
                et = Auxidestacionamiento.toString();

                if (etnumdocumento.getText().toString().equals("")){

                    Toast.makeText(Consultar.this, "POR FAVOR INGRESE EL NÚMERO DE LA "+ DocumentoSeleccionado, Toast.LENGTH_SHORT).show();

                }else{

                    PagarAcuenta();

                }
            }
        });
        btnpagaracuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et =etidestacionamiento.getText().toString();
                Long Auxidestacionamiento = Long.valueOf(et);
                et = Auxidestacionamiento.toString();

                PagarTotalAcuenta();

            }
        });
    }

    private void PagarTotalAcuenta() {

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
                        Intent pantalla1 = new Intent(Consultar.this,
                                MainActivity.class);
                        startActivity(pantalla1);
                        finish();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                Consultar.this);
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

        CancelarAcuentaRequest cancelarAcuentaRequest = new CancelarAcuentaRequest(
                et,"Pagado", responseListener);
        RequestQueue queue = Volley.newRequestQueue(Consultar.this);
        queue.add(cancelarAcuentaRequest);


    }

    private void PagarAcuenta() {

        Intent pantalla1 = new Intent(Consultar.this,
                PagoAcuenta.class);
        pantalla1.putExtra("monto",monto);
        pantalla1.putExtra("et",et);
        pantalla1.putExtra("DocumentoSeleccionado",DocumentoSeleccionado);
        numDoc = etnumdocumento.getText().toString();
        pantalla1.putExtra("numDoc",numDoc);
        pantalla1.putExtra("cadenaimpresion",cadenaimpresion);
        pantalla1.putExtra("placa",listaestacionamiento.get(0).getPlaca());

        startActivity(pantalla1);
        finish();
    }

    private void PagarAcumulado() {

        montocobrado = monto.toString();
        numDoc = etnumdocumento.getText().toString();

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
                        Intent pantalla1 = new Intent(Consultar.this,
                                MainActivity.class);
                        startActivity(pantalla1);
                        finish();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                Consultar.this);
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

        PagarAcumuladoRequest pagarAcumuladoRequest = new PagarAcumuladoRequest(
                montocobrado,pendientepago,DocumentoSeleccionado,numDoc,et,"Pagado", responseListener);
        RequestQueue queue = Volley.newRequestQueue(Consultar.this);
        queue.add(pagarAcumuladoRequest);
    }

    public void listar(){

        listaestacionamiento.clear();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.mydsystems.com/estacionamientomovil/ConsultarPagoEstacionamiento1.php?id_estacionamiento="+et;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                btnaceptar.setVisibility(View.VISIBLE);
                                spdocumento.setVisibility(View.VISIBLE);
                                etnumdocumento.setVisibility(View.VISIBLE);
                            }else{
                                AlertDialog.Builder  builder = new AlertDialog.Builder(
                                        Consultar.this);
                                builder.setMessage("Ya se registró el pago")
                                        .setNegativeButton("Regresar",null)
                                        .create()
                                        .show();
                                tvmuestramonto.setText("Ya se hizo el registro del pago");
                                cbchifa.setVisibility(View.GONE);
                                etadicional.setVisibility(View.GONE);
                                btnaceptar.setVisibility(View.GONE);
                                spdocumento.setVisibility(View.GONE);
                                etnumdocumento.setVisibility(View.GONE);
                            }
                            JSONArray jsonArray=jsonObject.getJSONArray("detalle_estacionamiento");

                            cadenaimpresion = "";

                            for(int i=0;i<jsonArray.length();i++) {
                                estacionamiento = new Estacionamiento();
                                JSONObject jsonObject1 =null;
                                jsonObject1 = jsonArray.getJSONObject(i);
                                estacionamiento.setFecha_ingreso(jsonObject1.getString("fecha"));
                                estacionamiento.setFecha_salida(jsonObject1.getString("fechasalida"));
                                estacionamiento.setHora_ingreso(jsonObject1.getString("hora_ingreso"));
                                estacionamiento.setMinuto_ingreso(jsonObject1.getString("minuto_ingreso"));
                                if (jsonObject1.getString("hora_salida").equals("")){
                                    estacionamiento.setHora_salida("");
                                    CondicionPago = false;

                                }else {
                                    estacionamiento.setHora_salida(formato(Integer.valueOf(jsonObject1.getString("hora_salida"))));
                                }
                                if (jsonObject1.getString("minuto_salida").equals("")){
                                    estacionamiento.setMinuto_salida("");
                                    CondicionPago = false;

                                }else {
                                    estacionamiento.setMinuto_salida(formato(Integer.valueOf(jsonObject1.getString("minuto_salida"))));
                                }

                                estacionamiento.setCondicion(jsonObject1.getString("condicion"));
                                estacionamiento.setTarifavehiculo(jsonObject1.getString("tarifavehiculo"));
                                estacionamiento.setNombretipovehiculo(jsonObject1.getString("nombretipovehiculo"));
                                estacionamiento.setPlaca(jsonObject1.getString("placa"));
                                estacionamiento.setPendientepago(jsonObject1.getString("pendientepago"));

                                listaestacionamiento.add(estacionamiento);
                            }


                            if (listaestacionamiento.get(0).getCondicion().equals("Sin Pagar")){
                                etadicional.setVisibility(View.VISIBLE);
                                etnumdocumento.setVisibility(View.VISIBLE);
                                spdocumento.setVisibility(View.VISIBLE);
                                cbchifa.setVisibility(View.VISIBLE);
                                tvmonto.setVisibility(View.VISIBLE);
                                btnacumular.setVisibility(View.GONE);
                                tvmuestramonto.setVisibility(View.GONE);
                                btnacuenta.setVisibility(View.GONE);
                                btnaceptar.setText("Pagar");


                            }else if (listaestacionamiento.get(0).getCondicion().equals("Acumulado")) {
                                etadicional.setVisibility(View.GONE);
                                tvmuestramonto.setVisibility(View.VISIBLE);
                                etnumdocumento.setVisibility(View.VISIBLE);
                                spdocumento.setVisibility(View.VISIBLE);
                                cbchifa.setVisibility(View.GONE);
                                tvmonto.setVisibility(View.GONE);

                                if (CondicionPago){
                                btnacumular.setVisibility(View.VISIBLE);
                                btnacumular.setText("Pagar");
                                btnacuenta.setVisibility(View.VISIBLE);
                                spdocumento.setVisibility(View.VISIBLE);
                                etnumdocumento.setVisibility(View.VISIBLE);
                                btnaceptar.setText("Acumular");
                                }else{

                                    btnacumular.setVisibility(View.GONE);
                                    btnacuenta.setVisibility(View.GONE);
                                    btnaceptar.setText("Acumular");
                                    etnumdocumento.setText("---");
                                    DocumentoSeleccionado = "---";
                                    spdocumento.setVisibility(View.GONE);
                                    etnumdocumento.setVisibility(View.GONE);
                                }


                                Double acumulado = 0.00;
                                cadenaimpresion="";

                                for(Estacionamiento est : listaestacionamiento){

                                    if (!est.getHora_salida().equals("")
                                            && !est.getMinuto_salida().equals("")) {

                                        acumulado = acumulado + calculaMonto(est.getHora_ingreso(),
                                                est.getMinuto_ingreso(),
                                                est.getHora_salida(),
                                                est.getMinuto_salida(),
                                                est.getTarifavehiculo(),
                                                est.getNombretipovehiculo());

                                        cadenaimpresion = cadenaimpresion + "<LEFT> HORA DE INGRESO:"+
                                                est.getHora_ingreso() +
                                                ":" + est.getMinuto_ingreso() +"    "+"HORA DE SALIDA:"
                                                + est.getHora_salida() + ":" +
                                                est.getMinuto_salida() +"<BR>" + "<BR>";
                                        //Toast.makeText(Consultar.this, est.getHora_ingreso(), Toast.LENGTH_SHORT).show();

                                    }else{

                                        acumulado = acumulado + calculaMonto(est.getHora_ingreso(),
                                                est.getMinuto_ingreso(),
                                                formato(horaactual),
                                                formato(minutoactual),
                                                est.getTarifavehiculo(),
                                                est.getNombretipovehiculo());
                                        cadenaimpresion = cadenaimpresion + "<LEFT> HORA DE INGRESO:"+
                                                est.getHora_ingreso() +
                                                ":" + est.getMinuto_ingreso() +"    "+"HORA DE SALIDA:"
                                                + formato(horaactual) + ":" +
                                                formato(minutoactual) +"<BR>" + "<BR>";
                                        CondicionPago = true;
                                    }
                                }

                              monto = acumulado;

                                tvmuestramonto.setText("EL MONTO ES : S/ " + monto.toString()+"0");

                            }else if (listaestacionamiento.get(0).getCondicion().equals("Pendiente")){

                                tvmuestramonto.setText("FALTA PAGAR : S/ " + listaestacionamiento.get(0).getPendientepago());
                                btnpagaracuenta.setVisibility(View.VISIBLE);
                                btnaceptar.setVisibility(View.GONE);
                                spdocumento.setVisibility(View.GONE);
                                etnumdocumento.setVisibility(View.GONE);
                                acuenta=true;

                            }

                            // Ocultar partes del front de Consultar dependiendo de la condicion

                            tvmonto.setVisibility(View.GONE);

                            // Calcula el monto a Pagar

                            montoNeto = redondearDecimales(monto/1.18);
                            IGV = redondearDecimales(monto - montoNeto);




                        }catch (JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        if (condicion.equals("Pagado")){

            Intent pantalla1 = new Intent(Consultar.this,
                    Consultar.class);
            startActivity(pantalla1);
            finish();
        }

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void RegistrarPago() {

        montocobrado = monto.toString();
        numDoc = etnumdocumento.getText().toString();
        Toast.makeText(this, montocobrado, Toast.LENGTH_SHORT).show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");


                    if (success){
                        // Hace la impresion en la etiquetera USB

                        ImprimirUsb();
                        Intent pantalla1 = new Intent(Consultar.this,
                                MainActivity.class);
                        startActivity(pantalla1);
                        finish();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                Consultar.this);
                        builder.setMessage("Ya se registró el pago1")
                                .setNegativeButton("Regresar",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        if (listaestacionamiento.get(0).getCondicion().equals("Sin Pagar")){

            // Se hace el calculo para el pago simple

            if (cbchifa.isChecked()){

                monto = calculaMonto(listaestacionamiento.get(0).getHora_ingreso(),
                        listaestacionamiento.get(0).getMinuto_ingreso(),
                        horaactual.toString(),minutoactual.toString(), "1",
                        listaestacionamiento.get(0).getNombretipovehiculo());


            }else{
                monto = calculaMonto(listaestacionamiento.get(0).getHora_ingreso(),
                        listaestacionamiento.get(0).getMinuto_ingreso(),
                        horaactual.toString(),minutoactual.toString(),
                        listaestacionamiento.get(0).getTarifavehiculo(),
                        listaestacionamiento.get(0).getNombretipovehiculo());

            }
            Toast.makeText(this, monto.toString()+"ee", Toast.LENGTH_SHORT).show();

            String Aux1 = etadicional.getText().toString();
            if(!Aux1.equals("")){
                monto = monto + Double.valueOf(Aux1);
            }


            montocobrado = monto.toString();
            montoNeto = redondearDecimales(monto/1.18);
            IGV = redondearDecimales(monto - montoNeto);
        }else if(listaestacionamiento.get(0).getCondicion().equals("Acumulado")){

            montocobrado = monto.toString();
            montoNeto = redondearDecimales(monto/1.18);
            IGV = redondearDecimales(monto - montoNeto);

        }

        PagoRequest pagoRequest = new PagoRequest(fechasalida, formato(horaactual), formato(minutoactual),
                montocobrado,DocumentoSeleccionado,numDoc,et,"", responseListener);

        RequestQueue queue = Volley.newRequestQueue(Consultar.this);
        queue.add(pagoRequest);
    }
    private void ImprimirUsb(){

        if (listaestacionamiento.get(0).getCondicion().equals("Sin Pagar")) {

            if (DocumentoSeleccionado.equals("BOLETA")) {

                textToPrint = "<BIG><BOLD><CENTER>PLAYA SAN ANDRES<BR>" + "<BR>" +
                        "<CENTER> Jr. Huallaga # 839 - Cercado de Lima " + "<BR>" + "<BR>" + "<BR>" +
                        "<LEFT> PLACA:              " + listaestacionamiento.get(0).getPlaca() + "<BR>" + ".<BR>" +
                        "<LEFT> MONTO A PAGAR:   S/ " + montocobrado +"0"+ "<BR>" + ".<BR>" +
                        "<LEFT> HORA DE ENTRADA:    " + listaestacionamiento.get(0).getHora_ingreso()
                        + ":" + listaestacionamiento.get(0).getMinuto_ingreso() + " Hora(s) " + "<BR>" + "<BR>" +
                        "<LEFT> HORA DE SALIDA:     " + formato(horaactual) + ":" + formato(minutoactual) + " Hora(s) " + "<BR>" + "<BR>" +
                        "<LEFT> TIEMPO TRANSCURRIDO:" + formato(HorasTranscurridas1) + ":" + formato(MinutosTranscurridos) + " Hora(s) " + "<BR>" + "<BR>" + "<BR>" +
                                "<CUT>";
            } else {

                textToPrint = "<BIG><BOLD><CENTER>PLAYA SAN ANDRES<BR>" + "<BR>" +
                        "<CENTER> Jr. Huallaga # 839 - Cercado de Lima " + "<BR>" + "<BR>" + "<BR>" +
                        "<LEFT> PLACA:              " + listaestacionamiento.get(0).getPlaca() + "<BR>" + ".<BR>" +
                        "<LEFT> MONTO A PAGAR:   S/ " + montocobrado +"0"+ "<BR>" + ".<BR>" +
                        "<LEFT> SUBTOTAL:        S/ " + montoNeto.toString() +  "<BR>" + "<BR>" +
                        "<LEFT> IGV:             S/ " + IGV.toString()  + "<BR>" + "<BR>" +
                        "<LEFT> HORA DE ENTRADA:    " + listaestacionamiento.get(0).getHora_ingreso()
                        + ":" + listaestacionamiento.get(0).getMinuto_ingreso() + " Hora(s) " + "<BR>" + "<BR>" +
                        "<LEFT> HORA DE SALIDA:     " + formato(horaactual)+ ":" + formato(minutoactual) + " Hora(s) " + "<BR>" + "<BR>" +
                        "<LEFT> TIEMPO TRANSCURRIDO:" + formato(HorasTranscurridas1) + ":" + formato(MinutosTranscurridos) + " Hora(s) " + "<BR>" + "<BR>" + "<BR>" +
                        "<CUT>";
            }


        }else if(listaestacionamiento.get(0).getCondicion().equals("Acumulado")){

            if (DocumentoSeleccionado.equals("BOLETA")) {

                textToPrint = "<BIG><BOLD><CENTER>PLAYA SAN ANDRES<BR>" + "<BR>" +
                        "<CENTER> Jr. Huallaga # 839 - Cercado de Lima " + "<BR>" + "<BR>" + "<BR>" +
                        "<LEFT> PLACA:              " + listaestacionamiento.get(0).getPlaca() + "<BR>" + ".<BR>" +
                        "<LEFT> MONTO A PAGAR:       S/ " + montocobrado+"0" + "<BR>" + "<BR>" +
                        cadenaimpresion + "<BR>"  ;

                if (Cancelado){

                    textToPrint = textToPrint + "<LEFT> El Monto total a Pagar es de : " + montocobrado + "<BR>" + "<BR>";

                }
                textToPrint = textToPrint + "<CUT>";
            } else {

                textToPrint = "<BIG><BOLD><CENTER>PLAYA SAN ANDRES<BR>" + "<BR>" +
                        "<CENTER> Jr. Huallaga # 839 - Cercado de Lima " + "<BR>" + "<BR>" + "<BR>" +
                        "<LEFT> PLACA:              " + listaestacionamiento.get(0).getPlaca()  + "<BR>" + ".<BR>" +
                        "<LEFT> SUBTOTAL:            S/ " + montoNeto + "<BR>" + "<BR>" +
                        "<LEFT> IGV:                 S/ " + IGV.toString() + "<BR>" + "<BR>" +
                        "<LEFT> MONTO A PAGAR:       S/ " + montocobrado+"0" + "<BR>" + "<BR>" +
                        cadenaimpresion + "<BR>"  ;

                if (Cancelado){

                    textToPrint = textToPrint + "<LEFT> El Monto total a Pagar es de : " + montocobrado + "<BR>" + "<BR>";

                }
                textToPrint = textToPrint + "<CUT>";
            }
        }else if(listaestacionamiento.get(0).getCondicion().equals("Pendiente")){

            textToPrint = "<BIG><BOLD><CENTER>PLAYA SAN ANDRES<BR>" + "<BR>" +
                    "<CENTER> Jr. Huallaga # 839 - Cercado de Lima " + "<BR>" + "<BR>" + "<BR>" +
                    "<LEFT> PLACA:              " + listaestacionamiento.get(0).getPlaca()  + "<BR>" + ".<BR>"+
                    "<LEFT> SE HIZO LA CANCELACION DE: " + listaestacionamiento.get(0).getPendientepago()  + "<BR>" + ".<BR>";
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
    public Double calculaMonto(String HorIng,String MinIng,String HorSal,String MinSal,
                               String tarifaString,String tipoVehiculo) {

        Integer HoraIngreso = Integer.valueOf(HorIng);
        Integer MinutoIngreso = Integer.valueOf(MinIng);
        Integer HoraSalida = Integer.valueOf(HorSal);
        Integer MinutoSalida = Integer.valueOf(MinSal);
        Double tarifa = Double.valueOf(tarifaString);
        Double MontoCalculado;

        if (tipoVehiculo.equals("TARIFA PLANA (35 SOLES)")){

            MontoCalculado = 35.00;
            if( HoraSalida >= 20 && MinutoSalida >0){
                MontoCalculado = MontoCalculado + calculaMontoPlana("20","0",HorSal,MinSal,
                        "5.00");
            }


        }else if (tipoVehiculo.equals("TARIFA PLANA (25 SOLES)")){

            MontoCalculado = 25.00;
            if( HoraSalida >= 20 && MinutoSalida >=0){

                MontoCalculado = MontoCalculado + calculaMontoPlana("20","0",HorSal,MinSal,
                        "5.00");

            }

        }else if (tipoVehiculo.equals("TARIFA NOCTURNA")){

            MontoCalculado = 20.00;
            if( HoraSalida >= 8 && MinutoSalida >0){

                MontoCalculado = MontoCalculado + calculaMontoPlana("8","0",HorSal,MinSal,
                        "5.00");
            }

        }else {
            Integer DiferenciaMinutos = (HoraSalida * 60 + MinutoSalida) - (HoraIngreso * 60 + MinutoIngreso);

                MontoCalculado = DiferenciaMinutos * tarifa;
                Mont = MontoCalculado.toString();
        }

        return MontoCalculado;
    }

    public Double calculaMontoPlana(String HorIng,String MinIng,String HorSal,String MinSal,
                                    String tarifaString) {

        Integer HoraIngreso = Integer.valueOf(HorIng);
        Integer MinutoIngreso = Integer.valueOf(MinIng);
        Integer HoraSalida = Integer.valueOf(HorSal);
        Integer MinutoSalida = Integer.valueOf(MinSal);
        Double tarifa = Double.valueOf(tarifaString);
        Double MontoCalculado;

        Integer DiferenciaMinutos = (HoraSalida * 60 + MinutoSalida) - (HoraIngreso * 60 + MinutoIngreso);

            MontoCalculado = DiferenciaMinutos * tarifa;

        return MontoCalculado;
    }

    public String formato (Integer fechahora){

        String ValorFormato;

        ValorFormato = fechahora.toString();

        if (fechahora <=9){

            ValorFormato = "0" + ValorFormato;
        }
        return ValorFormato;
    }
    public static double redondearDecimales(double valorInicial) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*100;
        resultado=Math.round(resultado);
        resultado=(resultado/100)+parteEntera;

        return resultado;
    }
}
