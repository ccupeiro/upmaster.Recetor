package com.upvmaster.carlos.recetor.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.entities.DiaDieta;
import com.upvmaster.carlos.recetor.utils.UtilsReceipt;

import java.util.List;

import static com.upvmaster.carlos.recetor.R.id.ll_comida;

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
        LinearLayout ll_desayuno = (LinearLayout) vistaTab.findViewById(R.id.ll_desayuno);
        for(String plato: diaDieta.getDesayuno()){
            ll_desayuno.addView(getFood(inflater,plato));
        }
        LinearLayout ll_mediamanana = (LinearLayout) vistaTab.findViewById(R.id.ll_mediamanana);
        for(String plato: diaDieta.getComida()){
            ll_mediamanana.addView(getFood(inflater,plato));
        }
        LinearLayout ll_comida = (LinearLayout) vistaTab.findViewById(R.id.ll_comida);
        for(String plato: diaDieta.getComida()){
            ll_comida.addView(getFood(inflater,plato));
        }
        LinearLayout ll_merienda = (LinearLayout) vistaTab.findViewById(R.id.ll_merienda);
        for(String plato: diaDieta.getMerienda()){
            ll_merienda.addView(getFood(inflater,plato));
        }
        LinearLayout ll_cena = (LinearLayout) vistaTab.findViewById(R.id.ll_cena);
        for(String plato: diaDieta.getCena()){
            ll_cena.addView(getFood(inflater,plato));
        }
        return vistaTab;
    }

    private LinearLayout getFood(LayoutInflater inflater,String plato){
        LinearLayout ll_paso = (LinearLayout) inflater.inflate(R.layout.elemento_dieta, null,false);
        ll_paso.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ((TextView)ll_paso.findViewById(R.id.tv_dieta)).setText(plato);
        ((TextView)ll_paso.findViewById(R.id.tv_dieta)).setTextColor(Color.parseColor("#000000"));
        ll_paso.setVisibility(View.VISIBLE);
        return ll_paso;
    }
    public DiaDieta getDiaDieta() {
        return diaDieta;
    }

    public void setDiaDieta(DiaDieta diaDieta) {
        this.diaDieta = diaDieta;
    }
}
