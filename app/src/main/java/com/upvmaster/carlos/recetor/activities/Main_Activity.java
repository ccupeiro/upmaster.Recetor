package com.upvmaster.carlos.recetor.activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.bbdd.DBHelper;
import com.upvmaster.carlos.recetor.bbdd.dao.GroupDao;
import com.upvmaster.carlos.recetor.bbdd.dao.ReceiptDao;
import com.upvmaster.carlos.recetor.entities.Group;
import com.upvmaster.carlos.recetor.entities.Ingrediente;
import com.upvmaster.carlos.recetor.entities.Receipt;
import com.upvmaster.carlos.recetor.utils.UtilsReceipt;

import java.util.ArrayList;
import java.util.List;

public class Main_Activity extends AppCompatActivity {

    private Button btn_recetas,btn_random,btn_dietas;
    private boolean animado=false;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        new InitDBTask().execute();
        inicializarToolbar();
        ImageView img_logo = (ImageView) findViewById(R.id.img_logo);
        btn_recetas = (Button) findViewById(R.id.btn_recetas);
        btn_recetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzar_Recetas(null);
            }
        });
        btn_random = (Button) findViewById(R.id.btn_comida_random);
        btn_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzar_Random(view);
            }
        });
        btn_dietas = (Button) findViewById(R.id.btn_dieta);
        LinearLayout grupo_btn = (LinearLayout) findViewById(R.id.grupo_botones);
        Animation anim_logo = AnimationUtils.loadAnimation(this,R.anim.anim_logo);
        Animation anim_botones = AnimationUtils.loadAnimation(this,R.anim.anim_botones);
        if(!animado){
            animado = true;
            img_logo.startAnimation(anim_logo);
            grupo_btn.startAnimation(anim_botones);
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
        tv_titulo.setText(getString(R.string.app_name_mayus));
        // Icono atrás
        ImageView iv_atras = (ImageView) findViewById(R.id.iv_atras);
        iv_atras.setVisibility(View.GONE);
        // Guardar
        ImageView iv_save = (ImageView) findViewById(R.id.iv_guardar);
        iv_save.setVisibility(View.GONE);
        //Edit Receipt
        ImageView iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_edit.setVisibility(View.GONE);
        //Add Receipt
        ImageView iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzar_add(view);
            }
        });
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
        Toast.makeText(this,"Configurar",Toast.LENGTH_SHORT).show();
    }

    private void lanzar_find(View view) {
        Intent i = new Intent(this, Search_Activity.class);
        startActivity(i);
    }

    private void lanzar_add(View view) {
        Intent i = new Intent(this, AddReceipt_Activity.class);
        startActivity(i);
    }

    private void lanzar_Recetas(View vista){
        Intent i = new Intent(this, ListReceipt_Activity.class);
        startActivity(i);
    }
    private void lanzar_Random(View vista){
        new RandomReciptTask().execute();
    }

    private class InitDBTask extends  AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase db = DBHelper.getDatabase(activity);
            return null;
        }
    }

    private class RandomReciptTask extends AsyncTask<Void,Void,Boolean>{

        private ProgressDialog pd;
        private Receipt randomReceipt;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
            pd.setCancelable(false);
            pd.setMessage("Cargando listas");
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            SQLiteDatabase db = DBHelper.getDatabase(activity);
            try {
                ReceiptDao dao = new ReceiptDao(db);
                randomReceipt = dao.randomReceipt();
                return true;
            } catch (Exception e) {
                Log.e(getClass().getName(),e.getMessage(),e);
            } finally {
                db.close();
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean resul) {
            if(pd!=null)
                pd.dismiss();
            if(randomReceipt!=null && resul){
                ViewReceipt_Activity vista_activity = new ViewReceipt_Activity();
                Intent i = new Intent(activity,vista_activity.getClass());
                i.putExtra(ViewReceipt_Activity.ID_RECETA, new Gson().toJson(randomReceipt));
                startActivity(i);
            }else{
                Toast.makeText(getApplicationContext()
                        ,"No se ha cargado la receta Random! Error"
                        ,Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }
}
