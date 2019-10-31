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
import java.io.IOException;
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
import javax.swing.UnsupportedLookAndFeelException;
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

/**Classe com a interface grafica e as funcoes para manipulacao do arquivo e do texto.
* @author Diego da Silva Parra e Mateus Fernandes Doimo
* @version 2.0
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
    
    /** Construtor para criar a interface.
     *Nela sao criados os itens e seus respectivos botoes bem como sao adicionados os respectivos icones. 
     * Alem disso, sao especificadas todas as acoes de edicao (copiar, colar, recortar, desfazer, refazer e selecionar tudo) bem como cria-se a area de manipulacao do texto.
     */
    public JEditor(){
        //Criação do Frame
        f = new JFrame("Editor de texto");
        
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e){}
        
        //Componente de texto
        t = new JTextArea(10,20);
        
        // refazer e desfazer
        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
        KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);

        undoManager = new UndoManager();
        
        Document document = t.getDocument();
        document.addUndoableEditListener((UndoableEditEvent e) -> {
            undoManager.addEdit(e.getEdit());
        });

        //Barra de menu
        JMenuBar mb = new JMenuBar();
        
        //Menu Arquivo
        JMenu itemArq = new JMenu("Arquivo");
        
        //Criação dos itens do menu Arquivo
        JMenuItem btnNovo = new JMenuItem("Novo");
        JMenuItem btnAbrir = new JMenuItem("Abrir");
        JMenuItem btnSalvar = new JMenuItem("Salvar");
        
        //Icones dos botões do menu Arquivo
        visualBtn(btnNovo,"/icon/new.png");
        visualBtn(btnAbrir,"/icon/file.png");
        visualBtn(btnSalvar,"/icon/save.png");
        
        btnNovo.addActionListener(this);
        btnAbrir.addActionListener(this);
        btnSalvar.addActionListener(this);
        
        itemArq.add(btnNovo);
        itemArq.add(btnAbrir);
        itemArq.add(btnSalvar);
        
        //Menu Editar
        JMenu itemEdit = new JMenu("Editar");
        
        //Criação dos itens do menu Editar
        JMenuItem btnCopiar = new JMenuItem("Copiar");
        JMenuItem btnColar = new JMenuItem("Colar");
        JMenuItem btnRecortar = new JMenuItem("Recortar");
        JMenuItem btnDesfazer = new JMenuItem("Desfazer");
        JMenuItem btnRefazer = new JMenuItem("Refazer");
        JMenuItem btnSelectAll = new JMenuItem("Selecionar tudo");
        
        //Icones dos botões do menu Editar
        visualBtn(btnCopiar,"/icon/copy.png");
        visualBtn(btnColar,"/icon/paste.png");        
        visualBtn(btnRecortar,"/icon/cut.png");
        visualBtn(btnDesfazer,"/icon/undo.png");
        visualBtn(btnRefazer,"/icon/redo.png");
        visualBtn(btnSelectAll,"/icon/selall.png");
        
        itemEdit.add(btnCopiar);
        itemEdit.add(btnColar);
        itemEdit.add(btnRecortar);
        itemEdit.add(btnDesfazer);
        itemEdit.add(btnRefazer);
        itemEdit.add(btnSelectAll);
        
        btnRecortar.addActionListener(this);
        btnCopiar.addActionListener(this);
        btnColar.addActionListener(this);
        btnDesfazer.addActionListener(this);
        btnRefazer.addActionListener(this);
        btnSelectAll.addActionListener(this);
        
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
        
        //Inserindo os itens criados na barra de menu
        mb.add(itemArq);
        mb.add(itemEdit);
                
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
                super.windowClosing(e);
                int ans = JOptionPane.showConfirmDialog(rootPane, "Salvar alterações ?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (ans == JOptionPane.YES_OPTION) {
                    salvarArq();
                }
            }
        });
        
        f.setVisible(true);
    }

    /** Metodo para inserir os icones nos botoes do editor.
     */       
    public void visualBtn(JMenuItem btn, String icone){
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource(icone)));
        btn.setFocusable(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(5,5,5,5));
    }
    
    /** Metodo para salvar o arquivo no disco.
     * Nela cria-se a caixa Salvar com a definicao do caminho do diretorio em que o arquivo sera gravado.
     */
    public void salvarArq(){
        // Criar um objeto da classe JFileChooser
        JFileChooser j = new JFileChooser("f:");
        //Chamar a função para mostrar a caixa Salvar
        int r = j.showSaveDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {

            // Caminho do diretório
            File fi = new File(j.getSelectedFile().getAbsolutePath());

            try {
                // Escritor do arquivo
                FileWriter wr = new FileWriter(fi, false);

                // Escrever
                try ( // Buffer para escriva
                        BufferedWriter w = new BufferedWriter(wr)) {
                    // Escrever
                    w.write(t.getText());
                    
                    w.flush();
                }
            }
            catch (IOException evt) {
                JOptionPane.showMessageDialog(f, evt.getMessage());
            }
        }
        //Se a operação for cancelada
        else
            JOptionPane.showMessageDialog(f, "Operação cancelada pelo usuário");
    }
    
    /** Metodo que especifica e realiza as acoes de cada botao da barra de menu.
     */
    @Override
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
            case "Selecionar tudo":
                t.selectAll();
                break;                
            case "Salvar":
                salvarArq();
                break;
            case "Abrir":
                {
                    // Criar um objeto da classe JFileChooser
                    JFileChooser j = new JFileChooser("f:");
                    //Chamar a função para mostrar a caixa Salvar
                    int r = j.showOpenDialog(null);
                    // Se o arquivo for selecionado
                    if (r == JFileChooser.APPROVE_OPTION) {
                        // Caminho do diretório
                        File fi = new File(j.getSelectedFile().getAbsolutePath());
                        
                        try {
                            // String
                            String s1 = "", sl = "";
                            
                            // Leitor do arquivo
                            FileReader fr = new FileReader(fi);
                            
                            // Leitor do buffer
                            BufferedReader br = new BufferedReader(fr);
                            
                            // Inicializando a string
                            sl = br.readLine();
                            
                            // Entrada do arquivo
                            while ((s1 = br.readLine()) != null) {
                                sl = sl + "\n" + s1;
                            }
                            
                            // Texto
                            t.setText(sl);
                        }
                        catch (IOException evt) {
                            JOptionPane.showMessageDialog(f, evt.getMessage());
                        }
                    }
                    //Se a operação for cancelada
                    else
                        JOptionPane.showMessageDialog(f, "Operação cancelada pelo usuário");
                    break;
                }
            case "Novo":
                t.setText("");
                break;
            default:
                break;
        }
    } 
}
