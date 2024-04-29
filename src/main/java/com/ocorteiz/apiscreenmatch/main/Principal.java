package com.ocorteiz.apiscreenmatch.main;

import com.ocorteiz.apiscreenmatch.model.DadosEpisodio;
import com.ocorteiz.apiscreenmatch.model.DadosSerie;
import com.ocorteiz.apiscreenmatch.model.DadosTemporada;
import com.ocorteiz.apiscreenmatch.model.Episodio;
import com.ocorteiz.apiscreenmatch.service.ConsumirApi;
import com.ocorteiz.apiscreenmatch.service.Converterdados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=ba089616";
    private ConsumirApi consumirApi = new ConsumirApi();
    private Converterdados converterdados = new Converterdados();

    public void exibirMenu() {
        System.out.println("DIGITE O NOME DA SERIE: ");
        var nomeSerie = leitura.nextLine();

        var json = consumirApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = converterdados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie + "\n");

        List<DadosTemporada> dadosTemporadaList = new ArrayList<>();

        // BUSCAND TODOS EPISODIOS

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            json = consumirApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = converterdados.obterDados(json, DadosTemporada.class);
            dadosTemporadaList.add(dadosTemporada);
        }

//        dadosTemporadaList.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        // CRIANDO STREAM COM dadosEpisodie

        List<DadosEpisodio> dadosEpisodioList = dadosTemporadaList.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        // CRIANDO STREAM COM 5 MELHORES EPISODIOS

        System.out.println("\nTOP 10 EPISODIOS:");
        dadosEpisodioList.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5);

        // CRIANDO STREAM COM Episodios E EXIBINDO 10 MELHORES

        List<Episodio> episodioList = dadosTemporadaList.stream()
                .flatMap(temporada -> temporada.episodios().stream()
                        .map(dadosEpisodio -> new Episodio(temporada.numero(), dadosEpisodio))
                )
                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                .limit(10)
                .collect(Collectors.toList());

        episodioList.forEach(System.out::println);

        // BUSCANDO EPISODIOS POR ANO

        System.out.println("\nDigite um ano de busca:");
        var ano = leitura.nextInt();
        leitura.nextLine();
        LocalDate buscaAno = LocalDate.of(ano, 1, 1);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodioList.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(buscaAno))
                .forEach(e -> System.out.println(
                        "Episodio: " + e.getTitulo() +
                        ", Temporada: " + e.getTemporada() +
                                ", Date de Lançamento: " + e.getDataLancamento().format(df)
                ));

        //  BUSCANDO EPISODIOS POR TEXTO

        System.out.println("\nDigite um nome de episodio para buscar: ");
        var buscaTexto = leitura.nextLine();

        Optional<Episodio> resultadoBuscatexto = episodioList.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(buscaTexto.toUpperCase()))
                .findFirst();
        if (resultadoBuscatexto.isPresent()) {
            System.out.println("Episodio encontrado: ");
            System.out.println(resultadoBuscatexto.get().getTitulo());
        } else {
            System.out.println("Episodio não encontrado");
        }
    }

}
