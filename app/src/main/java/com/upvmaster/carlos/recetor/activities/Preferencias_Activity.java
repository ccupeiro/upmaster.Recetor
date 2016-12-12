package com.upvmaster.carlos.recetor.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.fragments.PreferenciasFragment;


public class Preferencias_Activity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        activity = this;
        inicializarToolbar();
        getFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.activity_container,
                        new PreferenciasFragment())
                .commit();
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
}
