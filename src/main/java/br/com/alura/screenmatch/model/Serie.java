package br.com.alura.screenmatch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import br.com.alura.screenmatch.service.ConsultaMyMemory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING) // EnumType.STRING para armazenar o nome do enum no banco de dados.
    private Categoria genero;

    @Column(name = "qntd_temporadas")
    private Integer totalTemporadas;

    private Double avaliacao;

    private String atores;

    private String imagem;

    private String sinopse;

    //@Transient // Transient para não persistir no banco de dados.
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();
    // Uma serie pode ter vários episódios.
    // mappedBy = "serie" indica que a entidade episodio é proprietária do relacionamento e que a chave estrangeira está na tabela episodio.

    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.genero = Categoria.fromString(dadosSerie.generos().split(",")[0]);
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0.0); // OptionalDouble tenta converter a string para double, se não conseguir retorna 0.0. Uma forma de evitar o try-catch.
        this.atores = dadosSerie.atores();
        this.imagem = dadosSerie.imagem();
        this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse());
    }

    public Serie() {} // Construtor padrão necessário para o JPA poder instanciar a classe.

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(episodio -> episodio.setSerie(this)); 
        // Para cada episódio, setamos a série que ele pertence.
        // @OneToMany define que tem um relecionamento e que Episodio tem uma coluna serie_id que referencia a série, mas ela informa automaticamente o id da série em cada registro de episodio.
        this.episodios = episodios;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero=" + genero +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", atores='" + atores + '\'' +
                ", imagem='" + imagem + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", episodios=" + episodios +
                '}';
    }
}
