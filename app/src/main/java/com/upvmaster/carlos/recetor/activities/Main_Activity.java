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

import com.upvmaster.carlos.recetor.R;

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
        btn_random = (Button) findViewById(R.id.btn_recetas);
        btn_dietas = (Button) findViewById(R.id.btn_recetas);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
