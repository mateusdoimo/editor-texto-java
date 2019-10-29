/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
    editor(){
        f = new JFrame("Editor de texto");
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e){}
        
        t = new JTextArea();
        
        JMenuBar mb = new JMenuBar();
        
        JMenu btnMenu = new JMenu("Arquivo");
        
        JMenuItem btnNovo = new JMenuItem("Novo");
        JMenuItem btnAbrir = new JMenuItem("Abrir");
        JMenuItem btnSalvar = new JMenuItem("Salvar");
        JMenuItem btnImprimir = new JMenuItem("Imprimir");
        
        btnNovo.addActionListener(this);
        btnAbrir.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
