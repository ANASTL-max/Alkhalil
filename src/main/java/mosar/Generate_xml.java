package mosar;

import java.io.File;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pro
 */
public class Generate_xml {
    public static void main(String[] args) throws IOException {
        File file =new File("src/main/java/mosar/Mosar");
        txt_to_xml r=new txt_to_xml();
       r.directory_txt_to_directory_xml(file);

    }
    
}
