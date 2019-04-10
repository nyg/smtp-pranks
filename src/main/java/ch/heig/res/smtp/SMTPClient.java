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
        output.write(mailFrom, 0, mailFrom.length());

        // Checking server answer
        if (readMessage() != "250 OK"){
            abortConnection();
        }

        // sending mail's target
        output.write(rcptTo, 0, rcptTo.length());

        if (readMessage() != "250 OK"){
            abortConnection();
        }

        // Preparing to send mail's body
        output.write(DATA, 0, DATA.length());

        if (readMessage() != "250 OK"){
            abortConnection();
        }

        // sending mail's message
        output.write(data, 0, data.length());

        // Ending message
        output.write(endData, 0, endData.length());

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
