package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

public class ReimpresionActivity extends AppCompatActivity {

    RecyclerView rvreimpresiones;
    Button btnCancelarrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reimpresion);

        rvreimpresiones = findViewById(R.id.rvReimpresion);
        btnCancelarrv = findViewById(R.id.btnCancelarRecycler);


    }
}
