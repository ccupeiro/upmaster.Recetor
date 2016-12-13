package com.upvmaster.carlos.recetor.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private int REQUEST_EDIT=1000;
    private Receipt receta;
    private boolean animado=true,sonidos=true;
    private Activity activity;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);
        activity = this;
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        setPrefs();
        receta = (Receipt) getIntent().getSerializableExtra(ID_RECETA);
        if(receta==null){
            Toast.makeText(this,getString(R.string.text_noReceta),Toast.LENGTH_SHORT).show();
            this.finish();
        }
        inicializarToolbar();
    }

    private void setPrefs(){
        if(pref.getBoolean("sonidos",true)){
            sonidos = true;
        }else{
            sonidos = false;
        }
        if(pref.getBoolean("animaciones",true)){
            animado = true;
        }else{
            animado = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPrefs();
        printInfo();

    }

    private void printInfo() {
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
        LinearLayout tl_ingredientes = (LinearLayout) findViewById(R.id.tbl_ingredientes);
        tl_ingredientes.removeAllViews();
        List<Ingrediente> ingredientes = receta.getList_ingredients();
        if(ingredientes!=null && ingredientes.size()>0){
            //Hay ingredientes
            TextView tv_no_ingredientes = (TextView) findViewById(R.id.tv_no_ingrendientes);
            tv_no_ingredientes.setVisibility(View.GONE);
            for(Ingrediente ing : ingredientes){
                LinearLayout ll_ingrediente = (LinearLayout) getLayoutInflater().inflate(R.layout.elemento_ingrediente, null,false);
                ll_ingrediente.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                String texto="";
                if(ing.getCantidad()!=null && !ing.getCantidad().trim().equals("")){
                    texto+=ing.getCantidad()+" ";
                }
                ((TextView)ll_ingrediente.findViewById(R.id.tv_name_ingrediente)).setText(texto+ing.getName());
                tl_ingredientes.addView(ll_ingrediente);
            }
        }
        //Pasos
        LinearLayout tl_pasos = (LinearLayout) findViewById(R.id.tbl_pasos);
        tl_pasos.removeAllViews();
        List<Step> pasos = receta.getList_steps();
        if(pasos!=null && ingredientes.size()>0){
            //Hay pasos
            TextView tv_no_pasos = (TextView) findViewById(R.id.tv_no_pasos);
            tv_no_pasos.setVisibility(View.GONE);
            for(Step paso : pasos){
                LinearLayout ll_paso = (LinearLayout) getLayoutInflater().inflate(R.layout.elemento_paso, null,false);
                ll_paso.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ((TextView)ll_paso.findViewById(R.id.tv_num_paso)).setText(UtilsReceipt.getPaso(paso.getPos_paso()));
                ((TextView)ll_paso.findViewById(R.id.tv_texto_paso)).setText(paso.getDescription());
                tl_pasos.addView(ll_paso);
            }
        }
        //Variantes
        LinearLayout tl_variantes = (LinearLayout) findViewById(R.id.tbl_variantes);
        tl_variantes.removeAllViews();
        List<Variante> variantes = receta.getList_variantes();
        if(variantes!=null && variantes.size()>0){
            //Hay Variantes
            tl_variantes.setVisibility(View.VISIBLE);
            findViewById(R.id.linea_pasos).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_variante_general).setVisibility(View.VISIBLE);
            for(Variante variante : variantes){
                LinearLayout ll_var = (LinearLayout) getLayoutInflater().inflate(R.layout.elemento_variante, null,false);
                ll_var.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ((TextView)ll_var.findViewById(R.id.tv_variante)).setText(variante.getDescription());
                tl_variantes.addView(ll_var);
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
        final MediaPlayer mp_toolbar = MediaPlayer.create(activity, R.raw.sonido_toolbar);
        //Titulo
        TextView tv_titulo = (TextView) findViewById(R.id.tv_titulo_toolbar);
        tv_titulo.setText(R.string.view_receipt_title);
        // Icono atrás
        ImageView iv_atras = (ImageView) findViewById(R.id.iv_atras);
        iv_atras.setVisibility(View.VISIBLE);
        iv_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sonidos){
                    mp_toolbar.start();
                }
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
                if(sonidos){
                    mp_toolbar.start();
                }
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
                if(sonidos){
                    mp_toolbar.start();
                }
                lanzar_find(view);
            }
        });
        //config
        ImageView iv_config = (ImageView) findViewById(R.id.iv_config);
        iv_config.setVisibility(View.VISIBLE);
        iv_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sonidos){
                    mp_toolbar.start();
                }
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
        i.putExtra(AddReceipt_Activity.ID_EDIT_RECETA, receta);
        startActivityForResult(i,REQUEST_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_EDIT && resultCode==RESULT_OK && data!=null){
            receta = (Receipt) data.getSerializableExtra(ID_RECETA);
            if(receta==null){
                Toast.makeText(getApplicationContext()
                        , R.string.view_receipt_error_receive_receipt
                        ,Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }
}
