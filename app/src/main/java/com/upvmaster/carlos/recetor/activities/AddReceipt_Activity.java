package com.upvmaster.carlos.recetor.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.utils.UtilsReceipt;

/**
 * Created by carlos.cupeiro on 07/12/2016.
 */

public class AddReceipt_Activity extends AppCompatActivity {

    TableLayout tl_ingredientes;
    TableLayout tl_pasos;
    TableLayout tl_variante;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receta);
        tl_ingredientes = (TableLayout) findViewById(R.id.tbl_ingredientes);
        tl_pasos = (TableLayout) findViewById(R.id.tbl_pasos);
        tl_variante = (TableLayout) findViewById(R.id.tbl_variantes);
        LinearLayout ll_add = (LinearLayout) findViewById(R.id.ll_add_ingrediente);
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngrediente(null);
            }
        });
    }

    private void addIngrediente(View vista){
        TableRow ingredienteRow = new TableRow(this);
        ingredienteRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        View ingredienteRowInterno = getLayoutInflater().inflate(R.layout.elemento_add_ingrediente, null,false);
        //insertarlo en la tabla
        ingredienteRow.addView(ingredienteRowInterno);
        tl_ingredientes.addView(ingredienteRow);
    }
}
