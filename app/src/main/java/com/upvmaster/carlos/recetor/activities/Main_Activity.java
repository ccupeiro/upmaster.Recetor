package com.upvmaster.carlos.recetor.activities;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.entities.Ingrediente;
import com.upvmaster.carlos.recetor.entities.Receipt;

import java.util.ArrayList;
import java.util.List;

public class Main_Activity extends AppCompatActivity {

    private Button btn_recetas,btn_random,btn_dietas;
    private boolean animado=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    private void lanzar_Recetas(View vista){
        Intent i = new Intent(this, ListReceipt_Activity.class);
        startActivity(i);
    }
    private void lanzar_Random(View vista){
        ViewReceipt_Activity vista_activity = new ViewReceipt_Activity();
        Receipt r = getRecetaRandomMock();
        Intent i = new Intent(this,vista_activity.getClass());
        i.putExtra(ViewReceipt_Activity.ID_RECETA, new Gson().toJson(r));
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private Receipt getRecetaRandomMock(){
        Receipt r = new Receipt();
        r.setName("Alcachofas");
        r.setGroup(0);
        List<Ingrediente> lista_ing = new ArrayList<Ingrediente>();
        Ingrediente i = new Ingrediente();
        i.setName("Alcachofa");
        i.setCantidad(4);
        lista_ing.add(i);
        i = new Ingrediente();
        i.setName("Sal");
        i.setCantidad(0.5);
        lista_ing.add(i);
        r.setList_ingredients(lista_ing);
        List<String> lista_pasos = new ArrayList<String>();
        lista_pasos.add("Cocer las alcachofas");
        lista_pasos.add("Echar sal a las alcachofas");
        lista_pasos.add("Servir las alcachofas");
        r.setList_steps(lista_pasos);
        return r;
    }
}
