package com.upvmaster.carlos.recetor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.adapters.SearchAdapter;
import com.upvmaster.carlos.recetor.bbdd.DBHelper;
import com.upvmaster.carlos.recetor.bbdd.dao.ReceiptDao;
import com.upvmaster.carlos.recetor.entities.Receipt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 11/12/2016.
 */

public class Search_Activity extends AppCompatActivity {
    private List<Receipt> alph_list;
    private List<Receipt> search_list;
    private boolean animado=true,sonidos=true;
    private Activity activity;
    private SharedPreferences pref;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText et_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activity = this;
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        setPrefs();
        new GetListTask().execute();
        inicializarToolbar();

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

        et_search = (EditText) findViewById(R.id.et_search);
        et_search.addTextChangedListener(configurarTextWatcher(et_search));

    }


    private void inicializar_Lista(){
        recyclerView = (RecyclerView) findViewById(R.id.rv_search);
        SearchAdapter adaptador = new SearchAdapter(activity,search_list);
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sonidos){
                    MediaPlayer mp_list = MediaPlayer.create(activity, R.raw.sonido_lista);
                    mp_list.start();
                }
                int pos = recyclerView.getChildAdapterPosition(view);
                ViewReceipt_Activity vista_activity = new ViewReceipt_Activity();
                Receipt r = search_list.get(pos);
                Intent i = new Intent(activity,vista_activity.getClass());
                i.putExtra(ViewReceipt_Activity.ID_RECETA, new Gson().toJson(r));
                startActivity(i);
            }
        });
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
    }

    private TextWatcher configurarTextWatcher(final EditText texto){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    search_list = new ArrayList<>();
                    for(Receipt r: alph_list){
                        if(r.getName().toLowerCase().contains(s.toString().toLowerCase())){
                            search_list.add(r);
                        }
                    }
                }else{
                    search_list = alph_list;
                }
                inicializar_Lista();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        return textWatcher;
    }

    private class GetListTask extends AsyncTask<Void,Void,Boolean> {


        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
            pd.setCancelable(false);
            pd.setMessage(getString(R.string.search_message_loading));
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            SQLiteDatabase db = DBHelper.getDatabase(activity);
            try {
                ReceiptDao dao = new ReceiptDao(db);
                alph_list = dao.getAlphList();
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
            if(alph_list!=null && resul){
                search_list = alph_list;
                inicializar_Lista();
            }else{
                Toast.makeText(getApplicationContext()
                        , R.string.search_error_loading
                        ,Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }
}
