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
        COMENTARIO(";-"),
        ETIQUETA("-:"),
        FINAL("end"),
        SALTO("\n"),
        HEXADECIMAL(Hexadecimal());


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

    private static String Hexadecimal() {
        String temp = "";
        String T1 = "", T2 = "";
        for (int i = 0; i < 16; i++) {
            switch (i) {
                case 0:
                    T1 = "0";
                    break;
                case 1:
                    T1 = "1";
                    break;
                case 2:
                    T1 = "2";
                    break;
                case 3:
                    T1 = "3";
                    break;
                case 4:
                    T1 = "4";
                    break;
                case 5:
                    T1 = "5";
                    break;
                case 6:
                    T1 = "6";
                    break;
                case 7:
                    T1 = "7";
                    break;
                case 8:
                    T1 = "8";
                    break;
                case 9:
                    T1 = "9";
                    break;
                case 10:
                    T1 = "A";
                    break;
                case 11:
                    T1 = "B";
                    break;
                case 12:
                    T1 = "C";
                    break;
                case 13:
                    T1 = "D";
                    break;
                case 14:
                    T1 = "E";
                    break;
                case 15:
                    T1 = "F";
                    break;
            }
            for (int ii = 0; ii < 16; ii++) {
                switch (ii) {
                    case 0:
                        T2 = "0";
                        break;
                    case 1:
                        T2 = "1";
                        break;
                    case 2:
                        T2 = "2";
                        break;
                    case 3:
                        T2 = "3";
                        break;
                    case 4:
                        T2 = "4";
                        break;
                    case 5:
                        T2 = "5";
                        break;
                    case 6:
                        T2 = "6";
                        break;
                    case 7:
                        T2 = "7";
                        break;
                    case 8:
                        T2 = "8";
                        break;
                    case 9:
                        T2 = "9";
                        break;
                    case 10:
                        T2 = "A";
                        break;
                    case 11:
                        T2 = "B";
                        break;
                    case 12:
                        T2 = "C";
                        break;
                    case 13:
                        T2 = "D";
                        break;
                    case 14:
                        T2 = "E";
                        break;
                    case 15:
                        T2 = "F";
                        break;
                }
                String str;
                str = T1;
                str += T2;
                str += "|";
                temp += str;
            }
        }
        temp = temp.substring(0, temp.length() - 1);
        System.out.println(temp);
        return temp;
    }

}