/**
 * Source: http://www.java2s.com/Code/Java/Network-Protocol/SendingMailUsingSockets.htm
 */

package ch.heig.res.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TCPClient {

    private static final Logger LOG = Logger.getLogger(TCPClient.class.getName());
    //private static final String SERVER_REQUEST_FORMAT = "^RESULT (.*) END$";



    private int port;
    private String ip;
    private String hostName;

    protected Socket socket = null;
    protected BufferedReader input;
    protected BufferedWriter output;

    public TCPClient(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    /**
     * Start TCP connection
     */
    public void startConnection() {

        if (socket != null) {
            LOG.warning("Already connected to server, disconnect or create new instance.");
            return;
        }

        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * End TCP connection
     */
    public void endConnection() {

        if (socket == null) {
            LOG.warning("Not connected to server.");
        }
        try {
            input.close();
            output.close();
            socket.close();
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
        finally {
            socket = null;
        }
    }

    public void sendMessage(String message) {
        try {
            output.write(message + "\n");
            output.flush();
        }
        catch (IOException e){
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String readMessage(){
        String message = "";
        try{
            while (input.ready()) {
                message += input.readLine();
            }
        }
        catch (IOException e){
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }

        return message;
    }
}
