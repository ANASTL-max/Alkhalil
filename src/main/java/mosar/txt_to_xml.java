package mosar;

import com.web.alkhalil.domain.Instance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class txt_to_xml {

    // pour créer le fichier .XML pour un seul fichier .txt
    public void ALKhalil_lemmatization(File file, String pth) {
        String str;
        String new_file;
        str= file.getName();
        new_file=str.replace(".txt","");
        net.oujda_nlp_team.ADATAnalyzer.getInstance().processLemmatization(file.getPath(),
                "utf-8",
                pth+"/"+new_file+"−OUT−Lemma.xml",
                "utf-8");
    }

    // cette methode pour créer tous les fichiers .XML du Lemme Alkhalil respectant la meme arborescence du document Mosar
    public  void directory_txt_to_directory_xml(File file){
        String str;
        String [] T;
        File f1;
        File f2;
        for (File f : file.listFiles()) {
            if (!f.isDirectory()) {
                str= String.valueOf(f.getParentFile()).replace("src\\main\\java\\mosar\\Mosar","");
                str=str.replace("\\",",");
                T=str.split(",");
                f1=new File("src/main/java/mosar/xml_khalil/"+T[0]);
                System.out.println();
                System.out.println(f1.getName());
                System.out.println();
                if(!f1.exists()){
                    f1.mkdir();
                }else {
                    f2=new File("src/main/java/mosar/xml_khalil/"+T[0]+"/"+T[1]);
                    System.out.println(f2.getPath());
                    if (!f2.exists()){
                        f2.mkdir();
                    }else {
                       ALKhalil_lemmatization(f, String.valueOf(f2));
                    }
                }


            }else {
                directory_txt_to_directory_xml(f);
            }
        }
    }

    // pour lire les fichier .XML géneré par le Lemme Elkhelil
    public  void xml_to_Word_Instance_List(File file,List<Instance> instances) throws IOException {
        BufferedReader br =new BufferedReader(new FileReader(file));
        String ligne;
        String line;
        String [] str;
        while ((ligne=br.readLine())!=null){
            if(ligne.matches("^.</word>.$")) {
                line=ligne.replace("</word>","");
                line=line.replace("<word>","");
                // str=ligne.split(",");
                for(int i=0;i<instances.size();i++){
               // if(instances.get(i).)

                }
                System.out.println(line);
            }
        }
    }

    // pour visualiser les résultats
    public void read_directory_xml(File file) throws IOException {
        String str;
        int i=1;
        for (File f : file.listFiles()) {
            if (!f.isDirectory()) {
                System.out.println();
                System.out.println();
                System.out.println(" Le fichier : "+ f.getPath());
            //    xml_to_Word_Instance_List(f);
            }else {
                System.out.println();
                System.out.println();
                System.out.println(" Le Dossier  : "+f.getPath());
                read_directory_xml(f);
                i++;
            }
        }
    }



}