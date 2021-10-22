package model;

public class Matriz {

    String documento;
    String coluna;
    Integer valor;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Matriz(String documento, String coluna, Integer valor) {
        this.documento = documento;
        this.coluna = coluna;
        this.valor = valor;
    }


    public String getColuna() {
        return coluna;
    }

    public void setColuna(String coluna) {
        this.coluna = coluna;
    }

}
