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
    public void aServerShouldComputeAnExpression() {

        ComputeClient client = new ComputeClient(serverPort, serverIP);
        client.startConnection();

        Double result = client.sendComputation("1 + 3 * (5 - 2)");
        Assertions.assertEquals(10, result);

        client.endConnection();
    }

    @Test
    public void aServerShouldComputeMultipleExpressions() {

        ComputeClient client = new ComputeClient(serverPort, serverIP);
        client.startConnection();

        Double result = client.sendComputation("1 + 3 * (5 - 2)");
        Assertions.assertEquals(10, result);

        result = client.sendComputation("2 + 3 * (5 - 2)");
        Assertions.assertEquals(11, result);

        client.endConnection();
    }

    private static class ServerRunnable implements Runnable {

        @Override public void run() {
            ComputeServer server = new ComputeServer(serverPort, serverIP);
            server.serveClients();
        }
    }
}
