package com.upvmaster.carlos.recetor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.entities.Receipt;
import com.upvmaster.carlos.recetor.utils.UtilsReceipt;

/**
 * Created by Carlos on 03/12/2016.
 */

public class ReceiptHolder extends RecyclerView.ViewHolder {
    public static final int CABECERA_ALPH = 0;
    public static final int CABECERA_GRUPOS = 1;
    private boolean isHeader;
    private TextView titulo;
    private ImageView icon;
    private TextView headerText;
    private int type;
    public ReceiptHolder(View itemView,boolean isHeader,int type) {
        super(itemView);
        this.isHeader = isHeader;
        if(isHeader){
            headerText = (TextView) itemView.findViewById(R.id.tv_cabecera);
            this.type = type;
        }else{
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            icon = (ImageView) itemView.findViewById(R.id.icono);
        }

    }
    public void bind(final Receipt receta, Context context) {
        if(isHeader){
            String texto="";
            switch (type){
                case CABECERA_ALPH:
                    texto=receta.getName().toUpperCase().charAt(0)+"";
                    break;
                case CABECERA_GRUPOS:
                    texto = UtilsReceipt.getGroupName(receta.getGroup(),context);
                    break;
            }
            headerText.setText(texto);
        }else{
            titulo.setText(receta.getName());
            //TODO esto hay que cambiarlo para que cargue la imagen de la receta
            icon.setImageResource(R.drawable.logo);
        }

    }
}
