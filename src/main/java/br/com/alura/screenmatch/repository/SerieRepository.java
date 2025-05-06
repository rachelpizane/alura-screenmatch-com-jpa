package br.com.alura.screenmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.screenmatch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    // JpaRepository já possui métodos prontos para CRUD, não é necessário implementar nada aqui.
    // Você pode adicionar métodos personalizados se necessário.
    // Exemplo: List<Serie> findByTitulo(String titulo);
    // Durante a execu
}
