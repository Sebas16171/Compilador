package codigo;

public class Token {

    public enum Tipos {
        NUMERO("[0-9]+"), OPERADOR_BINARIO("[*|/|+|-|=]"), FINAL("end"), RESERVADAS("[mov]"), VAR("al|bl|cl"),
        COMENTARIO("[;-]"), ETIQUETA("[-:]");

        public final String patron;

        Tipos(String s) {
            this.patron = s;
        }
    }

    private Tipos tipo;
    private String valor;
    private boolean processed;

    public Tipos getTipo() {
        return tipo;
    }

    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }

    public void setProcesed(Boolean processed) {
        this.processed = processed;
    }

    public boolean getProcessed() {
        return processed;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}