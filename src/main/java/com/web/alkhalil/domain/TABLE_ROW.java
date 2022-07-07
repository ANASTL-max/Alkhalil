package com.web.alkhalil.domain;

import java.util.ArrayList;
import java.util.List;

public class TABLE_ROW {
    public int Niveau;
    public int Nombre_mots;
    public int Nombre_lemmes;
    public int Nouveaux_lemmes;

    public static List<TABLE_ROW> table_rows(int [][] t){
        List<TABLE_ROW> list = new ArrayList<TABLE_ROW>();



        for (int i =0;i<6;i++) {
            TABLE_ROW row =new TABLE_ROW();
            row.Niveau = t[i][0];
            row.Nombre_mots= t[i][1];
            row.Nombre_lemmes= t[i][2];
            row.Nouveaux_lemmes= t[i][3];

            list.add(row);
        }
        System.out.println("SUMMARY TABLE:");
        for (int i=0 ;i<list.size();i++){
            System.out.println(list.get(i).Niveau+" "+list.get(i).Nombre_mots+" "+list.get(i).Nombre_lemmes+" "+list.get(i).Nouveaux_lemmes);
        }
        return list;
    }
}
