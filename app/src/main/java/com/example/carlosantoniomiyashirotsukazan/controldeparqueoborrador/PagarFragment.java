package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Request.PagarFragmentRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Constantes.Constantes.Costo;


public class PagarFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PagarFragment() {

    }


    public static PagarFragment newInstance(String param1, String param2) {
        PagarFragment fragment = new PagarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button btnvalidar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final TextView tvfecha,tvplaca,tvhoraingreso,tvmonto;
        String parqueo, monto, condicion,placa,fecha_ingreso;
        final  String FechaValidacion="";
        Integer id_estacionamiento,hora_ingreso,minuto_ingreso,hora_salida,minuto_salida;

        View vista = inflater.inflate(R.layout.fragment_pagar, container, false);

        id_estacionamiento = getArguments().getInt("id_estacionamiento");
        parqueo = getArguments().getString("parqueo");
        fecha_ingreso = getArguments().getString("fecha_ingreso");
        hora_ingreso = getArguments().getInt("hora_ingreso");
        minuto_ingreso = getArguments().getInt("minuto_ingreso");
        hora_salida = getArguments().getInt("hora_salida");
        minuto_salida = getArguments().getInt("minuto_salida");
        monto = getArguments().getString("monto");
        condicion = getArguments().getString("condicion");
        placa = getArguments().getString("placa");

        Calendar fecha = Calendar.getInstance();


        hora_salida = fecha.get(Calendar.HOUR_OF_DAY);
        minuto_salida = fecha.get(Calendar.MINUTE);


        Double montopagar=0.0;
        if ((minuto_salida-minuto_ingreso)>5){

            montopagar = ((hora_salida - hora_ingreso)+1) * Costo;

        }else{

            montopagar = (hora_salida - hora_ingreso) * Costo;
            if ((hora_salida - hora_ingreso)==0){
                montopagar = Costo;
            }
        }


        final String id_estacionamiento1 = String.valueOf(id_estacionamiento);
        final String hora_salida1 = String.valueOf(hora_salida);
        final String minuto_salida1 = String.valueOf(minuto_salida);

        final String monto1 = String.valueOf(montopagar);
        tvfecha= vista.findViewById(R.id.tvFecha);
        tvplaca= vista.findViewById(R.id.tvPlaca);
        tvhoraingreso = vista.findViewById(R.id.tvHoraIngreso);
        tvmonto = vista.findViewById(R.id.tvMonto);
        // Se hace el Seteo de cada uno de los textview
        tvfecha.setText(fecha_ingreso);
        tvplaca.setText(placa);
        tvmonto.setText(monto1);
        String hh = String.valueOf(hora_ingreso +":"+ minuto_ingreso);
        tvhoraingreso.setText(hh);


        btnvalidar = vista.findViewById(R.id.btnValidarGasto);
        btnvalidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Condicion = "2";
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonresponse = new JSONObject(response);
                            boolean success = jsonresponse.getBoolean("success");

                            if (success){
/*
                                Fragment nuevoFragmento = new ListadoFragment();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.contentmain, nuevoFragmento);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                Toast.makeText(getContext(),"Actualizacion Exitosa",Toast.LENGTH_SHORT).show();
                                */

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
                String FechaVal = "";

                if (mes <= 9 ){
                    FechaVal = year + "/0"+mes;
                }else{
                    FechaVal = year + "/"+mes;
                }
                if(dia <=9){
                    FechaVal = FechaVal + "/0"+dia;
                }else{
                    FechaVal = FechaVal + "/"+dia;
                }


                PagarFragmentRequest fragmentrequest = new PagarFragmentRequest(id_estacionamiento1,Condicion,
                        hora_salida1,minuto_salida1,monto1, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(fragmentrequest);

            }
        });

        return vista;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
