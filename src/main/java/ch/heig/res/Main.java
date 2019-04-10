package ch.heig.res;

import java.io.Console;
import java.io.IOError;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

       /* if (args.length != 3) {
            System.out.println("Usage: compute-server [client|server] <ip address> <port>");
            System.exit(1);
        }

        final String serverIP = args[1];
        final int serverPort = Integer.parseInt(args[2]);

        if (args[0].equals("server")) {
            SMTPClient server = new SMTPClient(serverPort, serverIP);
            server.serveClients();
        }
        else if (args[0].equals("client")) {

            TCPClient client = new TCPClient(serverPort, serverIP);
            client.startConnection();

            Console console = System.console();
            String line;
            try {
                do {
                    System.out.print(" > ");
                    line = console.readLine();
                    client.sendComputation(line);
                }
                while (!line.equals("exit"));
            }
            catch (IOError e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
            }

            client.endConnection();
        }
        else {
            System.out.println("Usage: compute-server [client|server] <ip address> <port>");
            System.exit(1);
        }*/
    }
}
