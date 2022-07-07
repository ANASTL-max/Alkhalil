package com.web.alkhalil.Contoller;

import com.web.alkhalil.domain.Instance;
import com.web.alkhalil.domain.NWord;
import com.web.alkhalil.domain.TABLE_ROW;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.web.alkhalil.domain.NWord.*;
import static com.web.alkhalil.domain.NWord.list_general;

@org.springframework.stereotype.Controller
public class Controller {

   @GetMapping("/index")
    public String ViewForm(Model model) throws IOException {
        model.addAttribute("NWord",new NWord());
       model.addAttribute("lem",new NWord());

        return "index";
    }

    @PostMapping("/controllerdisplay")
    public String PostFormdisplay(@ModelAttribute NWord nWord, BindingResult result, Model model) throws IOException {

        File file =new File("src/main/resources/Lemme_khalil");
        List<List<Instance>> list_instances_word  = generate_list_of_instaces(file,"word");
        List<List<Instance>> list_instances_lemma = generate_list_of_instaces(file,"lemma");

        int t[][]=	summary_table(list_instances_word,list_instances_lemma);

        List<TABLE_ROW> table_rows = TABLE_ROW.table_rows(t);

        model.addAttribute("table_rows",table_rows);
        return "display";
    }


    @PostMapping("/controller")
    public String PostForm(@ModelAttribute NWord nWord, BindingResult result, Model model) throws IOException {

       model.addAttribute("nWord",nWord);

        File file =new File("src/main/resources/Lemme_khalil");
        List<List<Instance>> list_instances_word  = generate_list_of_instaces(file,"word");
        List<List<Instance>> list_instances_lemma = generate_list_of_instaces(file,"lemma");


        List<Instance> list_n_word=nWord.n_instace_plus_frequent(list_general(list_instances_word));
        List<Instance> list_n_lemma=nWord.n_instace_plus_frequent(list_general(list_instances_lemma));

        model.addAttribute("list_n_word",list_n_word);
        model.addAttribute("list_n_lemma",list_n_lemma);

       return "mot_lem_plus_frequents";
    }


    @PostMapping("/controller2")
    public String PostFormlem(@ModelAttribute NWord lem, BindingResult result, Model model) throws IOException {

        model.addAttribute("lem",lem);

        File file =new File("src/main/resources/Lemme_khalil");

        List<List<Instance>> list_lemma = generate_list_of_instaces(file,"lemma");
        List<Integer> lvls = lem.niveaux(list_lemma);

        model.addAttribute("lvls",lvls);


        double frequence ;
        List<Instance> list_general = list_general(generate_list_of_instaces(file,"lemma"));
        frequence = lem.frequence(list_general);

        model.addAttribute("frequence",frequence);

        return "lem_niv_frequence";
    }

}
