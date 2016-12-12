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
        if(diaDieta.getDia().equals("Martes")){
            for(String plato: diaDieta.getDesayuno()){
                LinearLayout ll_paso = (LinearLayout) inflater.inflate(R.layout.elemento_dieta, null,false);
                ll_paso.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ((TextView)ll_paso.findViewById(R.id.tv_dieta)).setText(plato);
                ((TextView)ll_paso.findViewById(R.id.tv_dieta)).setTextColor(Color.parseColor("#000000"));
                ll_paso.setVisibility(View.VISIBLE);
                ll_paso.setBackground(getResources().getDrawable(R.drawable.background_table_border));
                ll_desayuno.addView(ll_paso);
            }
        }
        addFood(ll_desayuno,diaDieta.getDesayuno());
        LinearLayout ll_mediamanana = (LinearLayout) vistaTab.findViewById(R.id.ll_mediamanana);
        addFood(ll_mediamanana,diaDieta.getMediamanana());
        LinearLayout ll_comida = (LinearLayout) vistaTab.findViewById(R.id.ll_comida);
        addFood(ll_comida,diaDieta.getComida());
        LinearLayout ll_merienda = (LinearLayout) vistaTab.findViewById(R.id.ll_merienda);
        addFood(ll_merienda,diaDieta.getMerienda());
        LinearLayout ll_cena = (LinearLayout) vistaTab.findViewById(R.id.ll_cena);
        addFood(ll_cena,diaDieta.getCena());
        return vistaTab;
    }

    private void addFood(LinearLayout ll, List<String> comidas){
        for(String plato: comidas){
            TextView vista = new TextView(getContext());
            vista.setText(plato);
            vista.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            vista.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            vista.setGravity(Gravity.CENTER);
            ll.addView(vista);
        }

    }

    public DiaDieta getDiaDieta() {
        return diaDieta;
    }

    public void setDiaDieta(DiaDieta diaDieta) {
        this.diaDieta = diaDieta;
    }
}
