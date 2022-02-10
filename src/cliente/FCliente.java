
package cliente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class FCliente extends javax.swing.JFrame {
    private JPanel panelConexion;
    private JTextField textIP;
    private JTextField textNombre;
    private JButton buttonEnviar;
    private JTextField textMensaje;
    private JButton buttonConectar;
    private JLabel labelNombre;
    private JLabel labelIP;
    private JTextArea textMensajes;
    private JPanel panelMensaje;
    private Cliente cliente;
    private Socket socket;
    private HiloCliente hiloCliente;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    
    public static void main(String[] args) {
        FCliente frame = new FCliente();
        frame.setVisible(true);
    }
    
    public FCliente() {
        initGUI();
        this.cliente = new Cliente(this);
    }
    
    public JTextArea getTextMensajes() {
        return this.textMensajes;
    }
    
    private void initGUI() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mini Chat. Cliente");
        getContentPane().setLayout(new BorderLayout());
        {
            panelConexion = new JPanel();
            panelConexion.setLayout(new FlowLayout());
            getContentPane().add(panelConexion, BorderLayout.NORTH);
            {
                labelIP = new JLabel();
                panelConexion.add(labelIP);
                labelIP.setText("IP: ");
            }
            {
                textIP = new JTextField();
                panelConexion.add(textIP);
                textIP.setText("127.0.0.1");
            }
            {
                labelNombre = new JLabel();
                panelConexion.add(labelNombre);
                labelNombre.setText("Nombre Cliente");
            }
            {
                textNombre = new JTextField();
                panelConexion.add(textNombre);
                textNombre.setPreferredSize(new java.awt.Dimension(100, 20));
                textNombre.setSize(80, 20);
            }
            {
                buttonConectar = new JButton();
                panelConexion.add(buttonConectar);
                buttonConectar.setText("Conectar");
                buttonConectar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        BConectarActionPerformed(evt);
                    }
                });
            }
        }
        {
            panelMensaje = new JPanel();
            BorderLayout PMensajeLayout = new BorderLayout();
            panelMensaje.setLayout(PMensajeLayout);
            getContentPane().add(panelMensaje, BorderLayout.SOUTH);
            {
                textMensaje = new JTextField();
                panelMensaje.add(textMensaje, BorderLayout.CENTER);
                textMensaje.setEnabled(false);
                textMensaje.setPreferredSize(new java.awt.Dimension(348, 21));
            }
            {
                buttonEnviar = new JButton();
                panelMensaje.add(buttonEnviar, BorderLayout.EAST);
                buttonEnviar.setText("Enviar");
                buttonEnviar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        buttonEnviarActionPerformed(evt);
                    }
                });
                buttonEnviar.setEnabled(false);
            }
        }
        {
            textMensajes = new JTextArea();
            getContentPane().add(textMensajes, BorderLayout.CENTER);
        }
        setSize(400, 300);
    }
    
    private void BConectarActionPerformed(ActionEvent evt) {
        if (!this.textNombre.getText().equals("")) {
            if (this.cliente.conectar(this.textIP.getText(), this.textNombre.getText())) {
                this.buttonConectar.setEnabled(false);
                this.textNombre.setEnabled(false);
                this.textIP.setEnabled(false);
                this.textMensaje.setEnabled(true);
                this.buttonEnviar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error del servidor", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Inserte Nombre", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buttonEnviarActionPerformed(ActionEvent evt) {
        this.cliente.enviar(this.textMensaje.getText());
        this.textMensaje.setText("");
    }
}
