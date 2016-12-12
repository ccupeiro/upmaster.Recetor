package com.upvmaster.carlos.recetor.entities;

import java.util.List;

/**
 * Created by carlos.cupeiro on 12/12/2016.
 */

public class DiaDieta {
    private String dia;
    private List<String> desayuno;
    private List<String> mediamanana;
    private List<String> comida;
    private List<String> merienda;
    private List<String> cena;

    public DiaDieta() {
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public List<String> getDesayuno() {
        return desayuno;
    }

    public void setDesayuno(List<String> desayuno) {
        this.desayuno = desayuno;
    }

    public List<String> getMediamanana() {
        return mediamanana;
    }

    public void setMediamanana(List<String> mediamanana) {
        this.mediamanana = mediamanana;
    }

    public List<String> getComida() {
        return comida;
    }

    public void setComida(List<String> comida) {
        this.comida = comida;
    }

    public List<String> getMerienda() {
        return merienda;
    }

    public void setMerienda(List<String> merienda) {
        this.merienda = merienda;
    }

    public List<String> getCena() {
        return cena;
    }

    public void setCena(List<String> cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "DiaDieta{" +
                "dia='" + dia + '\'' +
                ", desayuno=" + desayuno +
                ", mediamanana=" + mediamanana +
                ", comida=" + comida +
                ", merienda=" + merienda +
                ", cena=" + cena +
                '}';
    }
}
