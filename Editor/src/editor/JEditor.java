/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author DiegoParra
 */
public final class JEditor extends JFrame implements ActionListener{
    //Componentes de texto
    private final JTextArea t;
    
    private final URL caminhoImagem; //icone
    private final Image iconeTitulo; //icone
    private final UndoManager undoManager;
    //Frame
    private final JFrame f;
    private final UndoManager undo = new UndoManager();
    
    //construtor
    public JEditor(){
        
        f = new JFrame("Editor de texto");
        
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e){}
        
        t = new JTextArea(10,20);
        
        
        // refazer e desfazer
        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_Z, Event.CTRL_MASK);
        KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_Y, Event.CTRL_MASK);

        undoManager = new UndoManager();
        
        Document document = t.getDocument();
        document.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        //
        
        JMenuBar mb = new JMenuBar();
        
        //Menu Arquivo
        JMenu itemArq = new JMenu("Arquivo");
        
        JMenuItem btnNovo = new JMenuItem("Novo");
        JMenuItem btnAbrir = new JMenuItem("Abrir");
        JMenuItem btnSalvar = new JMenuItem("Salvar");
        
        visualBtn(btnNovo,"/icon/new.png");
        visualBtn(btnAbrir,"/icon/file.png");
        visualBtn(btnSalvar,"/icon/save.png");
        
        btnNovo.addActionListener(this);
        btnAbrir.addActionListener(this);
        btnSalvar.addActionListener(this);
        
        itemArq.add(btnNovo);
        itemArq.add(btnAbrir);
        itemArq.add(btnSalvar);
        //
        
        //Menu Editar
        JMenu itemEdit = new JMenu("Editar");
        
        JMenuItem btnRecortar = new JMenuItem("Recortar");
        JMenuItem btnCopiar = new JMenuItem("Copiar");
        JMenuItem btnColar = new JMenuItem("Colar");
        JMenuItem btnDesfazer = new JMenuItem("Desfazer");
        JMenuItem btnRefazer = new JMenuItem("Refazer");
        
        visualBtn(btnRecortar,"/icon/cut.png");
        visualBtn(btnCopiar,"/icon/copy.png");
        visualBtn(btnColar,"/icon/paste.png");
        visualBtn(btnDesfazer,"/icon/undo.png");
        visualBtn(btnRefazer,"/icon/redo.png");
        
        itemEdit.add(btnRefazer);
        itemEdit.add(btnDesfazer);
        itemEdit.add(btnRecortar);
        itemEdit.add(btnCopiar);
        itemEdit.add(btnColar);
        
        
        
        btnRecortar.addActionListener(this);
        btnCopiar.addActionListener(this);
        btnColar.addActionListener(this);
        btnDesfazer.addActionListener(this);
        btnRefazer.addActionListener(this);
        
        // Açoes de desfazer e refazer

        t.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(undoKeyStroke, "undoKeyStroke");
        t.getActionMap().put("undoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.undo();
                 } catch (CannotUndoException cue) {}
            }
        });
        
        t.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(redoKeyStroke, "redoKeyStroke");
        t.getActionMap().put("redoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.redo();
                 } catch (CannotRedoException cre) {}
            }
        });
        //
        
        
        JMenuItem itemFechar = new JMenuItem("Fechar");
        itemFechar.addActionListener(this);
        
        
        mb.add(itemArq);
        mb.add(itemEdit);
        mb.add(itemFechar);
                
        JScrollPane scroll = new JScrollPane (t);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        
        f.setJMenuBar(mb);
        f.add(scroll);
        f.setSize(700, 600);
        
        t.setColumns(20);
        t.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        t.setLineWrap(true);
        t.setRows(5);
        t.setBorder(new EmptyBorder(10, 10, 10, 10));
        
         //icone
        caminhoImagem = this.getClass().getClassLoader().getResource("icon/icone.png");
        iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoImagem);

        f.setIconImage(iconeTitulo);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
              
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.
                int ans = JOptionPane.showConfirmDialog(rootPane, "Save Changes ?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (ans == JOptionPane.YES_OPTION) {
                    //saveChanges();
                }
            }

        });
        
        f.setVisible(true);
    }
    
    public void visualBtn(JMenuItem btn, String icone){
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource(icone)));
        btn.setFocusable(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(5,5,5,5));
    }
    public void actionPerformed(ActionEvent e)     { 
        String s = e.getActionCommand(); 
  
        switch (s) {
            case "Recortar":
                t.cut();
                break;
            case "Copiar":
                t.copy() ;
                break;
            case "Colar":
                t.paste();
                break;
                
            case "Desfazer":
                try {
                    undoManager.undo();
                } catch (CannotUndoException cue) {}
                break;
            case "Refazer":
                try {
                    undoManager.redo();
                } catch (CannotRedoException cre) {}
                break;
            case "Salvar":
                {
                    // Create an object of JFileChooser class
                    JFileChooser j = new JFileChooser("f:");
                    // Invoke the showsSaveDialog function to show the save dialog
                    int r = j.showSaveDialog(null);
                    if (r == JFileChooser.APPROVE_OPTION) {
                        
                        // Set the label to the path of the selected directory
                        File fi = new File(j.getSelectedFile().getAbsolutePath());
                        
                        try {
                            // Create a file writer
                            FileWriter wr = new FileWriter(fi, false);
                            
                            // Create buffered writer to write
                            BufferedWriter w = new BufferedWriter(wr);
                            
                            // Write
                            w.write(t.getText());
                            
                            w.flush();
                            w.close();
                        }
                        catch (Exception evt) {
                            JOptionPane.showMessageDialog(f, evt.getMessage());
                        }
                    }
                    //Se a operação for cancelada
                    else
                        JOptionPane.showMessageDialog(f, "the user cancelled the operation");
                    break;
                }
            case "Abrir":
                {
                    // Create an object of JFileChooser class
                    JFileChooser j = new JFileChooser("f:");
                    // Invoke the showsOpenDialog function to show the save dialog
                    int r = j.showOpenDialog(null);
                    // If the user selects a file
                    if (r == JFileChooser.APPROVE_OPTION) {
                        // Set the label to the path of the selected directory
                        File fi = new File(j.getSelectedFile().getAbsolutePath());
                        
                        try {
                            // String
                            String s1 = "", sl = "";
                            
                            // File reader
                            FileReader fr = new FileReader(fi);
                            
                            // Buffered reader
                            BufferedReader br = new BufferedReader(fr);
                            
                            // Initilize sl
                            sl = br.readLine();
                            
                            // Take the input from the file
                            while ((s1 = br.readLine()) != null) {
                                sl = sl + "\n" + s1;
                            }
                            
                            // Set the text
                            t.setText(sl);
                        }
                        catch (Exception evt) {
                            JOptionPane.showMessageDialog(f, evt.getMessage());
                        }
                    }
                    // If the user cancelled the operation
                    else
                        JOptionPane.showMessageDialog(f, "the user cancelled the operation");
                    break;
                }
            case "Novo":
                t.setText("");
                break;
            case "Fechar":
                f.setVisible(false);
                break; 
            default:
                break;
        }
    } 
}
