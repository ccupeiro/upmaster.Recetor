package com.upvmaster.carlos.recetor.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.utils.UtilsReceipt;

/**
 * Created by carlos.cupeiro on 07/12/2016.
 */

public class AddReceipt_Activity extends AppCompatActivity {

    private TableLayout tl_ingredientes;
    private TableLayout tl_pasos;
    private TableLayout tl_variante;
    private EditText et_titulo;
    private Activity activity;
    private int paso_sig=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receta);
        activity = this;
        inicializarToolbar();
        et_titulo = (EditText) findViewById(R.id.et_name_receipt);
        et_titulo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    View view = getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        tl_ingredientes = (TableLayout) findViewById(R.id.tbl_ingredientes);
        tl_pasos = (TableLayout) findViewById(R.id.tbl_pasos);
        tl_variante = (TableLayout) findViewById(R.id.tbl_variantes);
        LinearLayout ll_add_ing = (LinearLayout) findViewById(R.id.ll_add_ingrediente);
        ll_add_ing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngrediente(null);
            }
        });
        LinearLayout ll_add_step = (LinearLayout) findViewById(R.id.ll_add_paso);
        ll_add_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPaso(null);
            }
        });
        LinearLayout ll_add_var = (LinearLayout) findViewById(R.id.ll_add_variante);
        ll_add_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVariante(null);
            }
        });
    }

    private void inicializarToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        //Titulo
        TextView tv_titulo = (TextView) findViewById(R.id.tv_titulo_toolbar);
        tv_titulo.setText(getString(R.string.app_name_mayus));
        // Icono atrás
        ImageView iv_atras = (ImageView) findViewById(R.id.iv_atras);
        iv_atras.setVisibility(View.VISIBLE);
        iv_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialogo para salir sin guardar
                Toast.makeText(getApplicationContext(),"NO GUARDO",Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        });
        // Guardar
        ImageView iv_save = (ImageView) findViewById(R.id.iv_guardar);
        iv_save.setVisibility(View.VISIBLE);
        iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar la receta
                //Recuperar todos los datos
                Toast.makeText(getApplicationContext(),"GUARDAR!!",Toast.LENGTH_SHORT).show();
            }
        });
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

    private void addIngrediente(View vista){
        TableRow ingredienteRow = new TableRow(this);
        ingredienteRow.setBackground(ContextCompat.getDrawable(activity, R.drawable.background_table_border));
        ingredienteRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        View ingredienteRowInterno = getLayoutInflater().inflate(R.layout.elemento_add_ingrediente, null,false);
        EditText et_ingrediente = (EditText) ingredienteRowInterno.findViewById(R.id.et_name_ingrediente);
        et_ingrediente.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    View view = getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        //insertarlo en la tabla
        ingredienteRow.addView(ingredienteRowInterno);
        tl_ingredientes.addView(ingredienteRow);
    }
    private void addPaso(View vista){
        TableRow pasoRow = new TableRow(this);
        pasoRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        View pasoRowInterno = getLayoutInflater().inflate(R.layout.elemento_add_paso, null,false);
        ((TextView)pasoRowInterno.findViewById(R.id.tv_num_paso)).setText(UtilsReceipt.getPaso(paso_sig));
        EditText et_ingrediente = (EditText) pasoRowInterno.findViewById(R.id.et_name_paso);
        et_ingrediente.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    View view = getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        //insertarlo en la tabla
        pasoRow.addView(pasoRowInterno);
        tl_pasos.addView(pasoRow);
    }
    private void addVariante(View vista){
        TableRow varianteRow = new TableRow(this);
        varianteRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        View varianteRowInterno = getLayoutInflater().inflate(R.layout.elemento_add_variante, null,false);
        EditText et_ingrediente = (EditText) varianteRowInterno.findViewById(R.id.et_name_variante);
        et_ingrediente.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    View view = getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        //insertarlo en la tabla
        varianteRow.addView(varianteRowInterno);
        tl_variante.addView(varianteRow);
    }
}
