package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class ReimpresionActivity extends AppCompatActivity {

    RecyclerView rvreimpresiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reimpresion);

        rvreimpresiones = findViewById(R.id.rvReimpresion);


    }
}
