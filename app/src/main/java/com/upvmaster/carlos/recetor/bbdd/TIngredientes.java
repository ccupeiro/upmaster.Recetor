package com.upvmaster.carlos.recetor.bbdd;

/**
 * Created by Carlos on 07/12/2016.
 */

public class TIngredientes {
    public static final String TABLE_NAME = "ingredientes";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String RECEIPT_ID = "receiptId";

    public static final String CREATE_TABLE = "CREATE TABLE '"+TABLE_NAME+"' ("+
            "`"+ID+"`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
            "`"+NAME+"` TEXT NOT NULL,"+
            "`"+QUANTITY+"`	NUMERIC,"+
            "`"+RECEIPT_ID+"`	INTEGER)";
}
