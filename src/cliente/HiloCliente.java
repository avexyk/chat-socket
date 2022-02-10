
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;

public class HiloCliente extends Thread {
    private ObjectInputStream input;
    private FCliente cliente;
    private boolean activo=true;
    
    public HiloCliente(FCliente cliente, ObjectInputStream input) {
        this.cliente = cliente;
        this.input = input;
    }
    
    @Override
    public void run() {
        while (this.activo) {            
            String mensaje;
            try {
                mensaje = (String)this.input.readObject();
                this.cliente.getTextMensajes().append(mensaje+"\n");
            } catch (IOException e) {
                this.activo=false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
