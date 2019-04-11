package ch.heig.res.tcp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.SocketException;
import java.util.List;

class TCPClientTest {

    // tcpbin.org – TCP server which will echo back the sent message
    private static final String serverIP = "52.20.16.20";
    private static final int serverPort = 30000;

    @Test
    public void tcpClientShouldConnectToServer() {
        TCPClient client = new TCPClient(serverIP, serverPort);
        client.openConnection();
    }

    @Test
    public void tcpClientShouldSendMessageToServerAndReceiveResponse() throws InterruptedException, SocketException {

        TCPClient client = new TCPClient(serverIP, serverPort);
        client.openConnection();

        String message = "Hello server!";
        client.sendMessage(message);

        List<String> responses = client.readResponses();
        Assertions.assertEquals(1, responses.size());
        Assertions.assertEquals(message, responses.get(0));
    }

    @Test
    public void tcpClientShouldCloseConnectionWithServer() {
        TCPClient client = new TCPClient(serverIP, serverPort);
        client.openConnection();
        client.closeConnection();
    }
}
