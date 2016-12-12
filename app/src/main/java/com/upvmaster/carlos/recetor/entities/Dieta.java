package com.upvmaster.carlos.recetor.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos.cupeiro on 12/12/2016.
 */

public class Dieta {
    List<DiaDieta> lst_dias;

    public Dieta() {
    }

    public List<DiaDieta> getLst_dias() {
        return lst_dias;
    }

    public void setLst_dias(List<DiaDieta> lst_dias) {
        this.lst_dias = lst_dias;
    }

    public List<DiaDieta> ordenarDieta(){
        //TODO hacer función que ordene
        return lst_dias;
    }

    @Override
    public String toString() {
        return "Dieta{" +
                "lst_dias=" + lst_dias +
                '}';
    }
}
