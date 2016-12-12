package com.upvmaster.carlos.recetor.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.upvmaster.carlos.recetor.entities.Step;
import com.upvmaster.carlos.recetor.entities.Variante;
import com.upvmaster.carlos.recetor.utils.UtilsReceipt;

import java.io.File;
import java.util.List;

public class ViewReceipt_Activity extends AppCompatActivity {
    public static final String ID_RECETA = "receta";
    private Receipt receta;
    private Activity activity;
    private String jsonReceta="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);
        activity = this;
        inicializarToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        if(receta.getSrc_photo()!=null && !receta.getSrc_photo().equals("")){
            //Hay foto
            File imgFile = new File(receta.getSrc_photo());
            if(imgFile.exists()){
                Bitmap bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv_imagen.setImageBitmap(bmp);
            }else{
                iv_imagen.setImageResource(R.drawable.logo);
            }
        }else{
            iv_imagen.setImageResource(R.drawable.logo);
        }
        //Titulo
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(receta.getName());
        //Grupo
        TextView tv_group = (TextView) findViewById(R.id.tv_grupo);
        tv_group.setText(UtilsReceipt.getGroupName(receta.getGroup(),activity));
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
                if(ing.getCantidad()!=null && !ing.getCantidad().trim().equals("")){
                    ((TextView)ingredienteRowInterno.findViewById(R.id.tv_cantidad)).setText(ing.getCantidad());
                }
                else{
                    ((TextView)ingredienteRowInterno.findViewById(R.id.tv_cantidad)).setVisibility(View.GONE);
                }
                ((TextView)ingredienteRowInterno.findViewById(R.id.tv_name_ingrediente)).setText(ing.getName());
                //insertarlo en la tabla
                ingredienteRow.addView(ingredienteRowInterno);
                tl_ingredientes.addView(ingredienteRow);
            }
        }
        //Pasos
        TableLayout tl_pasos = (TableLayout) findViewById(R.id.tbl_pasos);
        List<Step> pasos = receta.getList_steps();
        if(pasos!=null && ingredientes.size()>0){
            //Hay pasos
            TextView tv_no_pasos = (TextView) findViewById(R.id.tv_no_pasos);
            tv_no_pasos.setVisibility(View.GONE);
            for(Step paso : pasos){
                TableRow pasoRow = new TableRow(this);
                pasoRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                View pasoRowInterno = getLayoutInflater().inflate(R.layout.elemento_paso, null,false);
                ((TextView)pasoRowInterno.findViewById(R.id.tv_num_paso)).setText(UtilsReceipt.getPaso(paso.getPos_paso()));
                ((TextView)pasoRowInterno.findViewById(R.id.tv_texto_paso)).setText(paso.getDescription());
                //insertarlo en la tabla
                pasoRow.addView(pasoRowInterno);
                tl_pasos.addView(pasoRow);
            }
        }
        //Variantes
        TableLayout tl_variantes = (TableLayout) findViewById(R.id.tbl_variantes);
        List<Variante> variantes = receta.getList_variantes();
        if(variantes!=null && variantes.size()>0){
            //Hay Variantes
            for(Variante variante : variantes){
                TableRow pasoRow = new TableRow(this);
                pasoRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                View pasoRowInterno = getLayoutInflater().inflate(R.layout.elemento_variante, null,false);
                ((TextView)pasoRowInterno.findViewById(R.id.tv_variante)).setText(variante.getDescription());
                //insertarlo en la tabla
                pasoRow.addView(pasoRowInterno);
                tl_pasos.addView(pasoRow);
            }
        }else{
            tl_variantes.setVisibility(View.GONE);
            findViewById(R.id.linea_pasos).setVisibility(View.GONE);
            findViewById(R.id.tv_variante_general).setVisibility(View.GONE);
        }
    }

    private void inicializarToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        //Titulo
        TextView tv_titulo = (TextView) findViewById(R.id.tv_titulo_toolbar);
        tv_titulo.setText("Ver Receta");
        // Icono atrás
        ImageView iv_atras = (ImageView) findViewById(R.id.iv_atras);
        iv_atras.setVisibility(View.VISIBLE);
        iv_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        // Guardar
        ImageView iv_save = (ImageView) findViewById(R.id.iv_guardar);
        iv_save.setVisibility(View.GONE);
        //Edit Receipt
        ImageView iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_edit.setVisibility(View.VISIBLE);
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzar_edit(view);
            }
        });
        //Add Receipt
        ImageView iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_add.setVisibility(View.GONE);
        //find
        ImageView iv_find = (ImageView) findViewById(R.id.iv_find);
        iv_find.setVisibility(View.VISIBLE);
        iv_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzar_find(view);
            }
        });
        //config
        ImageView iv_config = (ImageView) findViewById(R.id.iv_config);
        iv_config.setVisibility(View.VISIBLE);
        iv_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzar_config(view);
            }
        });
    }

    private void lanzar_config(View view) {
        Intent i = new Intent(this, Preferencias_Activity.class);
        startActivity(i);
    }

    private void lanzar_find(View view) {
        Intent i = new Intent(this, Search_Activity.class);
        startActivity(i);
    }
    private void lanzar_edit(View view) {
        AddReceipt_Activity edit_activity = new AddReceipt_Activity();
        Intent i = new Intent(activity,edit_activity.getClass());
        i.putExtra(AddReceipt_Activity.ID_EDIT_RECETA, jsonReceta);
        startActivity(i);
    }
}
