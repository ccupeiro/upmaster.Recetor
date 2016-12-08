package com.upvmaster.carlos.recetor.bbdd;

/**
 * Created by Carlos on 07/12/2016.
 */

public class TGroups {

    public static final String TABLE_NAME = "food_groups";

    public static final String ID = "id";
    public static final String DESCRIPTION = "description";

    public static final String CREATE_TABLE = "CREATE TABLE '"+TABLE_NAME+"' ("+
            "`"+ID+"`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
            "`"+DESCRIPTION+"`	TEXT)";
}
