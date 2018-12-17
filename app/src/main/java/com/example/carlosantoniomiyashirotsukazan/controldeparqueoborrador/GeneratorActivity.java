package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GeneratorActivity extends AppCompatActivity {

    EditText etplaca;
    Button btngenerar;
    ImageView ivimqr;
    String text2Qr;
    //public static Bitmap btmap=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        etplaca = findViewById(R.id.etPlaca);
        btngenerar = findViewById(R.id.btnGenerar);
        ivimqr = findViewById(R.id.ivQr);


        btngenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QrGenerator();

            }
        });
    }

    private void QrGenerator() {


        text2Qr = etplaca.getText().toString().trim();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr,
                    BarcodeFormat.QR_CODE,270,270);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            Bitmap btmap = bitmap;
            ivimqr.setImageBitmap(bitmap);

        }catch (WriterException e){

            Toast.makeText(getApplicationContext(),e.toString()
                    ,Toast.LENGTH_SHORT).show();

        }
    }


}
