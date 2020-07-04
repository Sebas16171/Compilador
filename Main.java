import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.filechooser.*;
import java.util.regex.*;

import codigo.Token;
import codigo.Token.*;

import java.io.*;

public class Main implements ActionListener, KeyListener {

    public static String ruta;

    public static ArrayList<String> etiquetas = new ArrayList<String>();
    public static ArrayList<Integer> index_etiquetas = new ArrayList<Integer>();
    public static ArrayList<Linea> Lineas = new ArrayList<Linea>();

    static JFrame mainframe;
    JMenuBar MenuBar;
    JMenu file, edit, help;
    JMenuItem cut, copy, paste, selectAll, mnew, open, save;
    static JTextArea code_area;

/*
 █████╗ ███╗   ██╗ █████╗ ██╗     ██╗███████╗██╗███████╗
██╔══██╗████╗  ██║██╔══██╗██║     ██║██╔════╝██║██╔════╝
███████║██╔██╗ ██║███████║██║     ██║███████╗██║███████╗
██╔══██║██║╚██╗██║██╔══██║██║     ██║╚════██║██║╚════██║
██║  ██║██║ ╚████║██║  ██║███████╗██║███████║██║███████║
╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝╚═╝╚══════╝
*/

    private static ArrayList<Token> lex(String input) {

        final ArrayList<Token> tokens = new ArrayList<Token>();
        etiquetas.clear();
        index_etiquetas.clear();
        Lineas.clear();
        int end_counter = 0;
        String Lineas[] = input.split("\\r?\\n"), comentario = "";
        int line_counter = 0;
        boolean matched = false, comentario_iniciado = false;
        //Tipos Ultimo = null;

        for (String Linea : Lineas){
            line_counter++;
            comentario = "";
            comentario_iniciado = false;

            final StringTokenizer st = new StringTokenizer(Linea);

            if (Linea.trim().endsWith(":")){
                Token tk = new Token();
                tk.setProcesed(Registrar_Etiqueta(Linea));
                tk.setTipo(Tipos.ETIQUETA);
                tk.setValor(Linea);
                if (Registrar_Etiqueta(Linea)) {
                    etiquetas.add(Linea);
                }
                tokens.add(tk);
            } else {
                while (st.hasMoreTokens()) {
                    String palabra = st.nextToken();
                    if (comentario_iniciado){
                        comentario += palabra + " ";
                        matched = true;
                    } else {
                        for (Tipos tokenTipo : Tipos.values()) {
                            Pattern patron = Pattern.compile(tokenTipo.patron);
                            Matcher matcher = patron.matcher(palabra.trim());
                            if (matcher.find()) {
                                if (tokenTipo == Tipos.COMENTARIO || end_counter > 0) {
                                    matched = true;
                                    comentario_iniciado = true;
                                    comentario += palabra + " ";
                                } else if (tokenTipo == Tipos.ETIQUETA){}else{
                                    Token tk = new Token();
                                    tk.setProcesed(true);
                                    tk.setTipo(tokenTipo);
                                    tk.setValor(palabra);
                                    if (tokenTipo == Tipos.ETIQUETA) {
                                        if (Registrar_Etiqueta(palabra)) {
                                            matched = false;
                                            break;
                                        } else{
                                            tokens.remove(tokens.size() - 1);
                                            etiquetas.add(palabra);
                                            index_etiquetas.add(line_counter);                                           
                                        }
                                    }
                                    tokens.add(tk);
                                    matched = true;
                                    if (tk.getTipo() == Tipos.FINAL){
                                        end_counter++;
                                    }
                                }
                            }
                        }
                    }
                    
                    if (!matched) {
                        System.out.println("Se encontró un token invalido: " + palabra);
                    }
    
                }
            }


            if (comentario_iniciado && comentario.startsWith(";")) {
                Token tk = new Token();
                tk.setProcesed(false);
                tk.setTipo(Tipos.COMENTARIO);
                tk.setValor(comentario);
                tokens.add(tk);
                comentario_iniciado = false;
                comentario = "";
            }
            Token tk = new Token();
            tk.setProcesed(false);
            tk.setTipo(Tipos.SALTO);
            tk.setValor("\\n");
            tokens.add(tk);
            
            
        }

        switch (end_counter) {
            case 0:
                JOptionPane.showMessageDialog(null, "No se encontró un end.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case 1:
                if (matched) {
                    return tokens;        
                    
                }
            default:
                JOptionPane.showMessageDialog(null, "Solo debe haber un end.\n" + end_counter + " encontrados.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }

        return null;

    }

    private static boolean Registrar_Etiqueta(String etiqueta){
        if (etiquetas.size() > 0) {
            
            for(String checando : etiquetas){
                System.out.println("Comparando " + checando + " con " + etiqueta);
                if (checando.equals(etiqueta)) {
                    System.out.println("Encontrado");
                    return false;
                }
            }
        }
        return true;
    }

    public static void save_file() throws Exception {
        System.out.println(code_area.getText());
        File asmFile = new File(ruta);
        FileWriter writer = new FileWriter(asmFile);
        PrintWriter printer = new PrintWriter(writer);
        printer.println(code_area.getText());
        writer.close();
    }

    public static void select_file() {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Assembly code files", "asm");

        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            ruta = chooser.getSelectedFile().getAbsolutePath();
            try {
                File asmFile = new File(ruta);
                Scanner sc = new Scanner(asmFile);
                while (sc.hasNextLine()) {
                    String data = sc.nextLine();
                    data = data.trim();
                    code_area.append(data + '\n');
                }
                sc.close();

                mainframe.setTitle("Pirate Studio Code - " + asmFile.getName());

            } catch (Exception e) {
                System.out.print("Se murio");
            }
        }
    }
/*
██╗   ██╗███████╗███╗   ██╗████████╗ █████╗ ███╗   ██╗ █████╗ 
██║   ██║██╔════╝████╗  ██║╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
██║   ██║█████╗  ██╔██╗ ██║   ██║   ███████║██╔██╗ ██║███████║
╚██╗ ██╔╝██╔══╝  ██║╚██╗██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
 ╚████╔╝ ███████╗██║ ╚████║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═══╝  ╚══════╝╚═╝  ╚═══╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
                                                              
*/
    Main() {

        Color Window_BG = new Color(36, 36, 36);
        Color Code_BG = new Color(26, 26, 26);
        Color Accent = new Color(18, 18, 18);
        Color ForeColor1 = new Color(206, 206, 206);

        mainframe = new JFrame();
        mainframe.getContentPane().setBackground(Window_BG);

        cut = new JMenuItem("Cut");
        cut.setForeground(ForeColor1);
        cut.setBackground(Accent);
        cut.addActionListener(this);

        copy = new JMenuItem("Copy");
        copy.setForeground(ForeColor1);
        copy.setBackground(Accent);
        copy.addActionListener(this);

        paste = new JMenuItem("Paste");
        paste.setForeground(ForeColor1);
        paste.setBackground(Accent);
        paste.addActionListener(this);

        selectAll = new JMenuItem("SelectAll");
        selectAll.setForeground(ForeColor1);
        selectAll.setBackground(Accent);
        selectAll.addActionListener(this);

        // -----------------------------------//

        mnew = new JMenuItem("New File");
        mnew.setForeground(ForeColor1);
        mnew.setBackground(Accent);
        mnew.addActionListener(this);

        open = new JMenuItem("Open");
        open.setForeground(ForeColor1);
        open.setBackground(Accent);
        open.addActionListener(this);

        save = new JMenuItem("Save");
        save.setForeground(ForeColor1);
        save.setBackground(Accent);
        save.addActionListener(this);

        MenuBar = new JMenuBar();
        MenuBar.setBackground(Accent);
        MenuBar.setForeground(ForeColor1);

        file = new JMenu("File");
        file.setForeground(ForeColor1);
        edit = new JMenu("Edit");
        edit.setForeground(ForeColor1);
        help = new JMenu("Help");
        help.setForeground(ForeColor1);

        file.add(mnew);
        file.add(open);
        file.add(save);

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);

        MenuBar.add(file);
        MenuBar.add(edit);
        MenuBar.add(help);

        code_area = new JTextArea();
        code_area.setBounds(5, 5, 975, 525);
        code_area.setBackground(Code_BG);
        code_area.setForeground(ForeColor1);
        code_area.addKeyListener(this);

        mainframe.add(code_area);
        mainframe.add(MenuBar);

        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainframe.setJMenuBar(MenuBar);
        mainframe.setTitle("Pirate Studio Code");
        mainframe.setLayout(null);
        mainframe.setSize(1000, 600);
        mainframe.setVisible(true);
    }

    /*
██████╗  ██████╗ ████████╗ ██████╗ ███╗   ██╗███████╗███████╗
██╔══██╗██╔═══██╗╚══██╔══╝██╔═══██╗████╗  ██║██╔════╝██╔════╝
██████╔╝██║   ██║   ██║   ██║   ██║██╔██╗ ██║█████╗  ███████╗
██╔══██╗██║   ██║   ██║   ██║   ██║██║╚██╗██║██╔══╝  ╚════██║
██████╔╝╚██████╔╝   ██║   ╚██████╔╝██║ ╚████║███████╗███████║
╚═════╝  ╚═════╝    ╚═╝    ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚══════╝
                                                             
    */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            code_area.cut();
        if (e.getSource() == paste)
            code_area.paste();
        if (e.getSource() == copy)
            code_area.copy();
        if (e.getSource() == selectAll)
            code_area.selectAll();

        if (e.getSource() == open) {
            select_file();
        }
        if (e.getSource() == save) {
            try {
                save_file();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Main();
        
    }

/*
 █████╗ ████████╗ █████╗      ██╗ ██████╗ ███████╗
██╔══██╗╚══██╔══╝██╔══██╗     ██║██╔═══██╗██╔════╝
███████║   ██║   ███████║     ██║██║   ██║███████╗
██╔══██║   ██║   ██╔══██║██   ██║██║   ██║╚════██║
██║  ██║   ██║   ██║  ██║╚█████╔╝╚██████╔╝███████║
╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚══════╝
                                                  
*/

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == KeyEvent.VK_F5) {
            try {
                save_file();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            ArrayList<Token> tokens = lex(code_area.getText());
            String result = "";
            for (Token token : tokens) {
                result += "(" + token.getTipo() + ": " + token.getValor() + " | SE PROCESA: " + token.getProcessed() + ")\n";
            }
            JOptionPane.showMessageDialog(null, result);
        }
        
        if ((e.getKeyCode() == KeyEvent.VK_S) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            try {
                save_file();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        
        if ((e.getKeyCode() == KeyEvent.VK_O) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            select_file();
        }
        if (e.getKeyCode() == KeyEvent.VK_F6) {
            Tipos last = null;
            ArrayList<Token> tokens = lex(code_area.getText());
            String codigo_limpio = "";
            for (Token token : tokens) {
                if ((last == Tipos.SALTO && token.getTipo() == Tipos.SALTO) || (token.getTipo() == Tipos.COMENTARIO) || (!token.getProcessed() && !token
                        .getValor().equals("\\n"))) {
                    
                } else {
                    if (token.getValor().equals("\\n")) {
                        codigo_limpio += "\n";                   
                    } else {
                        codigo_limpio += token.getValor();
                    }
                    last = token.getTipo();
                }
            }
            JOptionPane.showMessageDialog(null, codigo_limpio);
        }
        

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}