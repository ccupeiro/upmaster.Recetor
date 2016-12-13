package com.upvmaster.carlos.recetor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.adapters.AlphabeticAdapter;
import com.upvmaster.carlos.recetor.adapters.ViewTabAdapter;
import com.upvmaster.carlos.recetor.bbdd.DBHelper;
import com.upvmaster.carlos.recetor.bbdd.dao.ReceiptDao;
import com.upvmaster.carlos.recetor.entities.Receipt;
import com.upvmaster.carlos.recetor.utils.UtilsReceipt;

import java.util.List;

public class ListReceipt_Activity extends AppCompatActivity {

    private List<Receipt> alph_list;
    private List<Receipt> group_list;
    private boolean animado=true,sonidos=true;
    private Activity activity;
    private SharedPreferences pref;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_receipt);
        activity = this;
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        setPrefs();
        inicializarToolbar();
        inicialirTabs();
    }

    private void inicialirTabs(){
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_tab_Alph)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_tab_Group)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pagina);
        adapter = new ViewTabAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

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

    public boolean isSonidos() {
        return sonidos;
    }

    public boolean isAnimado() {
        return animado;
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
        tv_titulo.setText(R.string.list_receipt_title);
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
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sonidos){
                    mp_toolbar.start();
                }
                lanzarAdd(null);
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

    public List<Receipt> getAlph_list() {
        return alph_list;
    }

    public List<Receipt> getGroup_list() {
        return group_list;
    }

    private void lanzar_config(View view) {
        Intent i = new Intent(this, Preferencias_Activity.class);
        startActivity(i);
    }

    private void lanzar_find(View view) {
        Intent i = new Intent(this, Search_Activity.class);
        startActivity(i);
    }

    private void lanzarAdd(View view){
        Intent intent = new Intent(this,AddReceipt_Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPrefs();
        new GetListTask().execute();
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

    private class GetListTask extends AsyncTask<Void,Void,Boolean>{


        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
            pd.setCancelable(false);
            pd.setMessage(getString(R.string.list_receipt_loading_list));
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            SQLiteDatabase db = DBHelper.getDatabase(activity);
            try {
                ReceiptDao dao = new ReceiptDao(db);
                alph_list = dao.getAlphList();
                group_list = dao.getGroupList();
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
            if(alph_list!=null && group_list!=null && resul){
                viewPager.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getApplicationContext()
                        , R.string.list_receipt_error_loading_list
                        ,Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }
}
