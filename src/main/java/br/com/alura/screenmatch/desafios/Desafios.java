package br.com.alura.screenmatch.desafios;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Desafios {
    public static void main(String[] args) {
        // Desafio 1
        List<String> input = Arrays.asList("10", "abc", "20", "30x");
        List<Integer> inputInt = input.stream()
                .map(item -> {
                    try {
                        return Integer.parseInt(item);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(item -> item != null)
                .collect((Collectors.toList()));

        System.out.println(inputInt);
        // Desafio 2

        System.out.println(processaNumero(Optional.of(5))); // Saída: Optional[25]
        System.out.println(processaNumero(Optional.of(-3))); // Saída: Optional.empty
        System.out.println(processaNumero(Optional.empty())); // Saída: Optional.empty

        // Desafio 4
        System.out.println(ehPalindromo("sus")); // Saída: true
        System.out.println(ehPalindromo("Java")); // Saída: false
    }

    public static boolean ehPalindromo(String frase) {
        String fraseSemEspaco = frase.replaceAll(" ", "").toLowerCase();
        String fraseInvertida = new StringBuilder(fraseSemEspaco).reverse().toString(); // O que faz o StringBuilder? Ele inverte a string.
        return fraseSemEspaco.equalsIgnoreCase(fraseInvertida);
    }

    public static Optional<Integer> processaNumero(Optional<Integer> numero) {
        return numero.filter(n -> n > 0).map(n -> n * n);
    }
}
