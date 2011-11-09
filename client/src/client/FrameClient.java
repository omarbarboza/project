package client;

import java.awt.Dimension;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.InetAddress;
import java.net.Socket;

import java.net.UnknownHostException;

import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class FrameClient extends JFrame {
    private JButton jButton1 = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();

    private Socket _client = null;
    private InputStreamReader _isr = null;
    private BufferedReader _br = null;
    private PrintWriter _pw = null;
    private JTable jTable1 = new JTable();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTextArea jTextArea1 = new JTextArea();


    public void listener(){
        InetAddress inetAddress =  null;
        try {
            inetAddress = InetAddress.getLocalHost();
            _client = new Socket(inetAddress, 12339);
            _isr = new InputStreamReader( _client.getInputStream() );
            _br = new BufferedReader( _isr );
            _pw = new PrintWriter(_client.getOutputStream(), true);
        } catch (UnknownHostException e) {
            System.out.println("Error al identificar al host "
                               + e.getMessage() );
        } catch (IOException e) {
            System.out.println("Error al crear el socket cliente "
                               + e.getMessage() );
        }
    }
    public FrameClient() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize(new Dimension(634, 361));
        this.setTitle("Client Frame");
        jButton1.setText("Obtain information");
        jButton1.setBounds(new Rectangle(240, 20, 210, 55));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jScrollPane1.setBounds(new Rectangle(15, 95, 590, 215));
        jScrollPane2.setBounds(new Rectangle(25, 25, 185, 50));
        jScrollPane1.getViewport().add(jTable1, null);
        jScrollPane2.getViewport().add(jTextArea1, null);
        this.getContentPane().add(jScrollPane2, null);
        this.getContentPane().add(jScrollPane1, null);
        this.getContentPane().add(jButton1, null);
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        
        
        _pw.println( "HEllo world" );
        String message = "";
        try {
            message = _br.readLine();
            
            jTextArea1.setText(message);   
        } catch (IOException f) {
            System.out.println("Error al leer el resultado "
                               + f.getMessage() );
        }
        /*StringTokenizer st = new StringTokenizer(message, "|");
        int count = st.countTokens();
        String[][] data = new String[count][3];
        int k = 0;
        while( k < count ){
            String record = st.nextToken();
            StringTokenizer str = new StringTokenizer(record," ");
            data[k][0] = str.nextToken();            
            data[k][1] = str.nextToken();
            
            k++;
        }
        String[] header = {"Department name","Salary"};
        DefaultTableModel dtm = new DefaultTableModel(data, header);
        jTable1.setModel( dtm );
*/
           
    }
    
    private void this_windowClosing(WindowEvent e) {
        _pw.close();
        try {
            _br.close();
            _isr.close();
            _client.close();
        } catch (IOException f) {
            System.out.println("Error al cerrar el flujo de lectura "
                               + f.getMessage() );
        }
    }
    
    public static void main(String[] argumentos){
        FrameClient frame = new FrameClient();
        frame.setVisible(true);
        frame.listener();
    }
}
