package services;

import model.Matriz;

import java.io.*;
import java.util.*;


public class SearchLinks {

    private static Map<String, Matriz> matriz = new LinkedHashMap<String, Matriz>();
    private static Map<Integer, String> nodos = new LinkedHashMap<Integer, String>();

    public static void findLinks() throws IOException {
        String diretorio = "html";
        File file = new File(diretorio);
        File afile[] = file.listFiles();
        int i = 0;
        int k = 0;
        int tamanho = afile.length;

        //zera posições
        for (int j = tamanho; i < j; i++) {
            File arquivos = afile[i];
            k = 0;
            for (int t = tamanho; k < t; k++) {
                File arquivosColuna = afile[k];
                matriz.put(arquivos.getName().toLowerCase().substring(0, 1) + arquivosColuna.getName().toLowerCase().substring(0, 1),
                        new Matriz(arquivos.getName().toLowerCase().substring(0, 1), arquivosColuna.getName().toLowerCase().substring(0, 1), 0));
            }
            nodos.put(i, arquivos.getName().toLowerCase().substring(0, 1));
        }
        i = 0;
        for (int j = afile.length; i < j; i++) {
            File arquivos = afile[i];

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(arquivos)/*, "ISO-8859-1"*/));
            String input;

            while ((input = reader.readLine()) != null) {
                StringTokenizer frase = new StringTokenizer(input);
                // Processamento a frase de entrada
                while (frase.hasMoreTokens()) {
                    String palavra = frase.nextToken().toLowerCase();
                    if (palavra.contains("href")) {
                        String col = palavra.substring(palavra.lastIndexOf("=") + 2, palavra.lastIndexOf(">") - 6);
                        matriz.put(arquivos.getName().toLowerCase().substring(0, 1) + col, new Matriz(arquivos.getName().toLowerCase().substring(0, 1), col, 1));
                    }
                }
            }
        }
        PageRankRI.pageRank(nodos, matriz);
    }
}


