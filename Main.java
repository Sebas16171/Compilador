import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.filechooser.*;

import codigo.Tokens;

import java.io.*;

public class Main implements ActionListener, KeyListener {

    public static String ruta;

    static JFrame mainframe;
    JMenuBar MenuBar;
    JMenu file, edit, help;
    JMenuItem cut, copy, paste, selectAll, mnew, open, save;
    static JTextArea code_area;

    public static void Lexer() {
        try {
            File asmFile = new File(ruta);
            Reader lector = new BufferedReader(new FileReader(asmFile));
            Lexer lexer = new Lexer(lector);
            String resultado = "";

            while (true) {
                Tokens tokens = lexer.yylex();
                if (tokens == null) {
                    resultado += "FIN";
                    JOptionPane.showMessageDialog(null, resultado);
                    return;
                }
                switch (tokens) {
                    case ERROR:
                        resultado += "Simbolo no definido\n";
                        break;
                    case Identificador:
                    case Numero:
                    case Reservadas:
                        resultado += "Es un " + tokens + "\n";
                        break;
                    default:
                        resultado += "Token: " + tokens + "\n";

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean debug() {
        String lineas[] = code_area.getText().split("\\r?\\n");

        if (!lineas[lineas.length - 1].trim().equals("end")) {
            return false;
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

    public static void fill_area(List<String> code) {
        for (int i = 0; i < code.size(); i++) {
            code_area.append(code.get(i) + '\n');
        }
    }

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

    public static void generarLexer() {
        String ruta = "D:/Documentos/Uni/Cuatri 3/Software de Sistemas/Compilador/Lexer.flex";
        File archivo = new File(ruta);
        JFlex.Main.generate(archivo);
    }

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
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            Lexer();
            if (debug()) {
                JOptionPane.showMessageDialog(null, "Jala");
            } else {
                JOptionPane.showMessageDialog(null, "No jala");
            }
        }

        if ((e.getKeyCode() == KeyEvent.VK_S) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            try {
                save_file();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        if ((e.getKeyCode() == KeyEvent.VK_O) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            select_file();
        }
        

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}