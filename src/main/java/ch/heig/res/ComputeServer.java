package ch.heig.res;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComputeServer {

    private final static Logger LOG = Logger.getLogger(ComputeServer.class.getName());
    private static final String CLIENT_REQUEST_FORMAT = "^COMPUTE (.*) END$";

    private int port;
    private String ip;

    public ComputeServer(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public void serveClients() {

        ServerSocket serverSocket;
        Socket clientSocket = null;

        BufferedReader input = null;
        BufferedWriter output = null;

        // Initialisation of serverSocket
        try {
            serverSocket = new ServerSocket(port, 10, InetAddress.getByName(ip));
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            return;
        }

        LOG.info("Server started...");
        while (true) {

            try {
                clientSocket = serverSocket.accept();

                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String request;
                boolean shouldRun = true;

                // Computing client requests
                while (shouldRun && (request = input.readLine()) != null) {

                    LOG.info(String.format("Received request from client: %s", request));
                    if (request.equalsIgnoreCase("COMPUTE END")) {
                        shouldRun = false;
                    }
                    else {
                        Pattern p = Pattern.compile(CLIENT_REQUEST_FORMAT);
                        Matcher m = p.matcher(request);

                        if (m.find()) {
                            //Expression math = new Expression(m.group(1));
                            //double result = math.calculate();

                            //LOG.info(String.format("Sending result to client: %f", result));
                            //output.write("RESULT " + result + " END\n");
                            output.flush();
                        }
                        else {
                            LOG.info(String.format("Received invalid request from client.", request));
                            output.write("INVALID REQUEST");
                            output.flush();
                        }
                    }
                }

                // Closing connection
                clientSocket.close();
                input.close();
                output.close();
            }
            catch (IOException e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
