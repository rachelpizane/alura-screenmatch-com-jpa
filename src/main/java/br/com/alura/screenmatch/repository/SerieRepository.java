package br.com.alura.screenmatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    // JpaRepository já possui métodos prontos para CRUD, não é necessário implementar nada aqui.
    // Você pode adicionar métodos personalizados se necessário.
    // Exemplo: List<Serie> findByTitulo(String titulo);
    List<Serie> findAllByOrderByGenero(); // Método para listar todas as séries ordenadas por gênero.
   
    Optional<Serie> findByTituloContainingIgnoreCase(String titulo);
    
    List<Serie> findAllByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String ator, double avaliacao); // Método para buscar séries por ator e avaliação maior ou igual a um valor específico.
    
    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findAllByGenero(Categoria genero); // Método para buscar séries por gênero.

    List<Serie> findAllByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporadas, double avaliacao); 

    @Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaEAvaliacao(int totalTemporadas, double avaliacao); 
    // Método para buscar séries por total de temporadas e avaliação maior ou igual a um valor específico.
    // O método seriesPorTemporadaEAvaliacao é uma consulta personalizada usando JPQL (Java Persistence Query Language).
    // A consulta JPQL é semelhante à SQL, mas opera em entidades e atributos em vez de tabelas e colunas.
    // A vantagem de usar JPQL é que ela é independente do banco de dados, o que torna o código mais portável.
}
