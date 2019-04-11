package ch.heig.res.smtp;

import ch.heig.res.smtp.SMTPClient;
import ch.heig.res.smtp.model.Mail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Disabled
public class SMTPClientTest {

    private static final String serverIP = "10.192.93.20"; // "127.0.0.1";
    private static final int serverPort = 2525;


    @Test
    public void theClientShouldCarryASimpleTransaction() throws UnknownHostException, IOException{

        List<String> victims = Arrays.asList("victim@mail.com", "target@mail.com, someone@somewhere.com");

        Mail mail = new Mail();
        mail.setExpeditor("prank@mail.com");
        mail.setDestinators(victims);
        mail.setMessage("Hello there.");

        SMTPClient client = new SMTPClient(serverPort, serverIP);
        client.startConnection();

        client.smtpGreetings();

        //TODO Simple transaction
        client.sendMail(mail);

        client.endConnection();
    }

    @Test
    public void theClientShouldAbortATransaction() throws UnknownHostException, IOException{
        String mail_from = "test@mail.com";
        String rcpt_to = "mail.com"; // Wrong format to trigger an error
        String data = "Oups";

        Mail mail = new Mail();

            SMTPClient client = new SMTPClient(serverPort, serverIP);
            client.startConnection();

            //TODO Abort transaction
            client.sendMail(mail);

            client.endConnection();
    }

    @Test
    public void theClientShouldGreetTheServer() throws UnknownHostException{
        String answer;

        SMTPClient client = new SMTPClient(serverPort, serverIP);

        client.startConnection();

        // Client greets server
        answer = client.smtpGreetings();

        Assertions.assertEquals("250-" + serverIP + "greets %s", answer);

    }
}
