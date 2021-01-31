
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.JobAttributes;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class swingS extends JFrame{
    String cname="";
    int start,stop,skor;
    JTextField text;
    JTextField competitor;
    JOptionPane alert;
    
    JButton startButton;
    JButton stopButton;
    JButton saveButton;
    JButton showButton;
    
    JLabel competitorText;
    JLabel textText;
    JLabel decimalLabel;
    JLabel decimalText;
    JLabel binaryLabel;
    JLabel binaryText;
    JLabel octalLabel;
    JLabel octalText;
    JLabel hexaLabel;
    JLabel hexaText;
    JTextArea reportField;
    JComboBox combobox;
    JScrollPane scrollPane;
    
    Thread t1;
    public swingS(){
        String s1[] = { "binary", "octal", "decimal", "hexadecimal" };
        
        Container container=getContentPane();
        container.setLayout(new FlowLayout());
        alert=new JOptionPane();
        competitorText = new JLabel("Enter a competitor: ");
        competitor=new JTextField();
        combobox = new JComboBox(s1);
        textText = new JLabel("Enter a number: ");
        text=new JTextField();
        competitor.setColumns(7);
        text.setColumns(7);
        startButton=new JButton("Start");
        stopButton=new JButton("Stop");
        saveButton=new JButton("save");
        showButton=new JButton("results");
        decimalLabel=new JLabel();
        binaryLabel=new JLabel();
        octalLabel=new JLabel();
        hexaLabel=new JLabel();
        reportField = new JTextArea();
        reportField.setEditable(false);
        //reportField.setLineWrap(true);
        scrollPane = new JScrollPane(reportField);
   
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);			
        scrollPane.setPreferredSize(new Dimension(400, 110));
        binaryText=new JLabel("Binary: ");
        decimalText=new JLabel("Decimal: ");
        octalText=new JLabel("Octal: ");
        hexaText=new JLabel("Hexadecimal: ");
        
        container.add(competitorText);
        container.add(competitor);
        container.add(textText);
        container.add(text);
        container.add(combobox);
        container.add(startButton);
        container.add(stopButton);
        container.add(saveButton);
        container.add(showButton); 
        container.add(decimalText);
        container.add(decimalLabel);
        container.add(binaryText);
        container.add(binaryLabel);
        container.add(octalText);
        container.add(octalLabel);
        container.add(hexaText);
        container.add(hexaLabel);
        container.add(scrollPane, BorderLayout.CENTER);
        setTitle("Marvel Bomb Timer");
        setSize(730,300);
        setVisible(true);
        
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                cname=competitor.getText();
                reportField.setText("");
                t1 = new Thread(new Runnable() {
                    public void run() {
                        
                        switch(String.valueOf(combobox.getSelectedItem())){
                            case "binary":
                                start=Integer.parseInt(text.getText(),2);
                                convert(2);                                   
                                break;
                                
                            case "octal":
                                start=Integer.parseInt(text.getText(),8);
                                convert(8);
                                break;
                            case "decimal":
                                start=Integer.parseInt(text.getText());
                                convert(10);
                                break;
                            case "hexadecimal":
                                start=Integer.parseInt(text.getText(),16);
                                convert(16);
                                break;
                        }
                    }
                });
                t1.start();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dbconnection db=new dbconnection();   
                    db.saveResults(cname, start, stop);
                    alert.showMessageDialog(container,"Your result saved.");  
                    cname="";
                    start=0;
                    stop=0;
                } catch (SQLException ex) {
                    Logger.getLogger(swingS.class.getName()).log(Level.SEVERE, null, ex);
                }
                
           }
        });
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            dbconnection db;
                try {
                    db = new dbconnection();

                    reportField.setText(db.bringResults());
                   
                } catch (SQLException ex) {
                    Logger.getLogger(swingS.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new Thread(new Runnable() {
                    public void run() {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                
                                t1.stop();
                                
                                
                                switch(String.valueOf(combobox.getSelectedItem())){
                                    case "binary":
                                        stop=Integer.parseInt(binaryLabel.getText(),2);  
                                        break;
                                    case "octal":
                                        stop=Integer.parseInt(octalLabel.getText(),8);
                                        break;
                                    case "decimal":
                                        stop=Integer.valueOf(decimalLabel.getText());
                                        break;
                                    case "hexadecimal":
                                        stop=Integer.parseInt(hexaLabel.getText(),16);
                                        break;
                                    default:       
                                }
                            }
                            
                       });
                    }
                }).start();
          
            }
        });
    }
    
    
    public void convert(int x){
        int decimalInt = Integer.parseInt(text.getText(),x);
                            

     for (int i = decimalInt; i >= 0; i--) {
            String decimalStr = String.valueOf(i);
            String binaryStr = Integer.toBinaryString(i);
            String octalStr = Integer.toOctalString(i);
            String hexaStr = Integer.toHexString(i);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    decimalLabel.setText(decimalStr);
                    binaryLabel.setText(binaryStr);
                    octalLabel.setText(octalStr);
                    hexaLabel.setText(hexaStr);
                    
        }
            });
            try {
                java.lang.Thread.sleep(1000);
            }
            catch(Exception e) { }
        }
    }
    
    public static void main(String[] args) throws IOException, SQLException {
        
        //dbconnection db=new dbconnection();
        swingS app=new swingS();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    }

}
