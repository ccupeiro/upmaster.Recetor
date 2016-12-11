package com.upvmaster.carlos.recetor.bbdd;

/**
 * Created by Carlos on 07/12/2016.
 */

public class TGroups {

    public static final String TABLE_NAME = "food_groups";

    public static final String ID = "id";
    public static final String DESCRIPTION = "name";

    public static final String CREATE_TABLE = "CREATE TABLE '"+TABLE_NAME+"' ("+
            "`"+ID+"`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
            "`"+DESCRIPTION+"`	TEXT)";

    public static final String SELECT_ALL = "SELECT * FROM "+TABLE_NAME+" ORDER BY "+ID;
    public static final String SELECT_NAME = "SELECT "+DESCRIPTION+" FROM "+TABLE_NAME+" WHERE "+ID+"=?";
    public static final String INSERT_EX = "INSERT INTO '"+TABLE_NAME+"' ("+ID+","+DESCRIPTION+") " +
            "VALUES (0,'Aperitivos y Entrantes'),"+
            "(1,'Sopas'),"+
            "(2,'Pastas y Arroces'),"+
            "(3,'Platos principales'),"+
            "(4,'Acompañamientos'),"+
            "(5,'Empanadas, Quiches y Pizzas'),"+
            "(6,'Pan y Bollería'),"+
            "(7,'Salsas, DIPS y Patés'),"+
            "(8,'Salsas y cremas dulces, mermeladas'),"+
            "(9,'Postres y dulces'),"+
            "(10,'Repostería y pastelería'),"+
            "(11,'Bebidas')";
}
