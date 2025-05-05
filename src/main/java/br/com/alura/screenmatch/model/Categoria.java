package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    COMEDIA("Comedy"),
    CRIME("Crime"),
    ADULTO("Adult");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
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
}
