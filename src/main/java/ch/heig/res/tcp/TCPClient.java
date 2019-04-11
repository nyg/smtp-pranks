package ch.heig.res.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {

    private static final Logger LOG = Logger.getLogger(TCPClient.class.getName());

    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;

    private int serverPort;
    private String serverIPAddress;

    public TCPClient(String serverIPAddress, int serverPort) {
        this.serverPort = serverPort;
        this.serverIPAddress = serverIPAddress;
    }

    /**
     * Start TCP connection
     */
    public void openConnection() {

        if (socket != null) {
            LOG.warning("Already connected to server, disconnect or create a new TCPClient instance.");
            return;
        }

        try {
            socket = new Socket(InetAddress.getByName(serverIPAddress), serverPort);
            socket.setSoTimeout(2500);

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * End TCP connection
     */
    public void closeConnection() {

        if (socket == null) {
            LOG.warning("Not connected to server.");
            return;
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
            output = null;
            socket = null;
            socket = null;
        }
    }

    /**
     * Sends a message to the server. A new line will be appended to the given value.
     *
     * @param message the message to send
     */
    public void sendMessage(String message, boolean raw) {

        try {
            output.write(message + (raw ? "" : "\r\n"));
            output.flush();
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void sendMessage(String message) {
        sendMessage(message, false);
    }

    /**
     * Reads a message from the server.
     *
     * @return the server's message
     */
    public List<String> readResponses() {

        List<String> responses = new ArrayList<>();
        try {
            String line;
            while ((line = input.readLine()) != null) {
                responses.add(line);
            }
        }
        catch (SocketTimeoutException e) {
            if (responses.size() == 0) {
                LOG.warning("Call to TCPClient.readResponses return no data.");
            }
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }

        return responses;
    }
}
