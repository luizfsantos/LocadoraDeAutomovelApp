package com.locax.locadora.controller;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.locax.locadora.R;
import com.locax.locadora.model.Automovel;
import com.locax.locadora.network.AutomovelRequester;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class DetalheAutomovelActivity extends ActionBarActivity {

    TextView automovelModelo;
    TextView automovelFabricante;
    TextView automovelTarifaLivre;
    TextView automovelControladoInicial;
    TextView automovelControladoKm;
    ImageView automovelImageView;
    AutomovelRequester requester;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_automovel);

        Intent intent = getIntent();
        final Automovel automovel = (Automovel)intent.getSerializableExtra(ListaAutomovelActivity.AUTOMOVEL);
        setupViews(automovel);

       requester = new AutomovelRequester();
        if(requester.isConnected(this)) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mProgress.setVisibility(View.VISIBLE);
                        final Bitmap img = requester.getImage(automovel.getNomeImagem());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                automovelImageView.setImageBitmap(img);
                                mProgress.setVisibility(View.INVISIBLE);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.padrao);
            automovelImageView.setImageDrawable(drawable);
            Toast toast = Toast.makeText(this, "Rede indisponível!", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    private void setupViews(Automovel automovel) {
        automovelModelo = (TextView) findViewById(R.id.txt_automovel_modelo);
        automovelModelo.setText("Modelo: "+automovel.getModelo());
        automovelFabricante = (TextView) findViewById(R.id.txt_automovel_fabricante);
        automovelFabricante.setText("Fabricante: "+automovel.getFabricante());
        Locale locale = new Locale("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        automovelTarifaLivre = (TextView) findViewById(R.id.txt_automovel_tarifaLivre);
        automovelTarifaLivre.setText("Tarifa de Km livre: "+ formatter.format(automovel.getTarifaLivre()));
        automovelControladoInicial = (TextView) findViewById(R.id.txt_automovel_controladoIncial);
        automovelControladoInicial.setText("Diaria: "+formatter.format(automovel.getControladoInicial()));
        automovelControladoKm = (TextView) findViewById(R.id.txt_automovel_controladoKm);
        automovelControladoKm.setText("KM: " + formatter.format(automovel.getControladoKm()));
        automovelImageView = (ImageView) findViewById(R.id.automovel_image_view);
        mProgress = (ProgressBar) findViewById(R.id.carregando_automovel);
        mProgress.setVisibility(View.INVISIBLE);
    }
}
