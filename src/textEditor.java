import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;


public class textEditor extends JFrame implements ActionListener{

    String fileName;
    String FileAdress;
    PrintWriter writer =null;
    

 
    public void open(){
        FileDialog fileDialog = new FileDialog(this,"Aç",FileDialog.LOAD);
        fileDialog.setVisible(true);

        if(fileDialog.getFile()!=null){
            fileName = fileDialog.getFile();
            FileAdress = fileDialog.getDirectory();
            this.setTitle(fileName);

        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(FileAdress+fileName));
            textArea.setText("");
            String line = null;
            while((line = br.readLine())!=null){

                textArea.append(line);


                
            }
            
        } catch (Exception e) {
            
        }
    }

    public void save(){
        if(fileName==null){
            saveAs();

        }
        else{
            try {
                FileWriter fw = new FileWriter(FileAdress + fileName);
                fw.write(textArea.getText());
                this.setTitle(fileName);
                fw.close();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"BİR ŞEYLER TERS GİTTİ");

            }

        }
    }

    public void saveAs(){
        FileDialog fd = new FileDialog(this, "Kaydet", FileDialog.SAVE);
        fd.setVisible(true);

        if(fd.getFile()!=null){
            fileName = fd.getFile();
            FileAdress = fd.getDirectory();
            this.setTitle(fileName);
        }
        try {
            FileWriter fw = new FileWriter(FileAdress + fileName);
            fw.write(textArea.getText());
            fw.close();
           
        } catch (Exception e) {
           JOptionPane.showMessageDialog(this,"BİR ŞEYLER TERS GİTTİ");
            
        }
   
    }
   
    JTextArea textArea;
    JScrollPane scrollPane;
    JLabel label;
    JSpinner fontSizeSpinner;
    JButton fontColorButton;
    JComboBox fontbox;

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JMenuItem saveAsItem;
    JMenuItem newWindowItem;
    
    textEditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Not Defteri");
        this.setSize(500,500);
        this.setResizable(false);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial",Font.PLAIN,20));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        label = new JLabel("Font: ");

        fontSizeSpinner  = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)  fontSizeSpinner.getValue()));
            }
           
        });

        fontColorButton = new JButton("Renk");
        fontColorButton.setFocusable(false);
        fontColorButton.addActionListener(this);

        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        fontbox = new JComboBox(fonts);
        fontbox.addActionListener(this);
        fontbox.setSelectedItem("Arial");

        menuBar = new JMenuBar();
        fileMenu = new JMenu("Dosya");
        openItem = new JMenuItem("Aç");
        saveItem = new JMenuItem("Kaydet");
        exitItem = new JMenuItem("Çık");
        saveAsItem = new JMenuItem("Farklı Kaydet");
        newWindowItem = new JMenuItem("Yeni Pencere");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        saveAsItem.addActionListener(this);
        newWindowItem.addActionListener(this);
        openItem.setActionCommand("Aç");
        saveItem.setActionCommand("Kaydet");
        saveAsItem.setActionCommand("Farklı Kaydet");
        newWindowItem.setActionCommand("Yeni Pencere");
        exitItem.setActionCommand("Çık");

        fileMenu.add(newWindowItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
        this.add(label);
        this.add(fontSizeSpinner);
        this.add(fontColorButton);
        this.add(fontbox);
        this.add(scrollPane);
        this.setVisible(true);
    
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String komut = e.getActionCommand();

        switch(komut){
            case "Aç":  open(); break;
            case "Kaydet": save(); break;
            case "Farklı Kaydet": saveAs(); break;
            case "Yeni Pencere" : newWindow(); break;
            case "Çık": exit1(); break;
            
        }
        if(e.getSource()==fontColorButton){
            
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Renk seçiniz: ",Color.black);
            
            textArea.setForeground(color);
        }

        if(e.getSource()==fontbox){
            textArea.setFont(new Font((String)fontbox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }
        
        
        
    }
    public void newWindow(){
        this.setTitle("Yeni Pencere");
        textArea.setText("");
        setVisible(true);
        textEditor te = new textEditor();
        te.setVisible(false);
        
        
        

        
    }
    public void exit1(){
        System.exit(0);
        
    }

    
}
