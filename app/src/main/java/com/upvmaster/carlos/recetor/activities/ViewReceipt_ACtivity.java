package com.upvmaster.carlos.recetor.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.entities.Ingrediente;
import com.upvmaster.carlos.recetor.entities.Receipt;
import com.upvmaster.carlos.recetor.utils.UtilsReceipt;

import java.util.List;

public class ViewReceipt_Activity extends AppCompatActivity {
    public static final String ID_RECETA = "receta";
    private Receipt receta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);
        String jsonReceta="";
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            jsonReceta = extras.getString(ID_RECETA);
        }else{
            Toast.makeText(this,getString(R.string.text_noReceta),Toast.LENGTH_SHORT).show();
            this.finish();
        }
        receta = new Gson().fromJson(jsonReceta,Receipt.class);
        //Foto
        ImageView iv_imagen = (ImageView) findViewById(R.id.iv_image);
        //TODO falta por ver como gestionar las fotos
        //Titulo
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(receta.getName());
        //Grupo
        TextView tv_group = (TextView) findViewById(R.id.tv_grupo);
        tv_group.setText(UtilsReceipt.getGroupName(receta.getGroup()));
        //Ingredientes
        TableLayout tl_ingredientes = (TableLayout) findViewById(R.id.tbl_ingredientes);
        List<Ingrediente> ingredientes = receta.getList_ingredients();
        if(ingredientes!=null && ingredientes.size()>0){
            //Hay ingredientes
            TextView tv_no_ingredientes = (TextView) findViewById(R.id.tv_no_ingrendientes);
            tv_no_ingredientes.setVisibility(View.GONE);
            for(Ingrediente ing : ingredientes){
                TableRow ingredienteRow = new TableRow(this);
                ingredienteRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                View ingredienteRowInterno = getLayoutInflater().inflate(R.layout.elemento_ingrediente, null,false);
                ((TextView)ingredienteRowInterno.findViewById(R.id.tv_cantidad)).setText(UtilsReceipt.doubleToStringReceipt(ing.getCantidad()));
                ((TextView)ingredienteRowInterno.findViewById(R.id.tv_name_ingrediente)).setText(ing.getName());
                //insertarlo en la tabla
                ingredienteRow.addView(ingredienteRowInterno);
                tl_ingredientes.addView(ingredienteRow);
            }
        }
        //Pasos
        TableLayout tl_pasos = (TableLayout) findViewById(R.id.tbl_pasos);
        List<String> pasos = receta.getList_steps();
        if(pasos!=null && ingredientes.size()>0){
            //Hay pasos
            TextView tv_no_pasos = (TextView) findViewById(R.id.tv_no_pasos);
            tv_no_pasos.setVisibility(View.GONE);
            int num_paso = 1;
            for(String paso : pasos){
                TableRow pasoRow = new TableRow(this);
                pasoRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                View pasoRowInterno = getLayoutInflater().inflate(R.layout.elemento_paso, null,false);
                ((TextView)pasoRowInterno.findViewById(R.id.tv_num_paso)).setText(UtilsReceipt.getPaso(num_paso));
                ((TextView)pasoRowInterno.findViewById(R.id.tv_texto_paso)).setText(paso);
                //insertarlo en la tabla
                pasoRow.addView(pasoRowInterno);
                tl_pasos.addView(pasoRow);
                num_paso++;
            }
        }
        //Variantes
        TableLayout tl_variantes = (TableLayout) findViewById(R.id.tbl_variantes);
        List<String> variantes = receta.getList_variantes();
        if(variantes!=null && variantes.size()>0){
            //Hay Variantes
            for(String variante : variantes){
                TableRow pasoRow = new TableRow(this);
                pasoRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                View pasoRowInterno = getLayoutInflater().inflate(R.layout.elemento_variante, null,false);
                ((TextView)pasoRowInterno.findViewById(R.id.tv_variante)).setText(variante);
                //insertarlo en la tabla
                pasoRow.addView(pasoRowInterno);
                tl_pasos.addView(pasoRow);
            }
        }else{
            tl_variantes.setVisibility(View.GONE);
        }
    }

    public void setReceta(Receipt receta) {
        this.receta = receta;
    }
}
