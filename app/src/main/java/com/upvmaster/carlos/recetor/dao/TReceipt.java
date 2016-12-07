package com.upvmaster.carlos.recetor.dao;

/**
 * Created by Carlos on 07/12/2016.
 */

public class TReceipt {
    public static final String TABLE_NAME = "receipt";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String GROUP = "group";
    public static final String IMAGE = "image";


    public static final String CREATE_TABLE = "CREATE TABLE '"+TABLE_NAME+"' ("+
            "`"+ID+"`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
            "`"+NAME+"` TEXT NOT NULL,"+
            "`"+GROUP+"`	INTEGER,"+
            "`"+IMAGE+"`	TEXT)";
}
