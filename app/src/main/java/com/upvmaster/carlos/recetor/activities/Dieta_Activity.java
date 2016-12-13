package com.upvmaster.carlos.recetor.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    private static final int SOLICITUD_PERMISO_INTERNET = 300;
    private static final String RUTA_SERVER="http://upvccupeiro.esy.es/recetor";
    private boolean animado=true,sonidos=true;
    private Activity activity;
    private SharedPreferences pref;
    private Dieta dieta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta);
        activity = this;
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        setPrefs();
        inicializarToolbar();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            new GetDietaTask().execute();
        }else{
            solicitarPermisoInternet();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
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
        tv_titulo.setText(R.string.diet_title);
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
        tabLayout.addTab(tabLayout.newTab().setText(R.string.diet_monday_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.diet_tuesday_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.diet_wednesday_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.diet_thursday_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.diet_friday_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.diet_saturday_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.diet_sunday_title));
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

    private void solicitarPermisoInternet() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            Snackbar.make (activity.getCurrentFocus(), getString(R.string.permission_internet), Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, SOLICITUD_PERMISO_INTERNET);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, SOLICITUD_PERMISO_INTERNET);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case SOLICITUD_PERMISO_INTERNET:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new GetDietaTask().execute();
                } else {
                    Snackbar.make(this.getCurrentFocus(), R.string.permission_message_internet, Snackbar.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                finish();
                break;
        }
    }

    private class GetDietaTask extends AsyncTask<Void,Void,Boolean> {


        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
            pd.setCancelable(false);
            pd.setMessage(getString(R.string.diet_loading_diet));
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
                    while (linea!=null && !linea.equals("")) {
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
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean resul) {
            if(pd!=null)
                pd.dismiss();
            if(dieta!=null && resul){
                dieta.ordenarDieta();
                inicialirTabs();
            }else{
                Toast.makeText(getApplicationContext()
                        , R.string.diet_error_loading_diet
                        ,Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }
}
