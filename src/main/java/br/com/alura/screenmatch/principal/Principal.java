package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<DadosSerie> series = new ArrayList<>();

    public void exibeMenu() {
        String opcao = "";
        while (!opcao.equals("0")) {
            String menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas

                    0 - Sair
                    """;
            String mensagemInput = "Digite a opção desejada: ";
            
            System.out.println(menu);
            System.out.print(mensagemInput);
            opcao = leitura.nextLine();

            switch (opcao) {
                case "1":
                    buscarSerieWeb();
                    break;
                case "2":
                    buscarEpisodioPorSerie();
                    break;
                case "3":
                    listarSeriesBuscadas();
                    break;
                case "0":
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
            System.out.println("--------------------------------");
        }
    }

    private void listarSeriesBuscadas() {
        if(series.isEmpty()) {
            System.out.println("(Nenhuma série foi buscada)");
            return;
        }

        series.stream()
            .map(serie -> new Serie(serie))
            .sorted(Comparator.comparing(serie -> serie.getGenero().toString()))
            .forEach(System.out::println); // Que lindoooo
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        series.add(dados); // Quando chamamos apenas a variavel em um System.out.println, o Java chama o toString() automaticamente.
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }
}