package ch.heig.res.tcp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TCPClientTest {

    // tcpbin.org – TCP server which will echo back the sent message
    private static final String serverIP = "52.20.16.20";
    private static final int serverPort = 30000;

    @Test
    public void tcpClientShouldConnectToServer() {
        TCPClient client = new TCPClient(serverPort, serverIP);
        client.startConnection();
    }

    @Test
    public void tcpClientShouldSendMessageToServer() throws InterruptedException {

        TCPClient client = new TCPClient(serverPort, serverIP);
        client.startConnection();

        String message = "Hello server!";
        client.sendMessage(message);

        // Wait for response
        Thread.sleep(1000);
        String response = client.readMessage();

        Assertions.assertEquals(message, response);
    }
}
