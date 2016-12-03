package com.upvmaster.carlos.recetor.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.adapters.AlphabeticAdapter;
import com.upvmaster.carlos.recetor.adapters.GroupAdapter;
import com.upvmaster.carlos.recetor.entities.Receipt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 03/12/2016.
 */

public class TabReceiptGroup_Fragment extends Fragment{

    private RecyclerView recyclerView;
    private GroupAdapter adaptador;
    private RecyclerView.LayoutManager layoutManager;
    private View vistaTab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vistaTab =   inflater.inflate(R.layout.tab_receipt_group,container,false);
        recyclerView = (RecyclerView) vistaTab.findViewById(R.id.rv_recetas_group);
        adaptador = new GroupAdapter(vistaTab.getContext(),MockListaRecetas());
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llevar a ver el elemento
            }
        });
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(vistaTab.getContext());
        recyclerView.setLayoutManager(layoutManager);
        return vistaTab;
    }

    private List<Receipt> MockListaRecetas(){
        List<Receipt> lista = new ArrayList<Receipt>();
        Receipt r = new Receipt();
        r.setName("Canelones");
        lista.add(r);
        r = new Receipt();
        r.setName("Pizza");
        lista.add(r);
        r = new Receipt();
        r.setName("Alcachofas");
        lista.add(r);
        r = new Receipt();
        r.setName("Brownie");
        lista.add(r);
        r = new Receipt();
        r.setName("Arroz a la Cubana");
        lista.add(r);
        r = new Receipt();
        r.setName("Puré Calabacín");
        lista.add(r);
        return lista;
    }
}
