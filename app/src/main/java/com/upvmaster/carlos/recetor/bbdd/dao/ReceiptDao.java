package com.upvmaster.carlos.recetor.bbdd.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.upvmaster.carlos.recetor.bbdd.TIngredientes;
import com.upvmaster.carlos.recetor.bbdd.TReceipt;
import com.upvmaster.carlos.recetor.bbdd.TSteps;
import com.upvmaster.carlos.recetor.bbdd.TVariants;
import com.upvmaster.carlos.recetor.entities.Ingrediente;
import com.upvmaster.carlos.recetor.entities.Receipt;
import com.upvmaster.carlos.recetor.entities.Step;
import com.upvmaster.carlos.recetor.entities.Variante;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Carlos on 08/12/2016.
 */

public class ReceiptDao {

    private SQLiteDatabase db;

    public ReceiptDao(SQLiteDatabase db){
        this.db = db;
    }

    public int insert(Receipt receta){
        SQLiteStatement stmt_receipt = null;
        long id = -1L;
        try {
            //Tabla Receipt
            stmt_receipt = db.compileStatement(TReceipt.INSERT);
            stmt_receipt.bindString(1,receta.getName());
            stmt_receipt.bindLong(2,receta.getGroup());
            if(receta.getSrc_photo()==null){
                stmt_receipt.bindNull(3);
            }else{
                stmt_receipt.bindString(3,receta.getSrc_photo());
            }
            id = stmt_receipt.executeInsert();
            receta.setId((int)id);
            //Tabla Ingredients
            for(Ingrediente ing : receta.getList_ingredients()){
                insert_Ingrediente(ing,receta.getId());
            }

            //Tabla Steps
            for(Step paso : receta.getList_steps()){
                insert_Step(paso,receta.getId());
            }

            //Tabla Variants
            for(Variante var : receta.getList_variantes()){
                insert_Variant(var,receta.getId());
            }

        } catch (SQLiteException e) {
            delete((int)id);
            throw e;
        } finally {
            stmt_receipt.close();
        }
        return (int)id;
    }

    public void update_src_photo(Receipt receta){
        SQLiteStatement stmt=null;
        try{
            stmt = db.compileStatement(TReceipt.UPDATE_FOTO);
            stmt.bindString(1, receta.getSrc_photo());
            stmt.bindLong(2, receta.getId());
            stmt.executeUpdateDelete();
        }catch (SQLiteException e){
            throw e;
        }finally {
            if(stmt!=null) {
                stmt.close();
            }
        }
    }

    public Receipt randomReceipt(){
        Receipt random = null;
        Cursor c = null;
        try {
            c = db.rawQuery(TReceipt.SELECT_RANDOM_RECEIPT,null);
            if(c.moveToFirst()){
                random = new Receipt();
                random.setId(c.getInt(c.getColumnIndex(TReceipt.ID)));
                String name = c.getString(c.getColumnIndex(TReceipt.NAME)).trim();
                random.setName(Character.toUpperCase(name.charAt(0))+name.substring(1));
                random.setGroup(c.getInt(c.getColumnIndex(TReceipt.GROUP)));
                random.setSrc_photo(c.getString(c.getColumnIndex(TReceipt.IMAGE)));
                    //Ingredientes
                random.setList_ingredients(getAllIngredientsReceipt(random.getId()));
                    //Pasos
                random.setList_steps(getAllStepsReceipt(random.getId()));
                    //Variantes
                random.setList_variantes(getAllVariantsReceipt(random.getId()));
            }
        } catch (SQLiteException e) {
            throw e;
        } catch (Exception e) {
            Log.e("GET_RANDOM",e.getMessage());
        } finally {
            c.close();
        }
        return random;
    }

    public List<Receipt> getGroupList(){
        return getAllReceipt(TReceipt.SELECT_GROUP_LIST);
    }

    public List<Receipt> getAlphList() throws Exception{
        List<Receipt> lista = getAllReceipt(TReceipt.SELECT_ALPHABETIC_LIST);
        Collections.sort(lista);
        return lista;
    }

    public List<Receipt> getAllReceipt(String query){
        List<Receipt> lista = new ArrayList<Receipt>();
        Cursor c = null;
        try {
            c = db.rawQuery(query,null);
            if(c.moveToFirst()){
                do{
                    Receipt r = new Receipt();
                    r.setId(c.getInt(c.getColumnIndex(TReceipt.ID)));
                    String name = c.getString(c.getColumnIndex(TReceipt.NAME)).trim();
                    r.setName(Character.toUpperCase(name.charAt(0))+name.substring(1));
                    r.setGroup(c.getInt(c.getColumnIndex(TReceipt.GROUP)));
                    r.setSrc_photo(c.getString(c.getColumnIndex(TReceipt.IMAGE)));
                    if(r.getSrc_photo()!=null){
                        r.setSrc_photo(r.getSrc_photo().trim());
                    }
                    //Ingredientes
                    r.setList_ingredients(getAllIngredientsReceipt(r.getId()));
                    //Pasos
                    r.setList_steps(getAllStepsReceipt(r.getId()));
                    //Variantes
                    r.setList_variantes(getAllVariantsReceipt(r.getId()));
                    lista.add(r);
                }while(c.moveToNext());
            }
        } catch (SQLiteException e) {
            throw e;
        } catch (Exception e) {
            Log.e("GET_RECETAS",e.getMessage());
        } finally {
            c.close();
        }
        return lista;
    }

    public List<Ingrediente> getAllIngredientsReceipt(int id) throws Exception{
        List<Ingrediente> lst_ing = new ArrayList<>();
        Cursor c = db.rawQuery(TIngredientes.SELECT_FROM_RECEIPT,new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            do{
                Ingrediente i = new Ingrediente();
                i.setId(c.getInt(c.getColumnIndex(TIngredientes.ID)));
                String name =c.getString(c.getColumnIndex(TIngredientes.NAME)).trim();
                i.setName(Character.toUpperCase(name.charAt(0))+name.substring(1));
                i.setCantidad(c.getString(c.getColumnIndex(TIngredientes.QUANTITY)));
                if(i.getCantidad()!=null){
                    i.setCantidad(i.getCantidad().trim());
                }
                lst_ing.add(i);
            }while(c.moveToNext());
        }
        return lst_ing;
    }

    public List<Step> getAllStepsReceipt(int id) throws Exception{
        List<Step> lst_steps = new ArrayList<>();
        Cursor c = db.rawQuery(TSteps.SELECT_FROM_RECEIPT,new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            do{
                Step paso = new Step();
                paso.setId(c.getInt(c.getColumnIndex(TSteps.ID)));
                paso.setPos_paso(c.getInt(c.getColumnIndex(TSteps.NUM)));
                String description = c.getString(c.getColumnIndex(TSteps.DESCRIPTION));
                if(description!=null){
                    paso.setDescription(Character.toUpperCase(description.trim().charAt(0))
                            +description.trim().substring(1));
                }else{
                    paso.setDescription(description);
                }

                lst_steps.add(paso);
            }while(c.moveToNext());
        }
        return lst_steps;
    }

    public List<Variante> getAllVariantsReceipt(int id) throws Exception{
        List<Variante> lst_variant = new ArrayList<>();
        Cursor c = db.rawQuery(TVariants.SELECT_FROM_RECEIPT,new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            do{
                Variante var = new Variante();
                var.setId(c.getInt(c.getColumnIndex(TVariants.ID)));
                String description = c.getString(c.getColumnIndex(TVariants.DESCRIPTION));
                if(description!=null){
                    var.setDescription(Character.toUpperCase(description.trim().charAt(0))
                            +description.trim().substring(1));
                }else{
                    var.setDescription(description);
                }
                lst_variant.add(var);
            }while(c.moveToNext());
        }
        return lst_variant;
    }

    public void delete(int idReceipt){
        SQLiteStatement stmt_receipt = null,stmt_ing=null,stmt_step = null, stmt_var = null;

        try {
            //Tabla Receipt
            stmt_receipt = db.compileStatement(TReceipt.DELETE);
            stmt_receipt.bindLong(1, idReceipt);
            stmt_receipt.executeUpdateDelete();
            //Tabla Ingredients
            stmt_ing = db.compileStatement(TIngredientes.DELETE_BY_RECEIPT);
            stmt_ing.bindLong(1, idReceipt);
            stmt_ing.executeUpdateDelete();
            //Tabla Steps
            stmt_step = db.compileStatement(TSteps.DELETE_BY_RECEIPT);
            stmt_step.bindLong(1, idReceipt);
            stmt_step.executeUpdateDelete();
            //Tabla Variants
            stmt_var = db.compileStatement(TVariants.DELETE_BY_RECEIPT);
            stmt_var.bindLong(1, idReceipt);
            stmt_var.executeUpdateDelete();
        } catch (SQLiteException e) {
            throw e;
        } finally {
            if(stmt_receipt!=null) {
                stmt_receipt.close();
            }
            if(stmt_ing!=null) {
                stmt_ing.close();
            }
            if(stmt_step!=null) {
                stmt_step.close();
            }
            if(stmt_var!=null) {
                stmt_var.close();
            }
        }
    }

    public void update_Ingrediente(Ingrediente ing, int Receipt_id){
        SQLiteStatement stmt_ing=null;
        try{
            stmt_ing = db.compileStatement(TIngredientes.UPDATE);
            stmt_ing.bindLong(1, Receipt_id);
            stmt_ing.bindString(2, ing.getName());
            if(ing.getCantidad()==null){
                stmt_ing.bindNull(3);
            }else{
                stmt_ing.bindString(3, ing.getCantidad());
            }
            stmt_ing.bindLong(4, ing.getId());
            stmt_ing.executeUpdateDelete();
        }catch (SQLiteException e){
            throw e;
        }finally {
            if(stmt_ing!=null) {
                stmt_ing.close();
            }
        }
    }
    public void insert_Ingrediente(Ingrediente ing, int Receipt_id){
        SQLiteStatement stmt_ing=null;
        try{
            stmt_ing = db.compileStatement(TIngredientes.INSERT);
            stmt_ing.bindLong(1, Receipt_id);
            stmt_ing.bindString(2, ing.getName());
            if(ing.getCantidad()==null){
                stmt_ing.bindNull(3);
            }else{
                stmt_ing.bindString(3, ing.getCantidad());
            }
            stmt_ing.executeInsert();
        }catch (SQLiteException e){
            throw e;
        }finally {
            if(stmt_ing!=null) {
                stmt_ing.close();
            }
        }
    }

    public void update_Step(Step paso, int Receipt_id){
        SQLiteStatement stmt_step=null;
        try{
            stmt_step = db.compileStatement(TSteps.UPDATE);
            stmt_step.bindLong(1, Receipt_id);
            stmt_step.bindLong(2, paso.getPos_paso());
            stmt_step.bindString(3, paso.getDescription());
            stmt_step.bindLong(4, paso.getId());
            stmt_step.executeUpdateDelete();
        }catch (SQLiteException e){
            throw e;
        }finally {
            if(stmt_step!=null) {
                stmt_step.close();
            }
        }
    }
    public void insert_Step(Step paso, int Receipt_id){
        SQLiteStatement stmt_step=null;
        try{
            stmt_step = db.compileStatement(TSteps.INSERT);
            stmt_step.bindLong(1, Receipt_id);
            stmt_step.bindLong(2, paso.getPos_paso());
            stmt_step.bindString(3, paso.getDescription());
            stmt_step.executeInsert();
        }catch (SQLiteException e){
            throw e;
        }finally {
            if(stmt_step!=null) {
                stmt_step.close();
            }
        }
    }

    public void update_Variant(Variante var, int Receipt_id){
        SQLiteStatement stmt_var=null;
        try{
            stmt_var = db.compileStatement(TVariants.UPDATE);
            stmt_var.bindLong(1, Receipt_id);
            stmt_var.bindString(2, var.getDescription());
            stmt_var.bindLong(3, var.getId());
            stmt_var.executeUpdateDelete();
        }catch (SQLiteException e){
            throw e;
        }finally {
            if(stmt_var!=null) {
                stmt_var.close();
            }
        }
    }
    public void insert_Variant(Variante var, int Receipt_id){
        SQLiteStatement stmt_var=null;
        try{
            stmt_var = db.compileStatement(TVariants.INSERT);
            stmt_var.bindLong(1, Receipt_id);
            stmt_var.bindString(2, var.getDescription());
            stmt_var.executeInsert();
        }catch (SQLiteException e){
            throw e;
        }finally {
            if(stmt_var!=null) {
                stmt_var.close();
            }
        }
    }

    public void update(Receipt receta){
        SQLiteStatement stmt_receipt = null;
        try {
            //Tabla Receipt
            stmt_receipt = db.compileStatement(TReceipt.UPDATE);

            stmt_receipt.bindString(1, receta.getName());
            stmt_receipt.bindLong(2,receta.getGroup());
            if(receta.getSrc_photo()==null){
                stmt_receipt.bindNull(3);
            }else{
                stmt_receipt.bindString(3,receta.getSrc_photo());
            }
            stmt_receipt.bindLong(4,receta.getId());

            stmt_receipt.executeUpdateDelete();

            //Tabla Ingredients
            for(Ingrediente ing : receta.getList_ingredients()){
                if(ing.getId()!=-1){
                    update_Ingrediente(ing,receta.getId());
                }else{
                    insert_Ingrediente(ing,receta.getId());
                }
            }

            //Tabla Steps
            for(Step paso : receta.getList_steps()){
                if(paso.getId()!=-1){
                    update_Step(paso,receta.getId());
                }else{
                    insert_Step(paso,receta.getId());
                }
            }

            //Tabla Variants
            for(Variante var : receta.getList_variantes()){
                if(var.getId()!=-1){
                    update_Variant(var,receta.getId());
                }else{
                    insert_Variant(var,receta.getId());
                }
            }
        } catch (SQLiteException e) {
            throw e;
        } finally {
            if(stmt_receipt!=null) {
                stmt_receipt.close();
            }
        }
    }

}
