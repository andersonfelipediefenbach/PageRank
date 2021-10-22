package model;

public class Matriz {

    String documento;
    String linha;
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

    public Matriz(String documento,String linha, Integer valor) {
        this.documento = documento;
        this.linha = linha;
        this.valor = valor;
    }



    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

}
