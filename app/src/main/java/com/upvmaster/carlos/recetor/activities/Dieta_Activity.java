package com.upvmaster.carlos.recetor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.adapters.ViewTabDietaAdapter;
import com.upvmaster.carlos.recetor.entities.DiaDieta;
import com.upvmaster.carlos.recetor.entities.Dieta;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Dieta_Activity extends AppCompatActivity {
    private static final String RUTA_SERVER="http://upvccupeiro.esy.es/recetor";
    private Activity activity;
    private Dieta dieta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta);
        activity = this;
        inicializarToolbar();
        //TODO Pedir permiso de internet
        new GetDietaTask().execute();
    }

    private void inicializarToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        //Titulo
        TextView tv_titulo = (TextView) findViewById(R.id.tv_titulo_toolbar);
        tv_titulo.setText("DIETA");
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
        iv_edit.setVisibility(View.GONE);
        //Add Receipt
        ImageView iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_add.setVisibility(View.GONE);
        //find
        ImageView iv_find = (ImageView) findViewById(R.id.iv_find);
        iv_find.setVisibility(View.GONE);
        //config
        ImageView iv_config = (ImageView) findViewById(R.id.iv_config);
        iv_config.setVisibility(View.GONE);
    }

    private void inicialirTabs(){
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("L"));
        tabLayout.addTab(tabLayout.newTab().setText("M"));
        tabLayout.addTab(tabLayout.newTab().setText("X"));
        tabLayout.addTab(tabLayout.newTab().setText("J"));
        tabLayout.addTab(tabLayout.newTab().setText("V"));
        tabLayout.addTab(tabLayout.newTab().setText("S"));
        tabLayout.addTab(tabLayout.newTab().setText("D"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pagina);
        final ViewTabDietaAdapter adapter = new ViewTabDietaAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),dieta);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private class GetDietaTask extends AsyncTask<Void,Void,Boolean> {


        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
            pd.setCancelable(false);
            pd.setMessage("Cargando Dieta");
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpURLConnection conexion=null;
            try {
                URL url=new URL(RUTA_SERVER+"/dieta.php");
                conexion = (HttpURLConnection) url
                        .openConnection();
                if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    StringBuilder json = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conexion.getInputStream()));
                    String linea = reader.readLine();
                    while (!linea.equals("")) {
                        json.append(linea);
                        linea = reader.readLine();
                    }
                    reader.close();
                    dieta = new Gson().fromJson(json.toString(),Dieta.class);
                    return true;
                } else {
                    Log.e("DIETA", conexion.getResponseMessage());
                }
            } catch (Exception e) {
                Log.e("DIETA", e.getMessage(), e);
            } finally {
                if (conexion!=null) conexion.disconnect();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean resul) {
            if(pd!=null)
                pd.dismiss();
            if(dieta!=null && resul){
                //TODO validar dieta
                dieta.ordenarDieta();
                inicialirTabs();
            }else{
                Toast.makeText(getApplicationContext()
                        ,"No se han cargado la dieta! Error"
                        ,Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }
}
