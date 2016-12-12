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
import com.upvmaster.carlos.recetor.entities.Receipt;

/**
 * Created by Carlos on 03/12/2016.
 */

public class TabReceiptAlph_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private AlphabeticAdapter adaptador;
    private RecyclerView.LayoutManager layoutManager;
    private View vistaTab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vistaTab =   inflater.inflate(R.layout.tab_receipt_alphabetic,container,false);
        recyclerView = (RecyclerView) vistaTab.findViewById(R.id.rv_recetas_alph);
        adaptador = new AlphabeticAdapter(vistaTab.getContext(),((ListReceipt_Activity)getActivity()).getAlph_list());
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = recyclerView.getChildAdapterPosition(view);
                ViewReceipt_Activity vista_activity = new ViewReceipt_Activity();
                Receipt r = ((ListReceipt_Activity)getActivity()).getAlph_list().get(pos);
                Intent i = new Intent(getContext(),vista_activity.getClass());
                i.putExtra(ViewReceipt_Activity.ID_RECETA, new Gson().toJson(r));
                startActivity(i);
            }
        });
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(vistaTab.getContext());
        recyclerView.setLayoutManager(layoutManager);
        return vistaTab;
    }


}
