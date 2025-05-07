package br.com.alura.screenmatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.screenmatch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    // JpaRepository já possui métodos prontos para CRUD, não é necessário implementar nada aqui.
    // Você pode adicionar métodos personalizados se necessário.
    // Exemplo: List<Serie> findByTitulo(String titulo);
    List<Serie> findAllByOrderByGenero(); // Método para listar todas as séries ordenadas por gênero.
   
    Optional<Serie> findByTituloContainingIgnoreCase(String titulo);
    
    List<Serie> findAllByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String ator, double avaliacao); // Método para buscar séries por ator e avaliação maior ou igual a um valor específico.
    
    List<Serie> findTop5ByOrderByAvaliacaoDesc();
}
