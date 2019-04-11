package ch.heig.res.app;

import ch.heig.res.smtp.SMTPClient;
import ch.heig.res.smtp.model.Mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {

        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");

        File appPropertiesFile = null;
        String appPropertiesLocation = System.getProperty("app.properties.location");

        // get application.properties file
        // - first look in system property app.properties.location
        // - then in ./application.properties
        // - then use default one
        if (appPropertiesLocation == null) {

            LOG.info("System property app.properties.location not specified.");
            appPropertiesFile = new File("application.properties");

            if (appPropertiesFile.exists() && appPropertiesFile.canRead()) {
                LOG.info("Using application.properties found in current directory.");
            }
            else {
                LOG.info("No user-defined application.properties found, using default.");
                appPropertiesFile = new File("src/main/resources/application.properties");
            }
        }
        else {
            appPropertiesFile = new File(appPropertiesLocation);
        }

        // parse application.properties file
        Properties appProperties = new Properties();
        appProperties.load(new FileInputStream(appPropertiesFile));

        String serverIP = appProperties.getProperty("app.server.ip");
        Integer serverPort = Integer.parseInt(appProperties.getProperty("app.server.port"));
        String pranksFile = appProperties.getProperty("app.file.pranks.location");
        String victimsFile = appProperties.getProperty("app.file.victims.location");
        Integer groupCount = Integer.parseInt(appProperties.getProperty("app.victims.groupCount"));

        // initializing PrankGenerator
        PrankGenerator prankGenerator = new PrankGenerator(victimsFile, pranksFile, groupCount);
        List<Mail> mails = prankGenerator.generateMails();

        // display property values to user
        LOG.info("Application will use the following property values:");
        Iterator<String> names = appProperties.stringPropertyNames().iterator();
        while (names.hasNext()) {
            String name = names.next();
            LOG.info(String.format(" - %s = %s", name, appProperties.getProperty(name)));
        }

        // launching PrankGenerator if user is okay
        System.out.print("\nLaunch prank campaign? (yes/no) ");
        String userInput = System.console().readLine();
        System.out.println("");

        if (userInput.matches("y(es)?")) {
            LOG.info("Launching campaign!");
            SMTPClient smtpClient = new SMTPClient(serverIP, serverPort);
            smtpClient.sendEhlo();
            mails.forEach(smtpClient::sendMail);
            smtpClient.sendQuit();
            LOG.info("Done.");
        }
        else {
            LOG.info("Aborting...");
        }
    }
}
