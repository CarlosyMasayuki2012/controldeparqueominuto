package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador;

import android.app.VoiceInteractor;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.FontRequest;
import android.renderscript.Sampler;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Entidades.TipoVehiculo;
import com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request.RegistroDetalleRequest;
import com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request.RegistroRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


//import static com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.GeneratorActivity.btmap;

public class RegistroActivity extends AppCompatActivity {

    public EditText etplaca;
    public String placa,Monto = "",Fecha_Ingreso,hh,Id_Estacionamiento="0",documento = "",numdocumento = "",
            idString = "", nombreVehiculo,id_tarifa,condicion, Hora_Salida, Minuto_Salida,pendientepago="";
    public Integer Hora_Ingreso, Minuto_Ingreso, TipoVehiculo;
    public String DAY, MONTH, YEAR,HHII,MMII;
    private Spinner spinner;
    ArrayList<String> tarifaVehiculo,montoTarifa,Id_TipoV;
    public String tarifa;
    public CheckBox cbtarifa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Id_TipoV = new ArrayList<>();
        tarifaVehiculo =new ArrayList<>();
        montoTarifa = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.sppFrutas);
        etplaca = findViewById(R.id.etPlaca);
        cbtarifa = findViewById(R.id.cbClienteEspecial);
        etplaca.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        listar();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            nombreVehiculo = tarifaVehiculo.get(i);
            tarifa = montoTarifa.get(i);
            id_tarifa = Id_TipoV.get(i);
            TipoVehiculo = Integer.valueOf(id_tarifa);

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        TecleaEdittext();
       // CheckTarifaplana();
    }

    private void TecleaEdittext() {

        etplaca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length() > 3){

                    etplaca.setInputType(2);

                }else{

                    etplaca.setInputType(4096);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void listar(){

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        tarifaVehiculo.clear();
        montoTarifa.clear();
        Id_TipoV.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://www.mydsystems.com/estacionamientomovil/ConsultaTarifa.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("tipovehiculo");
                            tarifaVehiculo.clear();
                            montoTarifa.clear();
                            Id_TipoV.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String tipoVehiculo=jsonObject1.getString("nombretipovehiculo");
                                String montotarifa=jsonObject1.getString("tarifavehiculo");
                                String id_TipoVehiculo=jsonObject1.getString("id_tipovehiculo");
                                tarifaVehiculo.add(tipoVehiculo);
                                montoTarifa.add(montotarifa);
                                Id_TipoV.add(id_TipoVehiculo);
                            }
                            spinner.setAdapter(new ArrayAdapter<String>(RegistroActivity.this, R.layout.spinner_layout, tarifaVehiculo));
                        }catch (JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    public void onclick(View view) {

        placa = etplaca.getText().toString();
        switch (view.getId()){
            case R.id.btnRegistrar:
                // Se tiene que definir las variables
                if (placa.length() == 7){
                    RegistrarEgreso();
                }else {
                    Toast.makeText(getApplicationContext(),"Ingrese la Placa correctamente",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnCancelar:
                cancelargasto();
                break;
        }
    }

    private void RegistrarEgreso() {

        if (cbtarifa.isChecked()){

            condicion = "Acumulado";

        }else{

            condicion = "Sin Pagar";

        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");
                    Id_Estacionamiento = jsonresponse.getString("id_estacionamiento");
                    etplaca.setText("");
                    Toast.makeText(getApplicationContext(),"sss",Toast.LENGTH_LONG).show();
                    if (success){

                        // Hace la impresion en la etiquetera USB
                        ImprimirUsb();
                        Intent pantalla1 = new Intent(RegistroActivity.this,
                                MainActivity.class);
                        startActivity(pantalla1);
                        finish();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                RegistroActivity.this);
                        builder.setMessage("No se pudo registrar el Vehiculo")
                                .setNegativeButton("Regresar",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Calendar fecha = Calendar.getInstance();
        Integer dia = fecha.get(Calendar.DAY_OF_MONTH);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer year = fecha.get(Calendar.YEAR);
        Hora_Ingreso = fecha.get(Calendar.HOUR_OF_DAY);
        Minuto_Ingreso = fecha.get(Calendar.MINUTE);

        DAY = formato(dia);
        MONTH = formato(mes);
        YEAR = formato(year);
        HHII = formato(Hora_Ingreso);
        MMII = formato(Minuto_Ingreso);

        Fecha_Ingreso = YEAR +"-"+MONTH+"-"+DAY;

        Monto = "";
        Hora_Salida ="";
        Minuto_Salida = "";

        RegistroRequest registrorequest = new RegistroRequest(
                 Monto,pendientepago, condicion,placa,documento,numdocumento,TipoVehiculo,Fecha_Ingreso,HHII,MMII,
                Hora_Salida,Minuto_Salida, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegistroActivity.this);
        queue.add(registrorequest);

    }

    public String formato (Integer fechahora){

        String ValorFormato;

        ValorFormato = fechahora.toString();

        if (fechahora <=9){

            ValorFormato = "0" + ValorFormato;
        }
        return ValorFormato;
    }


    private void cancelargasto() {

        Toast.makeText(getApplicationContext(),
                "se ha cancelado",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RegistroActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void ImprimirUsb(){

        idString += Id_Estacionamiento;

        while (idString.length() < 12){

            idString = "0"+ idString;

        }

        Double montoTarifa = Double.parseDouble(tarifa);
        montoTarifa = montoTarifa*10;
        Integer Aux =  (int)montoTarifa.doubleValue();
        tarifa = Aux.toString();

        Toast.makeText(getApplicationContext(),tarifa,Toast.LENGTH_LONG).show();
        String textToPrint = "<BIG><BOLD><CENTER>PLAYA SAN ANDRES<BR>" + "<BR>" +
                "<CENTER> Jr. Huallaga # 839 - Cercado de Lima "  + "<BR>" + "<BR>" +
                "<LEFT>FECHA DE INGRESO: " + Fecha_Ingreso + "<BR>" + "<BR>" +
                "<LEFT>HORA DE INGRESO:  "+ HHII + ":" + MMII +"  Hora(s) "+"<BR>" + "<BR>" +
                "<LEFT>PLACA:            " + placa + "<BR>" + "<BR>" +
                "<LEFT>Tipo Vehiculo  :  " + nombreVehiculo + "<BR>" + "<BR>" +"<BR>" +
                "<LEFT> - La perdida de este documento es la aceptacion del pago de S/5."+"<BR>" + "<BR>" +
                "<LEFT> - La tolerancia es de 10 minutos."+"<BR>" +

                "<BARCODE128>"+ idString +"<BR>"+"<CUT>";
        textToPrint = textToPrint+textToPrint;

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
