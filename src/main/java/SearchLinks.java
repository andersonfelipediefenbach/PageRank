import java.io.*;
import java.util.*;


public class SearchLinks {

    private static Map<String, Matriz> matriz = new LinkedHashMap<String, Matriz>();
    private static Map<Integer,String> nodos = new LinkedHashMap<Integer,String>();
    private static int nodo;

    /**
     *
     */
    public static void visualizarArquivos() throws IOException {
        String diretorio = "html";
        File file = new File(diretorio);
        File afile[] = file.listFiles();
        int i = 0;
        int k = 0;
        int tamanho=afile.length;
        //zera posições
        for (int j = tamanho; i < j; i++) {
            File arquivos = afile[i];
            k=0;
            for (int t = tamanho; k < t; k++) {
                File arquivosColuna = afile[k];
             //   System.out.println(arquivos.getName().toLowerCase().substring(0,1) + " dentro " +  arquivosColuna.getName().toLowerCase().substring(0,1));
                 matriz.put(arquivos.getName().toLowerCase().substring(0,1) + arquivosColuna.getName().toLowerCase().substring(0,1),
                         new Matriz(arquivos.getName().toLowerCase().substring(0,1), arquivosColuna.getName().toLowerCase().substring(0,1), 0));
            }
            nodos.put(i,arquivos.getName().toLowerCase().substring(0,1));

        }
        i = 0;
        for (int j = afile.length; i < j; i++) {
            File arquivos = afile[i];
          //  System.out.println(arquivos.getName() + " tamanho " + afile.length);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(arquivos )/*, "ISO-8859-1"*/));
            String input;

            while ((input = reader.readLine()) != null) {

                StringTokenizer frase = new StringTokenizer(input);

                // Processamento a frase de entrada
                while (frase.hasMoreTokens()) {
                    String palavra = frase.nextToken().toLowerCase();
                    if (palavra.contains("href")) {
                        String col = palavra.substring(palavra.lastIndexOf("=") + 2, palavra.lastIndexOf(">") - 6);
                        matriz.put(arquivos.getName().toLowerCase().substring(0,1) + col, new Matriz(arquivos.getName().toLowerCase().substring(0,1), col, 1));
                        //System.out.println(palavra.lastIndexOf("="));
                        //System.out.println(palavra);
                        //System.out.println(palavra.lastIndexOf(">"));
                        //System.out.println("doc " + i);
                      //  System.out.println(palavra.substring(palavra.lastIndexOf("=") + 2, palavra.lastIndexOf(">") - 6));
                    }
                }
            }
        }
        //System.out.println(matriz);

        PageRankRI.page(nodos,matriz);
    }

    public static void main(String[] args) {

        //System.out.println(removerRepetidas("teste repe teste http://google.com.br "));
        ex2("teste repe teste href=http://google.com.br teassfa p href=http://bing.com.br");
        try {
            visualizarArquivos();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public static void ex2(String s) {
      //  System.out.println(s.matches("\\w.*"));
        String[] splitString = (s.split("\\s+"));
       // System.out.println(splitString.length);// should be 14
        for (String string : splitString) {
            if (string.contains("href")){}
            //    System.out.println(string);
        }
        // replace all whitespace with tabs
        //System.out.println(s.replaceAll("\\s+", "\t"));
    }
}


