
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {
    private FCliente frameCliente;
    private Socket socket;
    private HiloCliente hiloCliente;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    
    public Cliente(FCliente frameCliente) {
        this.frameCliente = frameCliente;
    }
    
    public boolean conectar(String ip, String nombre) {
        try {
            this.socket = new Socket(InetAddress.getByName(ip), 5000);
            this.output = new ObjectOutputStream(this.socket.getOutputStream());
            this.input = new ObjectInputStream(this.socket.getInputStream());
            this.socket.getInputStream();
            this.output.writeObject(nombre);
            this.output.flush();
            this.hiloCliente = new HiloCliente(this.frameCliente, this.input);
            this.hiloCliente.start();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public void enviar(String mensaje) {
        try {
            this.output.writeObject(mensaje);
            this.output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
