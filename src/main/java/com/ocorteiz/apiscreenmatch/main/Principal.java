package com.ocorteiz.apiscreenmatch.main;

import com.ocorteiz.apiscreenmatch.model.DadosSerie;
import com.ocorteiz.apiscreenmatch.model.DadosTemporada;
import com.ocorteiz.apiscreenmatch.service.ConsumirApi;
import com.ocorteiz.apiscreenmatch.service.Converterdados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=ba089616";
    private ConsumirApi consumirApi = new ConsumirApi();
    private Converterdados converterdados = new Converterdados();

    public void exibirMenu(){
        System.out.println("DIGITE O NOME DA SERIE: ");
        var nomeSerie = leitura.nextLine();

        var json = consumirApi.obterDados( ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = converterdados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie+"\n");

        List<DadosTemporada> temporadaList = new ArrayList<>();

        for(int i = 1 ; i <= dadosSerie.totalTemporadas() ; i++){
            json = consumirApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = converterdados.obterDados(json, DadosTemporada.class);
            temporadaList.add(dadosTemporada);
        }

        temporadaList.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }

}
