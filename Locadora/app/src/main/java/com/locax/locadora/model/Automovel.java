package com.locax.locadora.model;

import java.io.Serializable;

/**
 * Created by Luiz on 13/10/2015.
 */
public class Automovel implements Comparable<Automovel>, Serializable {
    private long idAutomovel;
    private String chassi;
    private String placa;
    private String cidade;
    private int km;
    private String estado;
    private String modelo;
    private String fabricante;
    private int grupo;
    private int nagadorGps;
    private int cadeiraBebe;
    private int motorista;
    private double tarifaLivre;
    private double controladoInicial;
    private double controladoKm;
    private int ano;
    private int situacao;				//0 = Disponível; 1 = Locado; 2 = Em manutenção.
    private String nomeImagem;			//Variavel responsável por guardar a url da imagem

    public Automovel()						{	}

    public Automovel(long idAutomovel,String chassi,String placa,String cidade,int km,String estado, String modelo, String fabricante, int grupo, int nagadorGps, int cadeiraBebe, int motorista, double tarifaLivre, double controladoInicial, double controladoKm, int ano, int situacao, String nomeImagem){
        this.idAutomovel = idAutomovel;
        this.chassi = chassi;
        this.placa = placa;
        this.cidade = cidade;
        this.km = km;
        this.estado = estado;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.grupo = grupo;
        this.nagadorGps = nagadorGps;
        this.cadeiraBebe = cadeiraBebe;
        this.motorista = motorista;
        this.tarifaLivre = tarifaLivre;
        this.controladoInicial = controladoInicial;
        this.controladoKm = controladoKm;
        this.ano = ano;
        this.situacao = situacao;
        this.nomeImagem = nomeImagem;
    }

    public Automovel(String chassi, String modelo, String fabricante, String nomeImagem){
        this.chassi = chassi;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.nomeImagem = nomeImagem;
    }


    public Automovel( String modelo, String fabricante,double tarifaLivre, double controladoInicial, double controladoKm, String nomeImagem){
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.tarifaLivre = tarifaLivre;
        this.controladoInicial = controladoInicial;
        this.controladoKm = controladoKm;
        this.nomeImagem = nomeImagem;
    }



    public long getIdAutomovel(){
        return idAutomovel;
    }


    public String getChassi(){
        return chassi;
    }



    public String getPlaca(){
        return placa;
    }


    public String getCidade(){
        return cidade;
    }



    public int getKm(){
        return km;
    }



    public String getEstado(){
        return estado;
    }



    public String getModelo(){
        return modelo;
    }



    public String getFabricante(){
        return fabricante;
    }



    public int getGrupo(){
        return grupo;
    }
    //Utilizar switch para descobrir a qual grupo pertence.

    public String getNomeGrupo(){
        String nomeGrupo = "";
        switch(getGrupo()){

            case 1: nomeGrupo = "1";
                break;

            case 2: nomeGrupo = "2";
                break;

            case 3: nomeGrupo = "3";
                break;

            case 4: nomeGrupo = "4";
                break;

            case 5: nomeGrupo = "5";
                break;

            case 6: nomeGrupo = "6";
                break;

            case 7: nomeGrupo = "7";
                break;

            case 8: nomeGrupo = "8";
                break;

            case 9: nomeGrupo = "pickUp";
                break;

            case 10: nomeGrupo = "minivan";
                break;

            case 11: nomeGrupo = "furgao";
                break;

            case 12: nomeGrupo = "blindado";
                break;

        }
        return nomeGrupo;
    }



    public String getAcessorios(){
        String acessorios = "";
        if(getNagadorGps() == 1)
            acessorios += "navegadorgps";

        if(getCadeiraBebe() == 1)
            acessorios += " – " + "cadeirabebe";

        if(getMotorista() == 1)
            acessorios += " – " + "motorista";

        return acessorios;
    }



    public int getNagadorGps(){
        return nagadorGps;
    }



    public int getCadeiraBebe(){
        return cadeiraBebe;
    }



    public int getMotorista(){
        return motorista;
    }



    public double getTarifaLivre(){
        return tarifaLivre;
    }



    public double getControladoInicial(){
        return controladoInicial;
    }

    public void setControladoKm(double tarkm){
        controladoKm = tarkm;
    }

    public double getControladoKm() {
        return controladoKm;
    }

    public int getAno(){
        return ano;
    }



    public String getNomeImagem(){
        return nomeImagem;
    }

    public int getSituacao(){
        return situacao;
    }

    /*@Override
    public String toString() {
        return "";
    }*/

    @Override
    public int compareTo(Automovel automovel) {
        if (modelo.equals(automovel.getModelo())
                ) {
            return 0;
        }
        return this.getModelo().compareTo(automovel.getModelo());
    }
}
