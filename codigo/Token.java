package codigo;

public class Token {

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

    private Tipos tipo;
    private String valor;
    private boolean processed;

    public enum Tipos {
        NUMERO("[0-9]+"), OPERADOR_BINARIO("[*|/|+|-]"), FINAL("[end]"), DEF("[mov]"), COMENTARIO("[;]");
        //, RESERVADA("[;|:|=|/|+|-|*|.|.186|.286|.286c|and|arg|assume|byte]");

        public final String patron;

        Tipos(String s) {
            this.patron = s;
        }
    }

}