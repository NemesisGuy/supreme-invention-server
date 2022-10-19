/*
 *     
 * 
 */
package za.co.nemesisnet.supreme.invention.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter B
 */
public class SocketHandler {

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public SocketHandler(Socket cs) {
        try {
            this.oos = new ObjectOutputStream(cs.getOutputStream());
            this.ois = new ObjectInputStream(cs.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void sendObject(Object o) {
        try {
            this.oos.writeObject(o);
            this.oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public Object receiveObject() {
        try {
            return this.ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
