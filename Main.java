import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.filechooser.*;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main implements ActionListener, KeyListener {
    JFrame mainframe;
    JMenuBar MenuBar;
    JMenu file, edit, help;
    JMenuItem cut, copy, paste, selectAll, mnew, open, save;
    static JTextArea code_area;

    public static boolean debug() {
        String lineas[] = code_area.getText().split("\\r?\\n");

        
        if(!lineas[lineas.length - 1].trim().equals("end")){
            return false;
        }

        return true;
    }

    public static void select_file() {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Assembly code files", "asm");
        String ruta = "";

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

            } catch (Exception e) {
                System.out.print("Se murio");
            }
        }
    }

    public static void fill_area(List<String> code) {
        for (int i = 0; i < code.size(); i++) {
            code_area.append(code.get(i) + '\n');
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
    }

    public static void main(String[] args) {
        String input = "11 + 22 - 33";
        ArrayList<Token> tokens = lex(input);
        for (Token token : tokens) {
            System.out.println("(" + token.getTipo() + ": " + token.getValor() + ")");
        }
        new Main();
    }

    private static ArrayList<Token> lex(String input) {
        final ArrayList<Token> tokens = new ArrayList<Token>();
        final StringTokenizer st = new StringTokenizer(input);

        while (st.hasMoreTokens()) {
            String palabra = st.nextToken();
            boolean matched = false;

            for (Tipos tokenTipo : Tipos.values()) {
                Pattern patron = Pattern.compile(tokenTipo.patron);
                Matcher matcher = patron.matcher(palabra);
                if (matcher.find()) {
                    Token tk = new Token();
                    tk.setTipo(tokenTipo);
                    tk.setValor(palabra);
                    tokens.add(tk);
                    matched = true;
                }
            }

            if (!matched) {
                throw new RuntimeException("Se encontró un token invalido.");
            }
        }

        return tokens;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == KeyEvent.VK_F5){
            if (debug()) {
                JOptionPane.showMessageDialog(null, "Jala");
            } else {
                JOptionPane.showMessageDialog(null, "No jala");
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}