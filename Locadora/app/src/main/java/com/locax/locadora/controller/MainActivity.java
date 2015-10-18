package com.locax.locadora.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.locax.locadora.R;
import com.locax.locadora.model.Automovel;
import com.locax.locadora.network.AutomovelRequester;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    Spinner spinnerCidades;
    Button btnConsultar;
    String cidades;
    ArrayList<Automovel> automoveis;
    //utilizar ip do pc
    final String servidor = "192.168.0.155:8080";
    //final String servidor = "10.0.2.2:8080";
    AutomovelRequester requester;
    ProgressBar mProgress;
    Intent intent;
    ArrayList<String> chassis;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

    }

    private void setupViews() {
        cidades = "";
        btnConsultar = (Button) findViewById(R.id.botao_enviar);
        spinnerCidades = (Spinner) findViewById(R.id.dropdown_cidades);
        spinnerCidades.setOnItemSelectedListener(new CidadeSelecionado());
        mProgress = (ProgressBar) findViewById(R.id.carregando);
        mProgress.setVisibility(View.INVISIBLE);

    }

    private class CidadeSelecionado implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            cidades = (String) parent.getItemAtPosition(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }



    // constante static para identificar o parametro
    public final static String AUTOMOVEIS = "com.locax.locadora";
    //será chamado quando o usuário clicar em enviar
    public void consultarAutomoveis(View view) {
        final String pCidade = this.cidades.equals("Escolha a cidade")?"":cidades;

        requester = new AutomovelRequester();
        if(requester.isConnected(this)) {
            intent = new Intent(this, ListaAutomovelActivity.class);
            mProgress.setVisibility(View.VISIBLE);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        automoveis = requester.get("http://"+servidor+"/LocadoraDeVeiculos/selecao.json", pCidade);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                intent.putExtra(AUTOMOVEIS, automoveis);
                                mProgress.setVisibility(View.INVISIBLE);
                                startActivity(intent);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Toast toast = Toast.makeText(this, "Rede indisponível!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}
