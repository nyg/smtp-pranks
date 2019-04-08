package ch.heig.res;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ComputeServerTest {

    private static final String serverIP = null; // "127.0.0.1";
    private static final int serverPort = 6789;

    @BeforeAll
    public static void startServerThread() {
        new Thread(new ServerRunnable()).start();
    }
    

    @Test
    public void theServerShouldCarryASimpleTransaction(){
        ComputeClient client = new ComputeClient(serverPort, serverIP);
        client.startConnection();

        //TODO Simple transaction

        client.endConnection();
    }

    @Test
    public void theServerShouldAbortATransaction(){
        ComputeClient client = new ComputeClient(serverPort, serverIP);
        client.startConnection();

        //TODO Abort transaction

        client.endConnection();

    }

    private static class ServerRunnable implements Runnable {

        @Override public void run() {
            ComputeServer server = new ComputeServer(serverPort, serverIP);
            server.serveClients();
        }
    }
}
