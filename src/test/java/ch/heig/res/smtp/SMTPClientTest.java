package ch.heig.res.smtp;

import ch.heig.res.smtp.model.Mail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;
import java.util.Arrays;

@Disabled
public class SMTPClientTest {

    private static final String serverIP = "127.0.0.1";
    private static final int serverPort = 2525;

    @Test
    public void clientShouldGreetTheServer() throws UnknownHostException {
        SMTPClient client = new SMTPClient(serverIP, serverPort);
        client.sendEhlo();
    }

    @Test
    public void clientShouldGreetTheServerAndQuit() throws UnknownHostException {
        SMTPClient client = new SMTPClient(serverIP, serverPort);
        client.sendEhlo();
        client.sendQuit();
    }

    @Test
    public void clientShouldSendMailToServer() {

        Mail mail = new Mail();
        mail.setExpeditor("prank@mail.com");
        mail.setDestinators(Arrays.asList("victim@mail.com", "target@mail.com, someone@somewhere.com"));
        mail.setSubject("Hello friends!");
        mail.setMessage("Hello there, this is my email.");

        SMTPClient client = new SMTPClient(serverIP, serverPort);
        client.sendEhlo();
        client.sendMail(mail);
        client.sendQuit();
    }
}
