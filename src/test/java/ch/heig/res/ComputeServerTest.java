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
        String mail_from = "testMail@mail.com";
        String rcpt_to = "victim@mail.com";
        String data = "Hello there.";

        ComputeClient client = new ComputeClient(serverPort, serverIP);
        client.startConnection();

        //TODO Simple transaction
        client.sendMail(mail_from, rcpt_to, data);

        client.endConnection();
    }

    @Test
    public void theServerShouldAbortATransaction(){
        String mail_from = "test@mail.com";
        String rcpt_to = "mail.com"; // Wrong format to trigger an error
        String data = "Oups";

        ComputeClient client = new ComputeClient(serverPort, serverIP);
        client.startConnection();

        //TODO Abort transaction
        client.sendMail(mail_from, rcpt_to, data);

        client.endConnection();

    }

    private static class ServerRunnable implements Runnable {

        @Override public void run() {
            ComputeServer server = new ComputeServer(serverPort, serverIP);
            server.serveClients();
        }
    }
}
