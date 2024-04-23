package com.ocorteiz.apiscreenmatch;

import com.ocorteiz.apiscreenmatch.model.DadosSerie;
import com.ocorteiz.apiscreenmatch.service.ConsumirApi;
import com.ocorteiz.apiscreenmatch.service.Converterdados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumirApi consumirApi = new ConsumirApi();
		var json = consumirApi.obterDados("https://www.omdbapi.com/?t=friends&apikey=ba089616");
		System.out.println(json);
		Converterdados converterdados = new Converterdados();
		DadosSerie dados = converterdados.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}
}
