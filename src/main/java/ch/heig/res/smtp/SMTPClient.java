package ch.heig.res.smtp;

import ch.heig.res.smtp.model.Mail;
import ch.heig.res.tcp.TCPClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
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
     * @param mail mail to send
     * @throws IOException
     */
    public void sendMail(Mail mail)
            throws IOException {

        String mailFrom = "MAIL FROM: " + mail.getExpeditor();
        String rcptTo;

        // sending mail's sender
        sendMessage(mailFrom);

        // Checking server answer
/*
        if (readMessage() != "250 OK"){
            abortConnection();
        }
*/

        for(String dest : mail.getDestinators()){
            rcptTo = "RCPT TO: " + dest;

            // sending mail's target
            sendMessage(rcptTo);
        }


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
        sendMessage(mail.getMessage());

        // Ending message
        sendMessage(endData, true);

        // Getting server's answer
        readMessage();

    }

    /**
     * Make the mail header before sending it
     * @param mail mail to complete
     */
    public void makeHeader(Mail mail){
        //TODO add From, To, Subject to mail data
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
