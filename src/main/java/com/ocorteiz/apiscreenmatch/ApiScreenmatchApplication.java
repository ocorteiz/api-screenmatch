package com.ocorteiz.apiscreenmatch;

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
		ConsumirApi consumirApi = new ConsumirApi();
		var json = consumirApi.obterDados("https://www.omdbapi.com/?t=dark&apikey=ba089616");
		System.out.println(json+"\n");
		Converterdados converterdados = new Converterdados();
		DadosSerie dadosSerie = converterdados.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie+"\n");

		json = consumirApi.obterDados("https://www.omdbapi.com/?t=dark&season=1&episode=2&apikey=ba089616");
		DadosEpisodio dadosEpisodio = converterdados.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio+"\n");

		List<DadosTemporada> temporadaList = new ArrayList<>();

		for(int i = 1 ; i <= dadosSerie.totalTemporadas() ; i++){
			json = consumirApi.obterDados("https://www.omdbapi.com/?t=dark&season=" + i + "&apikey=ba089616");
			DadosTemporada dadosTemporada = converterdados.obterDados(json, DadosTemporada.class);
			temporadaList.add(dadosTemporada);
		}

		temporadaList.forEach(System.out::println);
	}
}
