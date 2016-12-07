package com.upvmaster.carlos.recetor.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TextView;

import com.upvmaster.carlos.recetor.R;


/**
 * Created by carlos.cupeiro on 05/12/2016.
 */

public class EditReceipt_Activity extends AppCompatActivity {

    TextView et_nombre;
    TextView et_group;
    TableLayout tl_ingredientes;
    TableLayout tl_pasos;
    TableLayout tl_variante;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receta);

    }
}
