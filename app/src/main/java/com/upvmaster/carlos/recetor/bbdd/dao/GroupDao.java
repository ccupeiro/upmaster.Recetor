package com.upvmaster.carlos.recetor.bbdd.dao;

import android.database.sqlite.SQLiteDatabase;

import com.upvmaster.carlos.recetor.entities.Group;

import java.util.List;

/**
 * Created by Carlos on 08/12/2016.
 */

public class GroupDao {
    private SQLiteDatabase db;

    public GroupDao(SQLiteDatabase db){
        this.db = db;
    }

    public void insert(String name_group){

    }

    public String getName(int id){
        return null;
    }

    public void delete(int id){

    }
    public List<Group> getAllGroups(){
        return null;
    }
}
