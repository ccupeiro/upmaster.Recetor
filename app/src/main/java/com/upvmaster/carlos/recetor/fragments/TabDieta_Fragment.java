package com.upvmaster.carlos.recetor.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.entities.DiaDieta;

/**
 * Created by carlos.cupeiro on 12/12/2016.
 */

public class TabDieta_Fragment extends Fragment {

    private View vistaTab;
    private DiaDieta diaDieta;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vistaTab =   inflater.inflate(R.layout.tab_dia_dieta,container,false);
        //Hacer cosas con el diaDieta
        return vistaTab;
    }

    public DiaDieta getDiaDieta() {
        return diaDieta;
    }

    public void setDiaDieta(DiaDieta diaDieta) {
        this.diaDieta = diaDieta;
    }
}
