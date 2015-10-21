package com.locax.locadora.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;

import com.locax.locadora.model.Automovel;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class AutomovelRequester {
    OkHttpClient client = new OkHttpClient();

    public ArrayList<Automovel> get(String url, String pCidade) throws IOException {

        ArrayList<Automovel> lista = new ArrayList<>();

        //acentuacao nao funciona se mandar via get, mesmo usando URLEncode.encode(String,UTF-8)
        if(pCidade.equals("Sâo Paulo")){
            pCidade = "Sao Paulo";
        }
        RequestBody formBody = new FormEncodingBuilder()
                .add("cidade", pCidade)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        //Response response = client.newCall(request).execute();
        long spend = 100000;
        client.setConnectTimeout(spend,TimeUnit.MILLISECONDS);
        Response response = client.newCall(request).execute();
        String jsonStr = response.body().string();

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

        try {
            JSONArray root = new JSONArray(jsonStr);
            JSONObject item = null;
            for (int i = 0; i < root.length(); i++ ) {
                item = (JSONObject)root.get(i);

                String chassi = item.getString("chassi");
                String modelo = item.getString("modelo");
                String fabricante = item.getString("fabricante");
                String nomeImagem = "padrao.png";

                lista.add(new Automovel(chassi,modelo,fabricante,nomeImagem));
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
        finally {
            if(lista.size() == 0)
                lista.add(new Automovel("erro","erro","erro","padrao.png"));
            //Log.v("AutomovelRequester", jsonStr);
        }
        return lista;
    }

    public ArrayList<Automovel> get(String url,String pModelo, String pChassi) throws IOException {

        ArrayList<Automovel> lista = new ArrayList<>();

        //acentuacao nao funciona se mandar via get, mesmo usando URLEncode.encode(String,UTF-8)
        RequestBody formBody = new FormEncodingBuilder()
                .add("modelo", pModelo)
                .add("chassi", pChassi)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        //Response response = client.newCall(request).execute();
        long spend = 100000;
        client.setConnectTimeout(spend,TimeUnit.MILLISECONDS);
        Response response = client.newCall(request).execute();
        String jsonStr = response.body().string();

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

        try {
            JSONArray root = new JSONArray(jsonStr);
            JSONObject item = null;
            for (int i = 0; i < root.length(); i++ ) {
                item = (JSONObject)root.get(i);

                String modelo = item.getString("modelo");
                String fabricante = item.getString("fabricante");
                double tarifaLivre = item.getDouble("tarifaLivre");
                double controladoInicial = item.getDouble("controladoInicial");
                double controladoKm = item.getDouble("controladoKm");
                String nomeImagem = item.getString("nomeImagem");

                lista.add(new Automovel(modelo,fabricante,tarifaLivre,controladoInicial,controladoKm,nomeImagem));
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
        finally {
            if(lista.size() == 0)
                lista.add(new Automovel(pModelo,"Falhou",0.00,0.00,0.00,"garrafa_vazia.png"));
            //Log.v("AutomovelRequester", jsonStr);
        }
        return lista;
    }


    public Bitmap getImage(String url) throws IOException {

        Bitmap img = null;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        InputStream is = response.body().byteStream();

        img = BitmapFactory.decodeStream(is);

        is.close();

        return img;
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
