package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<DadosSerie> series = new ArrayList<>();
    private SerieRepository serieRepository;
    private List<Serie> seriesBD = new ArrayList<>();

    public Principal(SerieRepository repository) {
        this.serieRepository = repository;
    }

    public void exibeMenu() {
        leitura = new Scanner(System.in);
        String opcao = "";
        while (!opcao.equals("0")) {
            String menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por título
                    5-  Buscar séries por ator
                    6 - Buscar top 5 séries
                    7 - Buscar séries por categoria
                    8 - Buscar séries por total de temporadas e avaliação
                    9 - Buscar episodio por trecho

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
                case "4":
                    buscarSeriePorTitulo();
                    break;
                case "5":
                    buscarSeriesPorAtor();
                    break;
                case "6":
                    buscarTop5Series();
                    break;
                case "7":
                    buscarSeriesPorCategoria();
                    break;
                case "8":
                    buscarSeriesPorTotalTemporadasEAvaliacao();
                    break;
                case "9":
                    buscarEpisodioPorTrecho();
                    break;
                case "10":
                case "0":
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
            System.out.println("--------------------------------");
        }
    }

    private void buscarEpisodioPorTrecho() {
        System.out.println("Digite o nome do episodio: ");
        String nomeEpisodio = leitura.nextLine();
        List<Episodio> episodiosEncontrados = serieRepository.episodiosPorTrecho(nomeEpisodio);
        if (!episodiosEncontrados.isEmpty()) {
            episodiosEncontrados.forEach(episodio -> {
                System.out.println("Título: " + episodio.getTitulo() + " - Temporada: " + episodio.getTemporada() + " - Serie: " + episodio.getSerie().getTitulo());
            });
        } else {
            System.out.println("Nenhum episódio encontrado com o trecho informado.");
        }
    }

    private void buscarSeriesPorTotalTemporadasEAvaliacao() {
        System.out.println("Digite o total máximo de temporadas: ");
        int totalTemporadas = leitura.nextInt();
        System.out.println("Digite a avaliação mínima: ");
        double avaliacao = leitura.nextDouble();

        List<Serie> seriesPorTotalTemporadasEAvaliacao = serieRepository.seriesPorTemporadaEAvaliacao(totalTemporadas, avaliacao);

        if (!seriesPorTotalTemporadasEAvaliacao.isEmpty()) {
            System.out.println("Séries encontradas:");
            seriesPorTotalTemporadasEAvaliacao.forEach(serie -> {
                System.out.println("Título: " + serie.getTitulo() + " - Avaliação: " + serie.getAvaliacao());
            });
        } else {
            System.out.println("Nenhuma série encontrada com os critérios informados.");
        }
    }

    private void buscarSeriesPorCategoria() {
        System.out.print("Digite a categoria desejada: ");
        String categoria = leitura.nextLine();
        Categoria categoriaEnum = Categoria.fromPortugues(categoria);

        List<Serie> seriesPorCategoria = serieRepository.findAllByGenero(categoriaEnum);
        seriesPorCategoria.forEach(serie -> {
            System.out.println("Título: " + serie.getTitulo());
        });
    }

    private void buscarTop5Series() {
        List<Serie> top5Series= serieRepository.findTop5ByOrderByAvaliacaoDesc();

        top5Series.forEach(serie -> {
            System.out.println("Titulo: " + serie.getTitulo() + " - Avaliação: " + serie.getAvaliacao());
        });
    }

    private void buscarSeriesPorAtor() {
        System.out.print("Digite o nome do ator para busca: ");
        String nomeAtor = leitura.nextLine();

        System.out.print("Digite avaliacao mínima: ");
        Double avaliacaoSerie = leitura.nextDouble();

        List<Serie> seriesPorAtor = serieRepository.findAllByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacaoSerie );
        System.out.println("Seriem em que o ator " + nomeAtor + " atuou:");
        if (seriesPorAtor.isEmpty()) {
            System.out.println("Nenhuma série encontrada");
        } else {
            seriesPorAtor.forEach(serie -> {
                System.out.println("Titulo: " + serie.getTitulo());
                System.out.println("Avaliação: " + serie.getAvaliacao());
            });
        }
    }

    private void listarSeriesBuscadas() {
        // List<Serie> seriesBD = serieRepository.findAll(); // Busca todas as séries do
        // banco de dados
        seriesBD = serieRepository.findAllByOrderByGenero(); // Busca todas as séries do banco de dados ordenadas por
                                                             // gênero
        if (seriesBD.isEmpty()) {
            System.out.println("(Nenhuma série foi buscada)");
            return;
        }

        seriesBD.stream().forEach(serie -> {
            System.out.println(serie);
        });

        // VERSAO ANTERIOR, SEM O USO DO BANCO DE DADOS
        // if(series.isEmpty()) {
        // System.out.println("(Nenhuma série foi buscada)");
        // return;
        // }

        // series.stream()
        // .map(serie -> new Serie(serie))
        // .sorted(Comparator.comparing(serie -> serie.getGenero().toString()))
        // .forEach(System.out::println); // Que lindoooo
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        serieRepository.save(serie); // Salva a série no banco de dados
        // series.add(dados);
        System.out.println(dados); // Quando chamamos apenas a variavel em um System.out.println, o Java chama o
                                   // toString() automaticamente.
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.print("Escolha uma serie pelo nome: ");
        String nomeSerie = leitura.nextLine();

        Optional<Serie> serieSelecionada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        // Optional<Serie> serieSelecionada = seriesBD.stream()
        //         .filter(serie -> serie.getTitulo().equalsIgnoreCase(nomeSerie))
        //         .findFirst();

        if (serieSelecionada.isPresent()) {
            Serie serieEncontrada = serieSelecionada.get();

            List<DadosTemporada> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo
                        .obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }

            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas
                .stream()
                .flatMap(d -> d.episodios().stream()
                    .map(e -> new Episodio(d.numero(), e)))
                .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada); // Salva a série atualizada
        } else {
            System.out.println("Série não encontrada");
        }
    }

    private void buscarSeriePorTitulo() {
        System.out.print("Escolha uma serie pelo nome: ");
        String nomeSerie = leitura.nextLine();
        Optional<Serie> serieBuscada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);
        if (serieBuscada.isPresent()) {
            System.out.println("Dados da Serie: " + serieBuscada.get());
        } else {
            System.out.println("Série não encontrada");
        }
    }
}