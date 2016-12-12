package com.upvmaster.carlos.recetor.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.gson.Gson;
import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.bbdd.DBHelper;
import com.upvmaster.carlos.recetor.bbdd.dao.GroupDao;
import com.upvmaster.carlos.recetor.bbdd.dao.ReceiptDao;
import com.upvmaster.carlos.recetor.entities.Group;
import com.upvmaster.carlos.recetor.entities.Ingrediente;
import com.upvmaster.carlos.recetor.entities.Receipt;
import com.upvmaster.carlos.recetor.entities.Step;
import com.upvmaster.carlos.recetor.entities.Variante;
import com.upvmaster.carlos.recetor.utils.UtilsReceipt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos.cupeiro on 07/12/2016.
 */

public class AddReceipt_Activity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int SOLICITUD_PERMISO_CAMARA = 100;
    private static final int SOLICITUD_READ_EXTERNAL_STORAGE = 200;
    public static final String ID_EDIT_RECETA = "edit_RECETA";

    private String filename_temp ="temp.jpg";
    private List<Group> list_group;

    private TableLayout tl_ingredientes;
    private TableLayout tl_pasos;
    private TableLayout tl_variante;
    private EditText et_titulo;
    private ImageView iv_imagen;
    private Spinner sp_group;
    private Activity activity;
    private Receipt receta_edit=null;
    private int paso_sig=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receta);
        //Mirar que no envien nada
        String jsonReceta="";
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            jsonReceta = extras.getString(ID_EDIT_RECETA);
            if(jsonReceta!=null){
                receta_edit = new Gson().fromJson(jsonReceta,Receipt.class);
            }
        }
        activity = this;
        //Borrar imagen temporal si existe
        File mypath=new File(activity.getFilesDir(),filename_temp);
        if(mypath.exists()){
            mypath.delete();
        }
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
                addIngrediente(null,null);
            }
        });
        /*LinearLayout ll_add_step = (LinearLayout) findViewById(R.id.ll_add_paso);
        ll_add_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPaso(null,null,paso_sig);
                paso_sig++;
            }
        });
        LinearLayout ll_add_var = (LinearLayout) findViewById(R.id.ll_add_variante);
        ll_add_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVariante(null,null);
            }
        });*/
        if(receta_edit!=null){
            inicializarEdit();
        }
    }

    private void inicializarEdit(){
        //Titulo
        et_titulo.setText(receta_edit.getName());
        //Grupo en el asynctask
        //Foto
        if(receta_edit.getSrc_photo()!=null && !receta_edit.getSrc_photo().equals("")){
            mostrarImagenTemp(receta_edit.getSrc_photo());
        }
        //Ingredientes
        if(receta_edit.getList_ingredients()!=null){
            for(Ingrediente ing : receta_edit.getList_ingredients()){
                addIngrediente(null,ing);
            }
        }
        //Pasos
        if(receta_edit.getList_steps()!=null){
            for(Step paso : receta_edit.getList_steps()){
                addPaso(null,paso,paso_sig);
                paso_sig++;
            }
        }
        //Variantes
        if(receta_edit.getList_variantes()!=null){
            for(Variante variante : receta_edit.getList_variantes()){
                addVariante(null,variante);
            }
        }
    }

    private void inicializarToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        //Titulo
        TextView tv_titulo = (TextView) findViewById(R.id.tv_titulo_toolbar);
        if(receta_edit!=null){
            tv_titulo.setText("Editar Receta");
        }else{
            tv_titulo.setText("Añadir Receta");
        }

        // Icono atrás
        ImageView iv_atras = (ImageView) findViewById(R.id.iv_atras);
        iv_atras.setVisibility(View.VISIBLE);
        iv_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialogo para salir sin guardar
                dialogExitWithoutSave();
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
        iv_config.setVisibility(View.VISIBLE);
        iv_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzar_config(view);
            }
        });
    }

    private void lanzar_config(View view) {
        Intent i = new Intent(this, Preferencias_Activity.class);
        startActivity(i);
    }

    private void clickFoto(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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
            }else{
                solicitarPermisoExternalStorage();
            }
        }else{
            solicitarPermisoCamara();
        }
    }

    private void solicitarPermisoCamara() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make (activity.getCurrentFocus(), "Permiso Camara", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, SOLICITUD_PERMISO_CAMARA);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, SOLICITUD_PERMISO_CAMARA);
        }
    }
    private void solicitarPermisoExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Snackbar.make (activity.getCurrentFocus(), "Permiso Lectura de Galeria", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SOLICITUD_READ_EXTERNAL_STORAGE);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, SOLICITUD_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case SOLICITUD_PERMISO_CAMARA:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    clickFoto(null);
                } else {
                    Snackbar.make(this.getCurrentFocus(), "No se puede utilizar la camara sin permiso", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case SOLICITUD_READ_EXTERNAL_STORAGE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    clickFoto(null);
                } else {
                    Snackbar.make(this.getCurrentFocus(), "No se puede utilizar la camara sin permiso de CAMARA de acceso a la GALERIA", Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
                finish();
                break;
        }
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
            saveImagetoTemp(imageBitmap);
            File f = new File(activity.getFilesDir(), filename_temp);
            mostrarImagenTemp(f.getAbsolutePath());
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                saveImagetoTemp(imageBitmap);
                File f = new File(activity.getFilesDir(), filename_temp);
                mostrarImagenTemp(f.getAbsolutePath());
            } catch (IOException e) {
                Toast.makeText(activity,"No se ha podido cargar la imagen de galería",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void saveImagetoTemp(Bitmap image){
        File mypath=new File(activity.getFilesDir(),filename_temp);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(mypath);
            image.compress(Bitmap.CompressFormat.JPEG, 60, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            Toast.makeText(activity,"No se ha podido guardar la imagen",Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Toast.makeText(activity,"El archivo de la imagen no se ha podido cerrar correctamente",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void mostrarImagenTemp(String path){
        try {
            File f=new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            iv_imagen.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
           Toast.makeText(activity,"No se ha podido cargar la imagen",Toast.LENGTH_SHORT).show();
        }
    }

    private void addIngrediente(View vista,Ingrediente ing){
        LinearLayout ll_ingrediente = (LinearLayout) getLayoutInflater().inflate(R.layout.elemento_add_ingrediente, null,false);
        ll_ingrediente.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tl_ingredientes.addView(ll_ingrediente);





        /*TableRow ingredienteRow = new TableRow(this);
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
        if(ing!=null){
            et_ingrediente.setText(ing.getName());
            ((EditText) ingredienteRowInterno.findViewById(R.id.et_cantidad)).setText(ing.getCantidad());
        }
        //insertarlo en la tabla
        ingredienteRow.addView(ingredienteRowInterno);
        tl_ingredientes.addView(ingredienteRow);*/
    }
    private void addPaso(View vista,Step paso,int num_paso){
        TableRow pasoRow = new TableRow(this);
        pasoRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        View pasoRowInterno = getLayoutInflater().inflate(R.layout.elemento_add_paso, null,false);
        ((TextView)pasoRowInterno.findViewById(R.id.tv_num_paso)).setText(UtilsReceipt.getPaso(num_paso));
        EditText et_paso = (EditText) pasoRowInterno.findViewById(R.id.et_name_paso);
        et_paso.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        if(paso!=null){
            et_paso.setText(paso.getDescription());
        }
        //insertarlo en la tabla
        pasoRow.addView(pasoRowInterno);
        tl_pasos.addView(pasoRow);
    }
    private void addVariante(View vista,Variante variante){
        TableRow varianteRow = new TableRow(this);
        varianteRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        View varianteRowInterno = getLayoutInflater().inflate(R.layout.elemento_add_variante, null,false);
        EditText et_var = (EditText) varianteRowInterno.findViewById(R.id.et_name_variante);
        et_var.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        if(variante!=null)
            et_var.setText(variante.getDescription());
        //insertarlo en la tabla
        varianteRow.addView(varianteRowInterno);
        tl_variante.addView(varianteRow);
    }

    private void dialogExitWithoutSave(){
        final AlertDialog.Builder mBuilderAlertDialog = new AlertDialog.Builder(activity, R.style.alert_dialog_gota);
        if(receta_edit!=null){
            mBuilderAlertDialog.setTitle("Salir de Edición");
        }else{
            mBuilderAlertDialog.setTitle("Salir de Creación");
        }
        mBuilderAlertDialog.setMessage("¿Seguro que desea salir? \nNo se guardará la receta que ha rellenado hasta ahora");
        mBuilderAlertDialog.setCancelable(true);
        mBuilderAlertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
            }
        });
        mBuilderAlertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilderAlertDialog.show();
    }

    private void saveReceipt(){
        //Recoger la info de la receta
        Receipt receta =  new Receipt();
        //Nombre
        if(et_titulo.getText().toString().equals("")){
            Toast.makeText(activity,"Sin un nombre de receta no se puede guardar nada",Toast.LENGTH_SHORT).show();
            return;
        }
        receta.setName(et_titulo.getText().toString());
        //Grupo
        receta.setGroup(((Group)sp_group.getSelectedItem()).getId());
        //Foto
        if(hayFotoTemporal()){
            File directory_photo = new File(activity.getFilesDir(),"FOTOS");
            if(!directory_photo.exists())
                directory_photo.mkdirs();
            receta.setSrc_photo(directory_photo.getAbsolutePath());
        }
        //Ingredientes
        List<Ingrediente> lst_ingrediente = new ArrayList<>();
        for(int i = 0, j = tl_ingredientes.getChildCount(); i < j; i++) {
            View view = tl_ingredientes.getChildAt(i);
            if (view instanceof TableRow) {
                TableRow row = (TableRow) view;
                View vista = row.getChildAt(0);
                EditText name = (EditText) vista.findViewById(R.id.et_name_ingrediente);
                EditText cant = (EditText) vista.findViewById(R.id.et_cantidad);
                if(name.getText()== null || name.getText().toString().trim().equals(""))
                    continue;
                Ingrediente ing = new Ingrediente(name.getText().toString(), cant.getText().toString());
                lst_ingrediente.add(ing);
            }
        }
        receta.setList_ingredients(lst_ingrediente);
        //Pasos
        List<Step> lst_pasos = new ArrayList<>();
        for(int i = 0, j = tl_pasos.getChildCount(); i < j; i++) {
            View view = tl_pasos.getChildAt(i);
            if (view instanceof TableRow) {
                TableRow row = (TableRow) view;
                View vista = row.getChildAt(0);
                EditText paso_desc = (EditText) vista.findViewById(R.id.et_name_paso);
                TextView paso_num = (TextView) vista.findViewById(R.id.tv_num_paso);
                if(paso_desc.getText()== null || paso_desc.getText().toString().trim().equals(""))
                    continue;
                Step paso = new Step(UtilsReceipt.getintFromPaso(paso_num.getText().toString()),
                        paso_desc.getText().toString());
                lst_pasos.add(paso);
            }
        }
        receta.setList_steps(lst_pasos);
        //Variantes
        List<Variante> lst_variante = new ArrayList<>();
        for(int i = 0, j = tl_variante.getChildCount(); i < j; i++) {
            View view = tl_variante.getChildAt(i);
            if (view instanceof TableRow) {
                TableRow row = (TableRow) view;
                View vista = row.getChildAt(0);
                EditText var = (EditText) vista.findViewById(R.id.et_name_variante);
                if(var.getText()== null || var.getText().toString().trim().equals(""))
                    continue;
                lst_variante.add(new Variante(var.getText().toString()));
            }
        }
        receta.setList_variantes(lst_variante);
        new SaveReceiptTask(receta).execute();
    }

    private boolean hayFotoTemporal(){
        File mypath=new File(activity.getFilesDir(),filename_temp);
        return mypath.exists();
    }
    private class SaveReceiptTask extends AsyncTask<Void,Void,Boolean>{
        private ProgressDialog pd;
        private Receipt receta;

        SaveReceiptTask(Receipt receipt){
            this.receta = receipt;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
            pd.setCancelable(false);
            pd.setMessage("Guardando Receta");
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            SQLiteDatabase db = DBHelper.getDatabase(activity);
            try {
                ReceiptDao dao = new ReceiptDao(db);
                if(receta_edit!=null){
                    dao.update(receta);
                }else{
                    int id = dao.insert(receta);
                    if(id==-1) return false;
                    receta.setId(id);
                }
                //Comprobar la imagen y mover a la carpeta
                if(hayFotoTemporal()){
                    //Mover a la ruta buena y actualizar src_Photo
                    File temporal=new File(activity.getFilesDir(),filename_temp);
                    File destino = new File(receta.getSrc_photo()+"/"+receta.getId()+".jpg");
                    temporal.renameTo(destino);
                    receta.setSrc_photo(destino.getAbsolutePath());
                    dao.update_src_photo(receta);
                }
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
            if(resul){
                Toast.makeText(getApplicationContext()
                        ,"Se ha guardado la receta"
                        ,Toast.LENGTH_LONG).show();

                View2Receipt_Activity vista_activity = new View2Receipt_Activity();
                Intent i = new Intent(activity,vista_activity.getClass());
                i.putExtra(View2Receipt_Activity.ID_RECETA, new Gson().toJson(receta));
                startActivity(i);
                activity.finish();
            }else{
                Toast.makeText(getApplicationContext()
                        ,"No se ha guardado la receta! Error"
                        ,Toast.LENGTH_LONG).show();
            }
        }
    }

    private class GetGroupListTask extends AsyncTask<Void,Void,Boolean>{

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
            pd.setCancelable(false);
            pd.setMessage("Cargando lista Grupos");
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
                if(list_group!=null && receta_edit!=null && receta_edit.getGroup()!= -1){
                    int pos=-1;
                    for(Group g : list_group){
                        pos++;
                        if(g.getId()==receta_edit.getGroup()){
                            break;
                        }
                    }
                    if(pos<list_group.size()){
                        sp_group.setSelection(pos);
                    }
                }
            }else{
                Toast.makeText(getApplicationContext()
                        ,"No se ha cargado la lista de grupos! Error"
                        ,Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }
}
