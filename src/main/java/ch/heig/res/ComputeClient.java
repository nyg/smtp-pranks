package ch.heig.res;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComputeClient {

    private static final Logger LOG = Logger.getLogger(ComputeClient.class.getName());
    private static final String SERVER_REQUEST_FORMAT = "^RESULT (.*) END$";

    private int port;
    private String ip;

    private Socket socket = null;
    private BufferedReader input;
    private BufferedWriter output;

    public ComputeClient(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public void startConnection() {

        if (socket != null) {
            LOG.warning("Already connected to server, deconnect or create new instance.");
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

    public void endConnection() {

        if (socket == null) {
            LOG.warning("Not connected to server.");
        }

        try {
            output.write("COMPUTE END\n");
            output.flush();

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

    public Double sendComputation(String expression) {

        try {
            // sending computation to server
            LOG.info(String.format("Sending computation to server: %s", expression));
            writeRequest(expression);

            // reading result from server
            String response = input.readLine();
            Pattern p = Pattern.compile(SERVER_REQUEST_FORMAT);
            Matcher m = p.matcher(response);

            if (m.find()) {
                String result = m.group(1);
                LOG.info(String.format("Received result from server: %s", result));
                return new Double(result);
            }
            else {
                LOG.info(String.format("Received response from server: %s", response));
                return null;
            }
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    private void writeRequest(String request) {

        try {
            output.write(String.format("COMPUTE %s END\n", request));
            output.flush();
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
