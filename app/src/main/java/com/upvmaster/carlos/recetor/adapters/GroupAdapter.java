package com.upvmaster.carlos.recetor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.entities.Receipt;

import java.util.List;

/**
 * Created by Carlos on 03/12/2016.
 */

public class GroupAdapter extends RecyclerView.Adapter<ReceiptHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflador;
    private List<Receipt> lista_recetas;
    protected View.OnClickListener onClickListener;
    private Context context;


    public GroupAdapter(Context context, List<Receipt> lista) {
        this.context = context;
        this.lista_recetas = lista;
        inflador = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ReceiptHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.elemento_receta, parent, false);
        v.setOnClickListener(onClickListener);
        return new ReceiptHolder(v,false,ReceiptHolder.CABECERA_GRUPOS);
    }

    @Override
    public void onBindViewHolder(ReceiptHolder holder, int position) {
        Receipt receta = lista_recetas.get(position);
        holder.bind(receta,context);
    }

    @Override
    public long getHeaderId(int position) {
        Receipt receta = lista_recetas.get(position);
        return receta.getGroup();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cabecera_sticky, parent, false);
        return new ReceiptHolder(view, true,ReceiptHolder.CABECERA_GRUPOS);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ReceiptHolder) holder).bind(lista_recetas.get(position),context);
    }

    @Override
    public int getItemCount() {
        return lista_recetas.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
