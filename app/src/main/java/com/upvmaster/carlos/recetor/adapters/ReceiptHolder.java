package com.upvmaster.carlos.recetor.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.upvmaster.carlos.recetor.R;

/**
 * Created by Carlos on 03/12/2016.
 */

public class ReceiptHolder extends RecyclerView.ViewHolder {
    public TextView titulo;
    public ImageView icon;
    public ReceiptHolder(View itemView) {
        super(itemView);
        titulo = (TextView) itemView.findViewById(R.id.titulo);
        icon = (ImageView) itemView.findViewById(R.id.icono);
    }
}
