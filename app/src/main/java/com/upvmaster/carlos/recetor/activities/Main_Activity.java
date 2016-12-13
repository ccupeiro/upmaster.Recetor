package com.upvmaster.carlos.recetor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.bbdd.DBHelper;
import com.upvmaster.carlos.recetor.bbdd.dao.ReceiptDao;
import com.upvmaster.carlos.recetor.entities.Receipt;

public class Main_Activity extends AppCompatActivity {

    private Button btn_recetas,btn_random,btn_dietas;
    private boolean animado=true,sonidos=true;
    private Activity activity;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        setPrefs();
        inicializarToolbar();
        //Sonidos
        final MediaPlayer mp = MediaPlayer.create(activity, R.raw.sonido_button);
        ImageView img_logo = (ImageView) findViewById(R.id.img_logo);
        btn_recetas = (Button) findViewById(R.id.btn_recetas);
        btn_recetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sonidos){
                    mp.start();
                }
                lanzar_Recetas(null);
            }
        });
        btn_random = (Button) findViewById(R.id.btn_comida_random);
        btn_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sonidos){
                    mp.start();
                }
                lanzar_Random(view);
            }
        });
        btn_dietas = (Button) findViewById(R.id.btn_dieta);
        btn_dietas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sonidos){
                    mp.start();
                }
                lanzar_Dieta(view);
            }
        });
        LinearLayout grupo_btn = (LinearLayout) findViewById(R.id.grupo_botones);
        Animation anim_logo = AnimationUtils.loadAnimation(this,R.anim.anim_logo);
        Animation anim_botones = AnimationUtils.loadAnimation(this,R.anim.anim_botones);
        if(animado){
            img_logo.startAnimation(anim_logo);
            grupo_btn.startAnimation(anim_botones);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPrefs();
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

    private void inicializarToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        final MediaPlayer mp_toolbar = MediaPlayer.create(activity, R.raw.sonido_toolbar);
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
                if(sonidos){
                    mp_toolbar.start();
                }
                lanzar_add(view);
            }
        });
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

    private void lanzar_Dieta(View vista){
        Intent i = new Intent(this, Dieta_Activity.class);
        startActivity(i);
    }

    private class RandomReciptTask extends AsyncTask<Void,Void,Boolean>{

        private ProgressDialog pd;
        private Receipt randomReceipt;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
            pd.setCancelable(false);
            pd.setMessage(getString(R.string.main_loading_random));
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
                i.putExtra(ViewReceipt_Activity.ID_RECETA, randomReceipt);
                startActivity(i);
            }else{
                Toast.makeText(getApplicationContext()
                        , R.string.main_error_loading_random
                        ,Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }
}
