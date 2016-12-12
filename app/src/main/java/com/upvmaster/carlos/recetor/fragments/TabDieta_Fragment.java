package com.upvmaster.carlos.recetor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.activities.ListReceipt_Activity;
import com.upvmaster.carlos.recetor.activities.ViewReceipt_Activity;
import com.upvmaster.carlos.recetor.adapters.AlphabeticAdapter;
import com.upvmaster.carlos.recetor.entities.DiaDieta;
import com.upvmaster.carlos.recetor.entities.Receipt;

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
