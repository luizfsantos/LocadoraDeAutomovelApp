package com.locax.locadora.util;

import android.widget.ImageView;
import android.widget.TextView;


public class ViewHolder {
    private ImageView fotoAutomovel;
    private TextView modeloAutomovel, detalhesAutomovel;

    public ViewHolder(ImageView fotoAutomovel, TextView modeloAutomovel, TextView detalhesAutomovel) {
        this.fotoAutomovel = fotoAutomovel;
        this.modeloAutomovel = modeloAutomovel;
        this.detalhesAutomovel = detalhesAutomovel;
    }

    public ImageView getFotoAutomovel() {
        return fotoAutomovel;
    }

    public void setFotoAutomovel(ImageView fotoAutomovel) {
        this.fotoAutomovel = fotoAutomovel;
    }

    public TextView getModeloAutomovel() {
        return modeloAutomovel;
    }

    public void setModeloAutomovel(TextView modeloAutomovel) {
        this.modeloAutomovel = modeloAutomovel;
    }

    public TextView getDetalhesAutomovel() {
        return detalhesAutomovel;
    }

    public void setDetalhesAutomovel(TextView detalhesAutomovel) {
        this.detalhesAutomovel = detalhesAutomovel;
    }
}
