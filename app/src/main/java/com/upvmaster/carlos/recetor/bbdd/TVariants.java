package com.upvmaster.carlos.recetor.bbdd;

/**
 * Created by Carlos on 07/12/2016.
 */

public class TVariants {
    public static final String TABLE_NAME = "variants";

    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String RECEIPT_ID = "receiptId";

    public static final String CREATE_TABLE = "CREATE TABLE '"+TABLE_NAME+"' ("+
            "`"+ID+"`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
            "`"+RECEIPT_ID+"`	INTEGER,"+
            "`"+DESCRIPTION+"`	TEXT)";

    public static final String INSERT ="INSERT INTO "+TABLE_NAME
            +" ("+RECEIPT_ID+","
            +DESCRIPTION+")"
            +" VALUES (?,?,?)";
    public static final String UPDATE ="UPDATE "+TABLE_NAME
            +" SET "+RECEIPT_ID+"= ? ,"
            + DESCRIPTION + "= ? "
            +"WHERE "+ID+"= ?";
    public static final String DELETE ="DELETE FROM "+TABLE_NAME
            +" WHERE "+ID+"=?";

    public static final String DELETE_BY_RECEIPT ="DELETE FROM "+TABLE_NAME
            +" WHERE "+RECEIPT_ID+"=?";

    public static final String SELECT_FROM_RECEIPT="SELECT * FROM "+TABLE_NAME+" WHERE "+RECEIPT_ID+"= ? ORDER BY "+ID;

    public static final String INSERT_EX = "INSERT INTO '"+TABLE_NAME+"' ("+ID+","+RECEIPT_ID+","+DESCRIPTION+") VALUES "
            +"(1,2,'Puede servirlo con arroz blanco acompañado de huevos fritos o revueltos'),"
            +"(2,3,'En vez de freir los plátanos se pueden hacer huevos poché y bañar el platano en salsa de soja. De esta forma será una receta mñas ligera')";
}
