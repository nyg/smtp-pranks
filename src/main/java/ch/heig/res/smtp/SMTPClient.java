package ch.heig.res.smtp;

import ch.heig.res.tcp.TCPClient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SMTPClient extends TCPClient {

    private final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());

    private static final String QUIT = "QUIT";
    private static final String EHLO = "EHLO";
    private static final String RST = "RST";

    public SMTPClient(int port, String ip) {
        super(port, ip);
    }


    public void sendMail(String mail_from, String rcpt, String data){

    }

    public void abortConnection() {
        try {
            output.write(RST, 0, RST.length());
            output.flush();

            // Read the server answer and discard it ?
            input.lines();

            output.write(QUIT, 0, QUIT.length());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
