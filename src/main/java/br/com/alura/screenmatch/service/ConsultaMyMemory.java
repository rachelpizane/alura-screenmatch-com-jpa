package br.com.alura.screenmatch.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import br.com.alura.screenmatch.model.DadosTraducao;

public class ConsultaMyMemory {
    public static String obterTraducao(String texto) {
        ConsumoApi consumo = new ConsumoApi();
        ConverteDados conversor = new ConverteDados();

        String textoFormatado = URLEncoder.encode(texto, StandardCharsets.UTF_8);
        String langpair = URLEncoder.encode("en|pt-br", StandardCharsets.UTF_8);

        String url = "https://api.mymemory.translated.net/get?q=" + textoFormatado + "&langpair=" + langpair;

        String json = consumo.obterDados(url);

        DadosTraducao traducao = conversor.obterDados(json, DadosTraducao.class);
  
        return traducao.dadosResposta().textoTraduzido();
    }
}
