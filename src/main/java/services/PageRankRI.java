package services;

import model.Matriz;

import java.util.*;
import java.util.stream.Collectors;

public class PageRankRI {
    private static Map<String, Double> calculado = new LinkedHashMap<String, Double>();
    private static Map<String, Matriz> mat = new LinkedHashMap<String, Matriz>();
    private static Map<Integer, String> nodos = new LinkedHashMap<Integer, String>();

    //Parâmetro de teleport e qtde iteração
    private static double teleport = 0.1;
    private static int iterationStep = 1;

    public static void calcPageRank() {
        int interation = 1;
        while (interation <= iterationStep) {

            System.out.println("\nDocumento - Total Iteração " + interation);

            //Número de interações do usuário
            interation++;
            for (int i = 0; i < nodos.size(); i++) {
                String finalI = i + "";
                double totalOcorrencia = 0;

                //Coleta as incidencias o nodo
                String nomeNodo = nodos.entrySet().stream().filter(x -> x.getKey() == Integer.parseInt(finalI)).map(x -> x.getValue()).findFirst().orElse("");
                List<String> collect = mat.entrySet().stream()
                        .filter(
                                x -> !x.getValue().getDocumento().equals(nomeNodo)// x.getValue().getDocumento() != finalI
                                        && x.getValue().getColuna().equals(nomeNodo)//x.getValue().getLinha() == finalI
                                        && x.getValue().getValor() == 1
                        )
                        .map(x -> x.getValue().getDocumento()).collect(Collectors.toList());

                //Busca para onde apontam essa incidências, se o valor já foi calculado e a quantidade de itens que aponta
                for (int j = 0; j < collect.size(); j++) {
                    int finalJ = j;
                    double ocorrencia = 0;
                    Long totallinks;

                    //Procura suas adjacencias e o total de referencias para outros links
                    try {
                        ocorrencia = calculado.entrySet().stream().filter(x -> x.getKey().equals(collect.get(finalJ) + "")).
                                mapToDouble(m -> m.getValue()).
                                findFirst().orElseThrow(Exception::new);
                        totallinks = mat.entrySet().stream().filter(
                                x -> x.getValue().getDocumento().equals(collect.get(finalJ))
                                        && x.getValue().getValor() == 1
                        ).count();
                        ocorrencia /= totallinks;
                    } catch (Exception exception) {
                        ocorrencia = 0;
                    }
                    //Se não encontrar, calcula do zerp
                    if (ocorrencia == 0) {
                        ocorrencia = 1;
                        ocorrencia = (ocorrencia / (nodos.size())) / mat.entrySet().stream().filter(
                                x -> x.getValue().getDocumento().equals(collect.get(finalJ)) &&
                                        x.getValue().getValor() == 1).count();

                    }
                    totalOcorrencia = totalOcorrencia + ocorrencia;
                }
                double total = (teleport / nodos.size()) + ((1 - teleport) * (totalOcorrencia));
                double epsilon = 0;
                try {
                    epsilon = (calculado.entrySet().stream().filter(x -> x.getKey().equals(nomeNodo))
                            .mapToDouble(v -> v.getValue()).max().orElseThrow(NoSuchElementException::new));
                } catch (Exception exception) {
                    epsilon = 0;
                }

                //Para calcular o epsilon, subtraio o valor atual menos o último calculado.
                total = total - epsilon;
                //Armazena valor calculado
                calculado.put(nomeNodo + "", total);
                //Exibi o valor de cada vertice em cada iteração

                System.out.println(i + "           " + total);
            }
        }
        imprime();
    }

    public static void imprime() {
        System.out.println("\n EX1:");
        System.out.println("____________________________________________________________________");
        for (Map.Entry<String, Double> entry : calculado.entrySet()) {
            String coluna = "";
            for (Map.Entry<String, Matriz> entry2 : mat.entrySet()) {
                if (entry.getKey().equals(entry2.getValue().getDocumento())) {
                    coluna = coluna + " | " + entry2.getValue().getColuna() + ":" + entry2.getValue().getValor() + " | ";
                }
            }
            System.out.println("| Documento:" + entry.getKey() + "   " + coluna);
            System.out.println("____________________________________________________________________");
        }
        System.out.println("\n");
        System.out.println("Documento    Total ");
        for (Map.Entry<String, Double> entry : calculado.entrySet()) {
            String coluna = "";
            System.out.println(entry.getKey() + "            " + entry.getValue());

        }
    }

    public static void pageRank(Map<Integer, String> nod, Map<String, Matriz> matriz) {
        Scanner in = new Scanner(System.in);
        nodos = nod;
        mat = matriz;
        calcPageRank();
    }


}
