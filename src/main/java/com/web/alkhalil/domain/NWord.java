package com.web.alkhalil.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NWord {
    private int n;
    private String lem;


    public String getLem() {return lem;}
    public void setLem(String lem) {this.lem = lem;}

    public int getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }


    // pour lire les fichier .XML géneré par le Lemme Elkhelil
    public static void xml_to_Instance_List(File file, List<Instance> instances, String word_Or_lemma) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String ligne;
        String word;
        String[] str;
        while ((ligne = br.readLine()) != null) {
            if (ligne.contains("</" + word_Or_lemma + ">")) {
                word = ligne.replace("</" + word_Or_lemma + ">", "");
                word = word.replace("<" + word_Or_lemma + ">", "");
                word = word.replaceAll("\\s+", "");
                word = word.replaceAll("(\\–)?(\\[)?(\\*)?(\\/)?(\\,)?(\\—)?(\\])?(\\꞉)?(\\…)?(\\»)?(\\«)?(\\')?(\\))?(\\()?(؟)?(،)?(؛)?(\\.)?(\")?(-)?(!)?(:)?(ǃ)?", "");

                //!!!!!supprimer les . ? ! ensuit tester si non vide apres creee l'instance si vide continue
                if (word.isEmpty() || word.equals("\uFEFF") || isNumeric(word) || !word.matches("^[\u0600-\u06FF]+$"))continue;


                boolean contain = false;
                int indice = 0;
                for (int i = 0; i < instances.size(); i++) {
                    if (instances.get(i).mot.equals(word)) {
                        contain = true;
                        indice = i;
                        break;
                    }
                }

                if (contain == true) {
                    instances.get(indice).nbr++;
                } else {
                    Instance inst = new Instance();
                    inst.mot = word;
                    inst.nbr = 1;
                    instances.add(inst);
                }
            }
        }

    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    //
    public static List<Instance> directory_xml_to_Instance_List(String fichier, String word_Or_lemma) throws IOException {

        File file = new File(fichier);

        List<Instance> list = new ArrayList<Instance>();

        for (File f : file.listFiles()) {

            xml_to_Instance_List(f, list, word_Or_lemma);

        }
        return list;
    }

    public static List<List<Instance>> generate_list_of_instaces(File file, String word_Or_lemma) throws IOException {

        List<List<Instance>> list_instances = new ArrayList<List<Instance>>();
        int i = 0;

        for (File f : file.listFiles()) {


            if (f.isDirectory()) {

                List<Instance> instances;

                instances = directory_xml_to_Instance_List(f.getPath(), word_Or_lemma);

                list_instances.add(instances);

            }

        }
        return list_instances;
        }

        public static int new_lemma_counter(List<Instance> list_new, List<Instance> list_old){
        int cpm=0;
        String search;
        String word;
        boolean found=false;

        for (int i = 0; i< list_new.size(); i++){
            search = list_new.get(i).mot;
            found=false;

            for (int j = 0; j< list_old.size(); j++){
                word = list_old.get(j).mot;
                if (search.equals(word)){found=true;break;}

            }
            if (found==false){cpm++;}
        }

return cpm;
        }

        public static int [][] summary_table(List<List<Instance>> list_instances_word,	List<List<Instance>> list_instances_lemma){
           int [][] T =new int[6][4];
            int cmp0=new_lemma_counter(list_instances_lemma.get(0),list_instances_lemma.get(0));
            int cmp1=new_lemma_counter(list_instances_lemma.get(1),list_instances_lemma.get(0));
            int cmp2=new_lemma_counter(list_instances_lemma.get(2),list_instances_lemma.get(1));
            int cmp3=new_lemma_counter(list_instances_lemma.get(3),list_instances_lemma.get(2));
            int cmp4=new_lemma_counter(list_instances_lemma.get(4),list_instances_lemma.get(3));
            int cmp5=new_lemma_counter(list_instances_lemma.get(5),list_instances_lemma.get(4));

            //les niveaux
            T[0][0]=1;
            T[1][0]=2;
            T[2][0]=3;
            T[3][0]=4;
            T[4][0]=5;
            T[5][0]=6;
            //nompbre de mot
            T[0][1]=list_instances_word.get(0).size();
            T[1][1]=list_instances_word.get(1).size();
            T[2][1]=list_instances_word.get(2).size();
            T[3][1]=list_instances_word.get(3).size();
            T[4][1]=list_instances_word.get(4).size();
            T[5][1]=list_instances_word.get(5).size();
            //nombre de lemme
            T[0][2]=list_instances_lemma.get(0).size();
            T[1][2]=list_instances_lemma.get(1).size();
            T[2][2]=list_instances_lemma.get(2).size();
            T[3][2]=list_instances_lemma.get(3).size();
            T[4][2]=list_instances_lemma.get(4).size();
            T[5][2]=list_instances_lemma.get(5).size();

            //nouveaux lemmes
            T[0][3]=cmp0;
            T[1][3]=cmp1;
            T[2][3]=cmp2;
            T[3][3]=cmp3;
            T[4][3]=cmp4;
            T[5][3]=cmp5;

            return T;

        }

        public static List<Instance> list_general(List<List<Instance>> list){
            List<Instance> l=new ArrayList<Instance>();
            String mot="";
            String word ="";
            int nbr=0;
            int indice = 0;
            boolean found=false;


            for (int i =0;i<list.size();i++){
                for (int j =0;j<list.get(i).size();j++){

                    word =list.get(i).get(j).mot;
                    nbr = list.get(i).get(j).nbr;

                    //check si word deja existe

                    found=false;

                   for (int h=0;h<l.size();h++){
                        mot=l.get(h).mot;
                        if (word.equals(mot)){found=true;indice=h;break;}
                    }

                    if (found==true){
                        l.get(indice).nbr+= nbr;
                    }else {
                        Instance inst = new Instance();
                        inst.mot = word;
                        inst.nbr = nbr;
                        l.add(inst);
                    }
                }
            }
            Collections.sort(l, new DistanceComparator());

        return l;
        }

        public List<Instance> n_instace_plus_frequent(List<Instance> list){
            List<Instance> l= new ArrayList<Instance>();
        int x=this.n;
            for (int i = 0; i < x; i++) l.add(list.get(i));
            System.out.println("l.size = "+l.size());
            return l;
        }


    public double  frequence(List<Instance> list){

        int som_total=0;
        int som_lem=0;

        for (int i =0;i<list.size();i++){
            som_total+=list.get(i).nbr;
            if (list.get(i).mot.equals(lem)) som_lem+=list.get(i).nbr;
        }
        System.out.println("som_lem & som_total "+som_lem+" /  "+som_total);
        return ( (double)som_lem / (double)som_total );

    }
    public List<Integer> niveaux(List<List<Instance>> list){

        List<Integer> niv =new ArrayList<Integer>();


        for (int i =0;i<list.size();i++){
            for (int j =0;j<list.get(i).size();j++){

                if (list.get(i).get(j).mot.equals(lem)){
                    niv.add(i+1);
                    break;
                }
            }}


        return niv;
    }
}