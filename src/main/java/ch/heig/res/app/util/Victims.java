package ch.heig.res.app.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Victims {

    private static final Logger LOG = Logger.getLogger(Victims.class.getName());

    /**
     * Reads given file and creates random groups of victims. Each group consists of up to 6 victims.
     *
     * @param victimsFile the file to parse
     * @return a list of list of emails
     */
    public static List<List<String>> generateGroups(String victimsFile) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(victimsFile)));
            List<List<String>> groups = new ArrayList<>();

            String line;
            int groupCount = getRandomGroupCount();
            List<String> currentGroup = new ArrayList<>();

            while ((line = reader.readLine()) != null) {

                if (currentGroup.size() == groupCount) {
                    groups.add(currentGroup);
                    currentGroup = new ArrayList<>();
                    groupCount = getRandomGroupCount();
                }

                currentGroup.add(line);
            }

            if (currentGroup.size() != 0) {
                groups.add(currentGroup);
            }

            reader.close();
            return groups;
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static int getRandomGroupCount() {
        return ThreadLocalRandom.current().nextInt(4, 7);
    }
}
