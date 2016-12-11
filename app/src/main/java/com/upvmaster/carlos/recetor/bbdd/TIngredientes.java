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
            "`"+RECEIPT_ID+"`	INTEGER,"+
            "`"+NAME+"` TEXT NOT NULL,"+
            "`"+QUANTITY+"`	NUMERIC)";

    public static final String INSERT ="INSERT INTO "+TABLE_NAME
            +" ("+RECEIPT_ID+","
            + NAME+","
            +QUANTITY+")"
            +" VALUES (?,?,?)";
    public static final String UPDATE ="UPDATE "+TABLE_NAME
            +" SET "+RECEIPT_ID+"= ? ,"
            + NAME +"= ? ,"
            + QUANTITY + "= ? "
            +"WHERE "+ID+"= ?";
    public static final String DELETE ="DELETE FROM "+TABLE_NAME
            +" WHERE "+ID+"=?";

    public static final String DELETE_BY_RECEIPT ="DELETE FROM "+TABLE_NAME
            +" WHERE "+RECEIPT_ID+"=?";

    public static final String SELECT_FROM_RECEIPT="SELECT * FROM "+TABLE_NAME+" WHERE "+RECEIPT_ID+"= ? ORDER BY "+ID;

    public static final String INSERT_EX = "INSERT INTO '"+TABLE_NAME+"' ("+ID+","+RECEIPT_ID+","+NAME+","+QUANTITY+") VALUES "+
            "(1,1,'patatas',2),"+
            "(2,1,'zanahorias',2),"+
            "(3,1,'huevo',1),"+
            "(4,1,'aceitunas negras',10),"+
            "(5,1,'guisantes','50 gr'),"+
            "(6,1,'lata atún',1),"+
            "(7,1,'mayonesa','250 gr'),"+
            "(8,2,'lata tomate frito o triturado',1),"+
            "(9,2,'patatas',3),"+
            "(10,2,'cebolla grande',1),"+
            "(11,2,'berenjena',1),"+
            "(12,2,'calabacín',1),"+
            "(13,2,'pimiento rojo',1),"+
            "(14,2,'primiento verde',1),"+
            "(15,2,'aceite de oliva',''),"+
            "(16,2,'pimentón','1 cdta'),"+
            "(17,3,'arroz','400 gr'),"+
            "(18,3,'tomate frito','150 gr'),"+
            "(19,3,'mantequilla','25 gr'),"+
            "(20,3,'huevos',4),"+
            "(21,3,'plátanos',4),"+
            "(22,3,'ajos',2),"+
            "(23,3,'aceite de oliva',NULL),"+
            "(24,3,'sal',NULL),"+
            "(25,4,'pasta de lasaña','200 gr'),"+
            "(26,4,'carne picada','300 gr'),"+
            "(27,4,'cebolla',1),"+
            "(28,4,'dientes de ajo',3),"+
            "(29,4,'vino blanco',''),"+
            "(30,4,'queso rallado',''),"+
            "(31,5,'pollo entero',1),"+
            "(32,5,'carne picada cerdo','600 gr'),"+
            "(33,5,'albaricoques secos','50 gr'),"+
            "(34,5,'tocino','60 gr'),"+
            "(35,5,'patatas grandes',4),"+
            "(36,5,'huevo',1),"+
            "(37,5,'vino Jerez','1 copa'),"+
            "(38,5,'orégano','2 cdas'),"+
            "(39,5,'pimienta','1 cda'),"+
            "(40,5,'sal','1 cda'),"+
            "(41,5,'nuez moscada','1 cda'),"+
            "(42,5,'manteca de cerdo','2 cdas'),"+
            "(43,5,'maicena','1 cda'),"+
            "(44,6,'garbanzos','280 gr'),"+
            "(45,6,'chorizos',2),"+
            "(46,6,'morcilla','280 gr'),"+
            "(47,6,'panceta','90 gr'),"+
            "(48,6,'cebollas',2),"+
            "(49,6,'zanahoria',1),"+
            "(50,6,'judías verdes','100 gr'),"+
            "(51,6,'pollo','trozos'),"+
            "(52,6,'dientes de ajo',2),"+
            "(53,6,'aceite de oliva',''),"+
            "(54,7,'almendras','200 gr'),"+
            "(55,7,'dientes de ajo',3),"+
            "(56,7,'miga de pan','200 gr'),"+
            "(57,7,'agua',''),"+
            "(58,7,'aceite virgen extra',NULL),"+
            "(59,7,'vinagre',NULL),"+
            "(60,7,'sal',NULL),"+
            "(61,7,'melón',NULL),"+
            "(62,7,'uvas',NULL),"+
            "(63,7,'jamón',NULL),"+
            "(64,8,'calabacín','1/2 kg'),"+
            "(65,8,'cebolla',NULL),"+
            "(66,8,'caldo de pollo',NULL),"+
            "(67,8,'nata',NULL),"+
            "(68,8,'mantequilla',NULL),"+
            "(69,8,'harina',NULL),"+
            "(70,8,'aceite',NULL),"+
            "(71,8,'sal',NULL),"+
            "(72,9,'masa hojaldre',2),"+
            "(73,9,'tomates','1/2 kg'),"+
            "(74,9,'pimientos rojos',2),"+
            "(75,9,'primiento verde',1),"+
            "(76,9,'cebollas',2),"+
            "(77,9,'huevos',4),"+
            "(78,9,'bonito','175 gr'),"+
            "(79,9,'aceite de oliva',NULL),"+
            "(80,9,'sal',NULL),"+
            "(81,10,'masa quebrada',1),"+
            "(82,10,'bacon','175 gr'),"+
            "(83,10,'cebolla','1/2'),"+
            "(84,10,'diente de ajo',1),"+
            "(85,10,'champiñones','150 gr'),"+
            "(86,10,'queso rallado','150 gr'),"+
            "(87,10,'huevos',3),"+
            "(88,10,'nata líquida','200 ml'),"+
            "(89,10,'sal','1/2 cda'),"+
            "(90,10,'pimienta','1/2 cda'),"+
            "(91,10,'aceite de oliva','1 cda'),"+
            "(92,11,'harina','75 gr'),"+
            "(93,11,'agua','125 ml'),"+
            "(94,11,'sal','1 pizca'),"+
            "(95,11,'mantequilla','35 gr'),"+
            "(96,11,'huevos',2),"+
            "(97,11,'azucar','2 cdas'),"+
            "(98,11,'nata para montar','1 brick'),"+
            "(99,12,'galletas maria','2 paquetes'),"+
            "(100,12,'queso mascarpone','1 tarrina'),"+
            "(101,12,'queso philadelphia','1 tarrina'),"+
            "(102,12,'nata líquida','1/2 kg'),"+
            "(103,12,'café','175 ml'),"+
            "(104,12,'azucar glass','125 gr'),"+
            "(105,12,'chocolate amargo',NULL),"+
            "(106,12,'brandy','1 copa')";

}
