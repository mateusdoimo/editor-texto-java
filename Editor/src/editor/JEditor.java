/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

/**
 *
 * @author DiegoParra
 */
public class JEditor extends JFrame implements ActionListener{
    //Componentes de texto
    JTextArea t;
    
    //Frame
    JFrame f;
    
    //construtor
    JEditor(){
        f = new JFrame("Editor de texto");
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e){}
        
        t = new JTextArea();
        
        JMenuBar mb = new JMenuBar();
        
        JMenu itemArq = new JMenu("Arquivo");
        
        JMenuItem btnNovo = new JMenuItem("Novo");
        JMenuItem btnAbrir = new JMenuItem("Abrir");
        JMenuItem btnSalvar = new JMenuItem("Salvar");
        
        btnNovo.addActionListener(this);
        btnAbrir.addActionListener(this);
        btnSalvar.addActionListener(this);
        
        itemArq.add(btnNovo);
        itemArq.add(btnAbrir);
        itemArq.add(btnSalvar);
        
        JMenu itemEdit = new JMenu("Editar");
        
        JMenuItem btnRecortar = new JMenuItem("Recortar");
        JMenuItem btnCopiar = new JMenuItem("Copiar");
        JMenuItem btnColar = new JMenuItem("Colar");
        JMenuItem btnDesfazer = new JMenuItem("Desfazer");
        JMenuItem btnRefazer = new JMenuItem("Refazer");
        
        btnRecortar.addActionListener(this);
        btnCopiar.addActionListener(this);
        btnColar.addActionListener(this);
        btnDesfazer.addActionListener(this);
        btnRefazer.addActionListener(this);
        
        itemEdit.add(btnRecortar);
        itemEdit.add(btnCopiar);
        itemEdit.add(btnColar);
        itemEdit.add(btnDesfazer);
        itemEdit.add(btnRefazer);
        
        JMenuItem itemFechar = new JMenuItem("Fechar");
        
        itemFechar.addActionListener(this);
        
        mb.add(itemArq);
        mb.add(itemEdit);
        mb.add(itemFechar);
        
        f.setJMenuBar(mb);
        f.add(t);
        f.setSize(500, 500);
        f.show();
    }
    
    public void actionPerformed(ActionEvent e)     { 
        String s = e.getActionCommand(); 
  
        switch (s) {
            case "Recortar":
                t.recortar();
                break;
            case "Copiar":
                t.copiar() ;
                break;
            case "Colar":
                t.colar();
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
            case "New":
                t.setText("");
                break;
            case "close":
                f.setVisible(false);
                break; 
            default:
                break;
        }
    } 
  
    // Main class 
    public static void main(String args[]) 
    { 
        JEditor e = new JEditor(); 
    } 
}
