package com.upvmaster.carlos.recetor.utils;

/**
 * Created by Carlos on 03/12/2016.
 */

public class UtilsReceipt {

    public static String getGroupName(int group_id){
        switch (group_id){
            case 0:
                return "Arroces";
            case 1:
                return "Caldos";
            case 2:
                return "Pastas";
            case 3:
                return "Verduras";
            default:
                return "Sin Grupo";
        }
    }

    public static String doubleToStringReceipt(double d){
        //TODO ver como devolver la fracción si es menor que 0
        return d+"";
    }
    public static String getPaso(int paso){
        return paso+"º";
    }

}
