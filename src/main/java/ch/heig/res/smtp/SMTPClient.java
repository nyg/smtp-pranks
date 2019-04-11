package ch.heig.res.smtp;

import ch.heig.res.smtp.model.Mail;
import ch.heig.res.tcp.TCPClient;

import java.util.List;
import java.util.logging.Logger;

public class SMTPClient extends TCPClient {

    private final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());

    private static final String EHLO = "EHLO";
    private static final String MAIL_FROM = "MAIL FROM: ";
    private static final String RCPT_TO = "RCPT TO: ";
    private static final String DATA = "DATA";
    private static final String END_DATA = "\r\n.\r\n";
    private static final String RSET = "RSET";
    private static final String QUIT = "QUIT";

    private static final String DATA_FROM = "From: <%s>";
    private static final String DATA_TO = "To: <%s>";
    private static final String DATA_SUBJECT = "Subject: <%s>";

    public SMTPClient(String serverIPAddress, int serverPort) {
        super(serverIPAddress, serverPort);
    }

    /**
     * Sends an email using the given  @{@link Mail} instance.
     */
    public void sendMail(Mail mail) {
        sendMailFrom(mail.getExpeditor());
        sendRcptTo(mail.getDestinators());
        sendData(mail);
    }

    /**
     * Sends EHLO.
     */
    public void sendEhlo() {
        openConnection();
        sendMessage(EHLO + " user");
        verifyLastResponse();
    }

    /**
     * Sends QUIT.
     */
    public void sendQuit() {
        sendMessage(QUIT);
    }

    /**
     * Sends RSET.
     */
    public void sendRset() {
        sendMessage(RSET);
    }

    /**
     * Sends MAIL FROM.
     */
    private void sendMailFrom(String expeditor) {
        sendMessage(MAIL_FROM + expeditor);
        verifyLastResponse();
    }

    /**
     * Sends RCPT_TO.
     */
    private void sendRcptTo(List<String> destinators) {

        for (String destinator : destinators) {
            sendMessage(RCPT_TO + destinator);
            verifyLastResponse();
        }
    }

    /**
     * Sends DATA followed by the email headers and body.
     */
    private void sendData(Mail mail) {
        sendMessage(DATA);
        sendMessage(String.format(DATA_FROM, mail.getExpeditor()));
        sendMessage(String.format(DATA_TO, String.join(">, <", mail.getDestinators())));
        sendMessage(String.format(DATA_SUBJECT, mail.getSubject()));
        sendMessage(mail.getMessage());
        sendMessage(END_DATA);
        verifyLastResponse();
    }

    /**
     * Reads server responses until a timeout occurs, then returns the last message received.
     */
    private String readLastResponse() {

        List<String> responses = readResponses();
        if (!responses.isEmpty()) {
            return responses.get(responses.size() - 1);
        }

        return null;
    }

    /**
     * Makes sure the last response from the server is "250 OK".
     */
    private void verifyLastResponse() {
        if (!"250 Ok".equalsIgnoreCase(readLastResponse())) {
            sendRset();
            sendQuit();
            throw new RuntimeException("Did not receive OK response from SMTP server.");
        }
    }
}
