package ch.heig.res.app;

import ch.heig.res.app.util.Pranks;
import ch.heig.res.app.util.Victims;
import ch.heig.res.smtp.util.Expeditors;
import ch.heig.res.smtp.model.Mail;

import java.util.ArrayList;
import java.util.List;

public class PrankGenerator {

    private final String expeditorsFile;
    private final String victimsFile;
    private final String pranksFile;

    public PrankGenerator(String expeditorsFile, String victimsFile, String pranksFile) {
        this.expeditorsFile = expeditorsFile;
        this.victimsFile = victimsFile;
        this.pranksFile = pranksFile;
    }

    public List<Mail> generateMails() {

        List<Mail> mails = new ArrayList<>();
        Expeditors expeditors = new Expeditors(expeditorsFile);
        Pranks pranks = new Pranks(pranksFile);

        for (List<String> victimGroup : Victims.generateGroups(victimsFile)) {

            Mail mail = new Mail();
            mail.setExpeditor(expeditors.next());
            mail.setDestinators(victimGroup);
            mail.setMessage(pranks.random());
            mails.add(mail);
        }

        return mails;
    }
}
