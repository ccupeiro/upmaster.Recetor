package com.upvmaster.carlos.recetor.dao;

/**
 * Created by Carlos on 07/12/2016.
 */

public class TSteps {
    public static final String TABLE_NAME = "steps";

    public static final String ID = "id";
    public static final String NUM = "num";
    public static final String DESCRIPTION = "description";
    public static final String RECEIPT_ID = "receiptId";

    public static final String CREATE_TABLE = "CREATE TABLE '"+TABLE_NAME+"' ("+
            "`"+ID+"`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
            "`"+NUM+"` INTEGER NOT NULL,"+
            "`"+DESCRIPTION+"`	TEXT,"+
            "`"+RECEIPT_ID+"`	INTEGER)";
}
