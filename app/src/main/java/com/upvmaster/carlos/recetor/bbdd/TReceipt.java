package com.upvmaster.carlos.recetor.bbdd;

/**
 * Created by Carlos on 07/12/2016.
 */

public class TReceipt {
    public static final String TABLE_NAME = "receipt";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String GROUP = "id_group";
    public static final String IMAGE = "image";


    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS '"+TABLE_NAME+"' ("+
            "`"+ID+"`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
            "`"+NAME+"` TEXT NOT NULL,"+
            "`"+GROUP+"`	INTEGER,"+
            "`"+IMAGE+"`	TEXT)";

    public static final String INSERT = "INSERT INTO "+ TABLE_NAME
            + "("+ NAME+","
            + GROUP+","
            + IMAGE+")"
            +"VALUES (?,?,?)";

    public static final String UPDATE ="UPDATE "+TABLE_NAME
            +" SET "+NAME+"= ? ,"
            + GROUP +"= ? ,"
            + IMAGE + "= ? "
            +"WHERE "+ID+"= ?";
    public static final String DELETE ="DELETE FROM "+TABLE_NAME
            +" WHERE "+ID+"=?";

    public static final String SELECT_ALPHABETIC_LIST = "SELECT * FROM "+TABLE_NAME+" ORDER BY "+NAME;

    public static final String SELECT_GROUP_LIST = "SELECT * FROM "+TABLE_NAME+" ORDER BY "+GROUP;

    public static final String SELECT_RANDOM_RECEIPT = "SELECT * FROM " +TABLE_NAME
            +" ORDER BY RANDOM()"
            +" LIMIT 1";

    public static final String UPDATE_FOTO ="UPDATE "+TABLE_NAME
            +" SET "+ IMAGE + "= ? "
            +"WHERE "+ID+"= ?";

    public static final String INSERT_EX = "INSERT INTO '"+TABLE_NAME+"' ("+ID+","+NAME+","+GROUP+","+IMAGE+") VALUES"+
            "(1,'ensaladilla rusa',0,''),"+
            "(2,'pisto con patatas',0,''),"+
            "(3,'arroz a la cubana',2,NULL),"+
            "(4,'lasaña boloñesa',2,NULL),"+
            "(5,'pollo relleno de Navidad',3,''),"+
            "(6,'cocido madrileño',3,''),"+
            "(7,'ajoblanco',1,NULL),"+
            "(8,'crema de calabacín',1,''),"+
            "(9,'empanada de bonito',5,NULL),"+
            "(10,'quiche de bacon y champiñones',5,''),"+
            "(11,'profiteroles',9,NULL),"+
            "(12,'tiramisú',9,'')";
}
