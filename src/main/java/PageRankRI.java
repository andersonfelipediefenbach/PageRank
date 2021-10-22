import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class PageRankRI {
    private static int nodes;
    private static Map<String, Double> calculado = new LinkedHashMap<String, Double>();
    private static Map<String, Matriz> mat = new LinkedHashMap<String, Matriz>();
    private static Map<Integer, String> nodos = new LinkedHashMap<Integer, String>();
    //Parâmetro de teleport
    private static double teleport = 0.1;
    private static int iteration_step = 1;

    public static void calc() {
        int interation = 1;
        while (interation <= iteration_step) {

            System.out.println("\nDocumento - Total Iteração "+interation);

            //Número de interações do usuário
            interation++;
            for (int i = 0; i < nodes; i++) {
                String finalI = i + "";
                //String finalij = i+"";
                //Coleta qual incidencia
                String nomeNodo = nodos.entrySet().stream().filter(x -> x.getKey() == Integer.parseInt(finalI)).map(x -> x.getValue()).findFirst().toString();
                String nomeNodo2 = nomeNodo.replace("Optional", "").replace("[", "").replace("]", "");
                List<String> collect = mat.entrySet().stream()
                        .filter(
                                x -> !x.getValue().getDocumento().equals(nomeNodo2)// x.getValue().getDocumento() != finalI
                                        && x.getValue().getLinha().equals(nomeNodo2)//x.getValue().getLinha() == finalI
                                        && x.getValue().getValor() == 1
                        )
                        .map(x -> x.getValue().getDocumento()).collect(Collectors.toList());

                double totalOcorrencia = 0;

                //Busca para onde apontam essa incidências, se o valor já foi calculado e a quantidade de itens que aponta
                for (int j = 0; j < collect.size(); j++) {
                    int finalJ = j;
                    double ocorrencia = 0;
                    Long totallinks;
                    String nomeColuna = nodos.entrySet().stream().filter(x -> x.getKey() == (finalJ)).findFirst().toString();
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
                    if (ocorrencia == 0) {
                        ocorrencia = 1;
                        ocorrencia /= nodes;
                        ocorrencia /= mat.entrySet().stream().filter(
                                x -> x.getValue().getDocumento().equals(collect.get(finalJ)) &&
                                        x.getValue().getValor() == 1
                        ).count();
                    }
                    totalOcorrencia = totalOcorrencia + ocorrencia;
                }
                double total = (teleport / nodes) + ((1 - teleport) * (totalOcorrencia));
                double epsilon = 0;
                try {
                    epsilon = (calculado.entrySet().stream().filter(x -> x.getKey().equals(nomeNodo2))
                            .mapToDouble(v -> v.getValue()).max().orElseThrow(NoSuchElementException::new));
                    //calculado.entrySet().stream().filter(x -> x.getKey().equals(nomeNodo2)).mapToDouble(x -> x.getValue()).max();
                } catch (Exception exception) {
                    epsilon = 0;
                }

                total = total - epsilon;
                //Armazena valor calculado
                calculado.put(nomeNodo2 + "", total);
                //Exibi o valor de cada vertice em cada iteração

                System.out.println(i + "           " + total);
            }
        }
        imprime();
    }

    public static void imprime() {
        System.out.println("\n");
        System.out.println("____________________________________________________________________");
        for (Map.Entry<String, Double> entry : calculado.entrySet()) {
            String coluna = "";
            for (Map.Entry<String, Matriz> entry2 : mat.entrySet()) {
                if (entry.getKey().equals(entry2.getValue().getDocumento())) {
                    coluna = coluna + " | "+ entry2.getValue().getLinha() + ":" + entry2.getValue().getValor() + " | ";
                }
            }
            System.out.println("| Documento:" + entry.getKey() + "   " + coluna);
            System.out.println("____________________________________________________________________");
        }
        System.out.println("\n");
        System.out.println("Documento    Total ");
        for (Map.Entry<String, Double> entry : calculado.entrySet()) {
            String coluna = "";
            System.out.println(  entry.getKey() + "            " +entry.getValue());

        }
    }

    public static void page(Map<Integer, String> nod, Map<String, Matriz> matriz) {
        int i, j, cost;
        Scanner in = new Scanner(System.in);
        //System.out.println("Enter the Number of WebPages \n");
        nodes = nod.size();
        nodos = nod;
        mat = matriz;
        /*for (Map.Entry<String, Matriz> entry : mat.entrySet()) {
            System.out.println(entry.getKey() + " -> "
                    + entry.getValue().getDocumento() + " ->" +
                    entry.getValue().getLinha() + " ->" +
                    entry.getValue().getValor());
        }*/
        // System.out.println(mat.size());
        calc();
    }

    public static void main(String args[]) {
        int i, j, cost;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the Number of WebPages \n");
        nodes = in.nextInt();
        PageRankRI p = new PageRankRI();
        System.out.println("Enter the Adjacency Matrix with 1->PATH & 0->NO PATH Between two WebPages: \n");

        for (i = 0; i < nodes; i++) {
            List<Integer> list = new ArrayList<>();
            List col = new ArrayList();
            for (j = 0; j < nodes; j++) {
                int a = in.nextInt();
                String b = String.valueOf(a);
                for (String valor : b.split(" ")) {
                    list.add(Integer.valueOf(valor));
                    mat.put(i + "" + j, new Matriz(i + "", j + "", Integer.valueOf(valor)));
                }
                col.add(j);
            }
        }


        for (Map.Entry<String, Matriz> entry : mat.entrySet()) {
            System.out.println(entry.getKey() + " -> "
                    + entry.getValue().getDocumento() + " ->" +
                    entry.getValue().getLinha() + " ->" +
                    entry.getValue().getValor());
        }
        //  System.out.println(mat.size());
        calc();
    }

    /*
     public static void calc() {
        int i;

        for (i = 0; i < nodes; i++) {
            int finalI = i;
            Long e = mat.entrySet().stream().filter(
                    x -> x.getValue().getDocumento() != finalI
                            && x.getValue().getLinha() == finalI
                            && x.getValue().getValor() == 1
            ).count();

    Object[] incidencias = mat.entrySet().stream().filter(
            x -> x.getValue().getDocumento() != finalI
                    && x.getValue().getLinha() == finalI
                    && x.getValue().getValor() == 1
    ).toArray();
    List<Integer> collect = mat.entrySet().stream()
            .filter(
                    x -> x.getValue().getDocumento() != finalI
                            && x.getValue().getLinha() == finalI
                            && x.getValue().getValor() == 1
            )
            .map(x -> x.getValue().getDocumento()).collect(Collectors.toList());

    double totalOcorrencia = 0;
            for (int j = 0; j < collect.size(); j++) {
        int finalJ = j;
        double ocorrencia = 0;
        Long totallinks;

        try {
            ocorrencia = calculado.entrySet().stream().filter(x -> x.getKey() == collect.get(finalJ)).
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

        if (ocorrencia == 0) {
            ocorrencia = 1;
            ocorrencia /= nodes;
            ocorrencia /= mat.entrySet().stream().filter(
                    x -> x.getValue().getDocumento() == collect.get(finalJ) &&
                            x.getValue().getValor() == 1
            ).count();
        }
        totalOcorrencia = totalOcorrencia + ocorrencia;

    }

    double total = (teleport / nodes) + ((1 - teleport) * (totalOcorrencia));
            calculado.put(i, total);
    // System.out.println("total incidencias " + i + " " + e);

}
        System.out.println(calculado);
                }

                  */
}
