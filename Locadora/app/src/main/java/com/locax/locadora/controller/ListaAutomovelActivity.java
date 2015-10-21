package com.locax.locadora.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.locax.locadora.R;
import com.locax.locadora.controller.MainActivity;
import com.locax.locadora.model.Automovel;
import com.locax.locadora.adapter.AutomovelAdapter;
import com.locax.locadora.network.AutomovelRequester;

import java.io.IOException;
import java.util.ArrayList;

public class ListaAutomovelActivity extends ActionBarActivity {

    ListView listView;
    Activity atividade;
    public final static String AUTOMOVEL = "com.locax.locadora";
    Automovel[] automoveis;
    ArrayList<Automovel> automoveisList;

    final String servidor = "10.0.2.2:8080";
    //ou utilizar ip do pc ex:
    //final String servidor = "192.168.0.177:8080";
    AutomovelRequester requester;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_automovel);
        atividade = this;

        //pega a mensagem do intent
        Intent intentMensagem = getIntent();
        automoveis = ((ArrayList<Automovel>)intentMensagem.getSerializableExtra(MainActivity.AUTOMOVEIS)).toArray(new Automovel[0]);


        //cria o listview de cervejas
        listView = (ListView) findViewById(R.id.view_lista_automovel);

        AutomovelAdapter adapter = new AutomovelAdapter(this, automoveis);

        listView.setAdapter(adapter);



        // listener de click em um item do listview

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                //pega o modelo do automovel que foi clicado
                final String modelo = automoveis[position].getModelo();
                final String chassi = automoveis[position].getChassi();

                requester = new AutomovelRequester();
                if(requester.isConnected(atividade)) {
                    intent = new Intent(atividade, DetalheAutomovelActivity.class);


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                automoveisList = requester.get("http://"+servidor+"/LocadoraDeVeiculos/selecaoDetalhado.json",modelo,chassi);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        intent.putExtra(AUTOMOVEL, automoveisList.get(0));
                                        startActivity(intent);
                                    }
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast toast = Toast.makeText(atividade, "Rede indisponível!", Toast.LENGTH_LONG);
                    toast.show();
                }

                startActivity(intent);

            }

        });
    }
}
