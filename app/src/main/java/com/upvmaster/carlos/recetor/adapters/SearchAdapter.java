package com.upvmaster.carlos.recetor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.entities.Receipt;

import java.util.List;

/**
 * Created by Carlos on 03/12/2016.
 */

public class SearchAdapter extends RecyclerView.Adapter<ReceiptHolder> {

    private LayoutInflater inflador;
    private List<Receipt> lista_recetas;
    protected View.OnClickListener onClickListener;


    public SearchAdapter(Context context, List<Receipt> lista) {
        this.lista_recetas = lista;
        inflador = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ReceiptHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.elemento_receta, parent, false);
        v.setOnClickListener(onClickListener);
        return new ReceiptHolder(v);
    }

    @Override
    public void onBindViewHolder(ReceiptHolder holder, int position) {
        Receipt receta = lista_recetas.get(position);
        holder.titulo.setText(receta.getName());
        //TODO esto hay que cambiarlo para que cargue la imagen de la receta
        holder.icon.setImageResource(R.drawable.logo);
    }

    @Override
    public int getItemCount() {
        return lista_recetas.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
