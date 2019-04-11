package ch.heig.res.smtp;

import ch.heig.res.tcp.TCPClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Logger;


public class SMTPClient extends TCPClient {

    private final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());

    private static final String QUIT = "QUIT";
    private static final String EHLO = "EHLO";
    private static final String RST = "RST";
    private static final String DATA = "DATA";
    private static final String endData = "\r\n.\r\n";

    public SMTPClient(int port, String ip) throws UnknownHostException {
        super(port, ip);
    }

    public String smtpGreetings(){
        sendMessage(EHLO + " user");

        return null;
    }


    /**
     *
     * @param mail_from sender
     * @param rcpt receiver
     * @param data message
     * @throws IOException
     */
    public void sendMail(String mail_from, String rcpt, String data)
            throws IOException {

        String mailFrom = "MAIL FROM: " + mail_from ;
        String rcptTo = "RCPT TO:<" + rcpt + ">";

        // sending mail's sender
        sendMessage(mailFrom);

        // Checking server answer
/*
        if (readMessage() != "250 OK"){
            abortConnection();
        }
*/

        // sending mail's target
        sendMessage(rcptTo);

/*
        if (readMessage() != "250 OK"){
            abortConnection();
        }
*/

        // Preparing to send mail's body
        sendMessage(DATA);

/*
        if (readMessage() != "250 OK"){
            abortConnection();
        }
*/

        // sending mail's message
        sendMessage(data);

        // Ending message
        sendMessage(endData, true);

        // Getting server's answer
        readMessage();

    }

    /**
     * Abort a smtp connection
     */
    public void abortConnection() {
            sendMessage(RST);

            // Read the server's answer
            readMessage();

            sendMessage(QUIT);
    }
}
