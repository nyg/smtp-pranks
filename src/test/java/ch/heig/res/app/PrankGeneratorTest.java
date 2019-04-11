package ch.heig.res.app;

import ch.heig.res.smtp.model.Mail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PrankGeneratorTest {

    private static final int VICTIM_COUNT = 50; // from victims.txt
    private static final int GROUP_COUNT = 5;
    private static final int GROUP_SIZE = VICTIM_COUNT / GROUP_COUNT;

    @Test
    public void prankGeneratorShouldGenerateMails() {

        PrankGenerator prankGenerator = new PrankGenerator("src/test/resources/victims.txt", "src/test/resources/pranks.txt", GROUP_COUNT);
        List<Mail> mails = prankGenerator.generateMails();

        Assertions.assertEquals(GROUP_COUNT, mails.size());

        mails.forEach(mail -> {
            Assertions.assertTrue(mail.getExpeditor() != null);
            Assertions.assertTrue(mail.getMessage().length() != 0);

            // some groups may be smaller, and -1 because we have removed the expeditor
            Assertions.assertTrue(mail.getDestinators().size() <= GROUP_SIZE - 1);
        });
    }
}
