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
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {

    private static final Logger LOG = Logger.getLogger(TCPClient.class.getName());

    protected Socket socket = null;
    protected BufferedReader input;
    protected BufferedWriter output;

    private int port;
    private String ip;
    private String hostName;

    public TCPClient(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    /**
     * Start TCP connection
     */
    public void startConnection() {

        if (socket != null) {
            LOG.warning("Already connected to server, disconnect or create a new TCPClient instance.");
            return;
        }

        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * End TCP connection
     */
    public void endConnection() {

        if (socket == null) {
            LOG.warning("Not connected to server.");
            return;
        }

        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            output = null;
            socket = null;
            socket = null;
        }
    }

    /**
     * Sends a message to the server. A new line will be appended to the given
     * value.
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {
        sendMessage(message, false);
    }

    public void sendMessage(String message, boolean raw) {

        try {
            output.write(message + (raw ? "" : "\r\n"));
            output.flush();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Reads a message from the server.
     *
     * @return the server's message
     */
    public String readMessage() {

        StringBuilder message = new StringBuilder();
        try {
            String line;
            while ((line = input.readLine()) != null) {
                message.append(line);
            }
        } catch (SocketTimeoutException e) {
            // no big deal, return what we have read until now
            LOG.warning("A SocketTimeoutException occured.");
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }

        return message.toString();
    }

    /**
     * Method only for unit tests. Package visibility.
     */
    void setSocketTimeout(int seconds) throws SocketException {
        socket.setSoTimeout(seconds * 1000);
    }
}
