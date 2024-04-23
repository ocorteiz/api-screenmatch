package com.ocorteiz.apiscreenmatch;

import com.ocorteiz.apiscreenmatch.main.Principal;
import com.ocorteiz.apiscreenmatch.model.DadosEpisodio;
import com.ocorteiz.apiscreenmatch.model.DadosSerie;
import com.ocorteiz.apiscreenmatch.model.DadosTemporada;
import com.ocorteiz.apiscreenmatch.service.ConsumirApi;
import com.ocorteiz.apiscreenmatch.service.Converterdados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApiScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibirMenu();
	}
}
