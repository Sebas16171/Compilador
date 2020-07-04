package codigo;

public class Token {

    public enum Tipos {

        /*
████████╗██╗██████╗  ██████╗ ███████╗    ██████╗ ███████╗    ████████╗ ██████╗ ██╗  ██╗███████╗███╗   ██╗███████╗
╚══██╔══╝██║██╔══██╗██╔═══██╗██╔════╝    ██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██║ ██╔╝██╔════╝████╗  ██║██╔════╝
   ██║   ██║██████╔╝██║   ██║███████╗    ██║  ██║█████╗         ██║   ██║   ██║█████╔╝ █████╗  ██╔██╗ ██║███████╗
   ██║   ██║██╔═══╝ ██║   ██║╚════██║    ██║  ██║██╔══╝         ██║   ██║   ██║██╔═██╗ ██╔══╝  ██║╚██╗██║╚════██║
   ██║   ██║██║     ╚██████╔╝███████║    ██████╔╝███████╗       ██║   ╚██████╔╝██║  ██╗███████╗██║ ╚████║███████║
   ╚═╝   ╚═╝╚═╝      ╚═════╝ ╚══════╝    ╚═════╝ ╚══════╝       ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚══════╝                                                                                                               
        /
        NUMERO("[0-9]+"), OPERADOR_BINARIO("[*|/|+|-|=]"), COMANDO("[mov]"), REGISTRO("al|bl|cl"),
        COMENTARIO("[;-]"), ETIQUETA("[-:]");

        */

        Comando_Registro_Registro("add|sub|mul|div|mod|cmp|and|or|xor"),
        Comando_Registro_Valor("add|sub|div|mul|cmp|mov"),
        Comando("halt|nop|pushf|popf|ret|iret|clo|sti|cli"),
        Comando_valor("in|out|call|int|db"),    // valor de 00 a 08
        Comando_registro("inc|dec|org|shl|shr|rol|ror|push|pop|not"),
        Comando_etiqueta("jmp|jz|jnz|js|jns|jo|jno"),
        COMENTARIO("[;-]"),
        ETIQUETA("[-:]"),
        FINAL("end"),
        SALTO("\n");


        public final String patron;

        Tipos(String s) {
            this.patron = s;
        }
    }

    /*
    ██████╗ ███████╗███████╗████████╗██████╗ ██╗ ██████╗ ██████╗██╗ ██████╗ ███╗   ██╗███████╗███████╗
    ██╔══██╗██╔════╝██╔════╝╚══██╔══╝██╔══██╗██║██╔════╝██╔════╝██║██╔═══██╗████╗  ██║██╔════╝██╔════╝
    ██████╔╝█████╗  ███████╗   ██║   ██████╔╝██║██║     ██║     ██║██║   ██║██╔██╗ ██║█████╗  ███████╗
    ██╔══██╗██╔══╝  ╚════██║   ██║   ██╔══██╗██║██║     ██║     ██║██║   ██║██║╚██╗██║██╔══╝  ╚════██║
    ██║  ██║███████╗███████║   ██║   ██║  ██║██║╚██████╗╚██████╗██║╚██████╔╝██║ ╚████║███████╗███████║
    ╚═╝  ╚═╝╚══════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═╝ ╚═════╝ ╚═════╝╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚══════╝
                
        Comando [valor], registro		mov
        Comando  registro,[valor]		mov,cmp
        Comando  registro,[registro]		mov
        Comando  [registro],registro		mov
    */

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