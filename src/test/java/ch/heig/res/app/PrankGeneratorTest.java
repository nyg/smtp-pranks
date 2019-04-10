package ch.heig.res.app;

import ch.heig.res.smtp.model.Mail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PrankGeneratorTest {

    @Test
    public void prankGeneratorShouldGenerateMails() {

        PrankGenerator prankGenerator = new PrankGenerator("src/test/resources/expeditors.txt", "src/test/resources/victims.txt", "src/test/resources/pranks.txt");
        List<Mail> mails = prankGenerator.generateMails();

        Assertions.assertTrue(mails.size() != 0);

        mails.forEach(mail -> {
            Assertions.assertTrue(mail.getExpeditor() != null);
            Assertions.assertTrue(mail.getDestinators().size() != 0);
            Assertions.assertTrue(mail.getMessage().length() != 0);
        });
    }
}
