package ch.heig.res.smtp;

import ch.heig.res.tcp.TCPClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SMTPClient extends TCPClient {

    private final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());
    private InetAddress localHost;
    private InetAddress mailHost;

    private static final String QUIT = "QUIT";
    private static final String EHLO = "EHLO";
    private static final String RST = "RST";
    private static final String DATA = "DATA";
    private static final String endData = "\n.\n";

    public SMTPClient(String host, int port, String ip) throws UnknownHostException {
        super(port, ip);

        mailHost =InetAddress.getByName(host);
        localHost = InetAddress.getLocalHost();
    }

    public String smtpGreetings(){
        sendMessage(EHLO);

        return readMessage();
    }


    public void sendMail(String mail_from, String rcpt, String data)
            throws IOException {

        String mailFrom = "MAIL FROM:<" + mail_from + ">";
        String rcptTo = "RCPT TO:<" + rcpt + ">";

        // sending mail's sender
        sendMessage(mailFrom);

        // Checking server answer
        if (readMessage() != "250 OK"){
            abortConnection();
        }

        // sending mail's target
        sendMessage(rcptTo);

        if (readMessage() != "250 OK"){
            abortConnection();
        }

        // Preparing to send mail's body
        sendMessage(DATA);

        if (readMessage() != "250 OK"){
            abortConnection();
        }

        // sending mail's message
        sendMessage(data);

        // Ending message
        sendMessage(endData);

        // Getting server's answer
        readMessage();

    }

    public void abortConnection() {
        try {
            output.write(RST, 0, RST.length());
            output.flush();

            // Read the server's answer
            input.lines();

            output.write(QUIT, 0, QUIT.length());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
