package ch.heig.res.app;

import ch.heig.res.app.model.Prank;
import ch.heig.res.app.util.Pranks;
import ch.heig.res.app.util.Victims;
import ch.heig.res.smtp.model.Mail;

import java.util.ArrayList;
import java.util.List;

public class PrankGenerator {

    private final String victimsFile;
    private final String pranksFile;
    private Integer groupCount;

    public PrankGenerator(String victimsFile, String pranksFile, Integer groupCount) {
        this.victimsFile = victimsFile;
        this.pranksFile = pranksFile;
        this.groupCount = groupCount;
    }

    public List<Mail> generateMails() {

        List<Mail> mails = new ArrayList<>();
        Pranks pranks = new Pranks(pranksFile);

        for (List<String> victimGroup : Victims.generateGroups(victimsFile, groupCount)) {

            Mail mail = new Mail();

            // expeditor is first victim of the group
            mail.setExpeditor(victimGroup.remove(0));
            mail.setDestinators(victimGroup);

            Prank prank = pranks.next();
            mail.setSubject(prank.getSubject());
            mail.setMessage(prank.getMessage());
            mails.add(mail);
        }

        return mails;
    }
}
