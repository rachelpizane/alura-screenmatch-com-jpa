package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action", "Acao"),
    ROMANCE("Romance", "Romance"),
    DRAMA("Drama", "Drama"),
    COMEDIA("Comedy", "Comédia"),
    CRIME("Crime", "Crime"),
    ADULTO("Adult", "Adulto"),;

    private String categoriaOmdb;
    private String categoriaPT;

    Categoria(String categoriaOmdb, String categoriaPT) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPT = categoriaPT;
    }

    public static Categoria fromString(String categoriaOmdbString) {
        // Se a categoriaOmdbString for "Action", retorna o enum ACAO
        // Se a categoriaOmdbString não for identificada, é lançado um IllegalArgumentException
        for (Categoria categoriaEnum : Categoria.values()) {
            if (categoriaEnum.categoriaOmdb.equalsIgnoreCase(categoriaOmdbString)) { //.equalsIgnoreCase ignora maiúsculas e minúsculas
                return categoriaEnum;
            }
        }
        throw new IllegalArgumentException("Categoria não encontrada: " + categoriaOmdbString);
    }

    public static Categoria fromPortugues(String categoriaPTString) {
        for (Categoria categoriaEnum : Categoria.values()) {
            if (categoriaEnum.categoriaPT.equalsIgnoreCase(categoriaPTString)) {
                return categoriaEnum;
            }
        }

        throw new IllegalArgumentException("Categoria não encontrada: " + categoriaPTString);
    }
}
