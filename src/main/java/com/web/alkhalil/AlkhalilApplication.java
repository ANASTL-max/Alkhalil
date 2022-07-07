package com.web.alkhalil;

import com.web.alkhalil.domain.Instance;
import com.web.alkhalil.domain.NWord;
import com.web.alkhalil.domain.TABLE_ROW;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.web.alkhalil.domain.NWord.*;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AlkhalilApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(AlkhalilApplication.class, args);


	}

}
