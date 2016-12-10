package com.upvmaster.carlos.recetor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.bbdd.DBHelper;
import com.upvmaster.carlos.recetor.bbdd.dao.GroupDao;
import com.upvmaster.carlos.recetor.entities.Group;
import com.upvmaster.carlos.recetor.entities.Receipt;
import com.upvmaster.carlos.recetor.utils.UtilsReceipt;

import java.util.List;

/**
 * Created by carlos.cupeiro on 07/12/2016.
 */

public class AddReceipt_Activity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    private TableLayout tl_ingredientes;
    private TableLayout tl_pasos;
    private TableLayout tl_variante;
    private EditText et_titulo;
    private ImageView iv_imagen;
    private Spinner sp_group;
    private Activity activity;
    private int paso_sig=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receta);
        activity = this;
        //Grupo
        sp_group = (Spinner) findViewById(R.id.spinner_group);
        new GetGroupListTask().execute();
        inicializarToolbar();
        //Titulo
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
        //Foto
        iv_imagen = (ImageView) findViewById(R.id.iv_image);
        iv_imagen.setImageResource(R.drawable.logo);
        CoordinatorLayout cl_foto = (CoordinatorLayout) findViewById(R.id.cl_camara);
        cl_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFoto(null);
            }
        });
        //Tablas
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
                saveReceipt();
            }
        });
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

    private void clickFoto(View view){
        //TODO pedir permiso de camara
        Runnable runCaptura = new Runnable() {
            @Override
            public void run() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        };

        Runnable runGallery = new Runnable() {
            @Override
            public void run() {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,REQUEST_IMAGE_GALLERY);
            }
        };
        createAlertCamera(runCaptura,runGallery);
    }

    private void createAlertCamera(final Runnable runCapture, final Runnable runGallery){
        AlertDialog.Builder mBuilderAlertDialog = new AlertDialog.Builder(activity, R.style.alert_dialog_gota);
        mBuilderAlertDialog.setTitle("Foto");
        mBuilderAlertDialog.setMessage("De donde quiere sacar la foto");
        mBuilderAlertDialog.setCancelable(true);
        mBuilderAlertDialog.setPositiveButton("Cámara", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                runCapture.run();
            }
        });
        mBuilderAlertDialog.setNegativeButton("Galería", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                runGallery.run();
            }
        });
        mBuilderAlertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //Guardar la foto
            iv_imagen.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            iv_imagen.setImageBitmap(imageBitmap);
        }
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

    private void saveReceipt(){
        //Recoger la info de la receta
        Receipt receta =  new Receipt();

        new SaveReceiptTask().execute(receta);
    }

    private class SaveReceiptTask extends AsyncTask<Receipt,Void,Boolean>{
        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
            pd.setCancelable(false);
            pd.setMessage("Cargando listas");
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Receipt... receipts) {
            Toast.makeText(getApplicationContext(),"GUARDAR!!",Toast.LENGTH_SHORT).show();
            return true;
        }
        @Override
        protected void onPostExecute(Boolean resul) {
            if(pd!=null)
                pd.dismiss();
            if(resul){
                Toast.makeText(getApplicationContext()
                        ,"Se ha guardado la receta"
                        ,Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext()
                        ,"No se ha guardado la receta! Error"
                        ,Toast.LENGTH_LONG).show();
            }
            activity.finish();
        }
    }

    private class GetGroupListTask extends AsyncTask<Void,Void,Boolean>{

        private ProgressDialog pd;
        private List<Group> list_group;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
            pd.setCancelable(false);
            pd.setMessage("Cargando listas");
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            SQLiteDatabase db = DBHelper.getDatabase(activity);
            try {
                GroupDao dao = new GroupDao(db);
                list_group = dao.getAllGroups();
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
            if(list_group!=null && resul){
                ArrayAdapter<Group> dataAdapter = new ArrayAdapter<>(activity,android.R.layout.simple_spinner_item,list_group);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_group.setAdapter(dataAdapter);
            }else{
                Toast.makeText(getApplicationContext()
                        ,"No se ha cargado la lista de grupos! Error"
                        ,Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }
}
