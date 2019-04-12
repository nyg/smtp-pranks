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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Victims {

    private static final Logger LOG = Logger.getLogger(Victims.class.getName());

    /**
     * Reads given file and creates random groups of victims. Each group
     * consists of at least three victims (one of which will be the sender).
     *
     * @param victimsFile the file to parse
     * @return a list of list of emails
     */
    public static List<List<String>> generateGroups(String victimsFile, Integer groupCount) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(victimsFile)))) {

            List<List<String>> groups = Stream.generate(ArrayList<String>::new).limit(groupCount).collect(Collectors.toList());
            List<Integer> groupIndexes = fillGroupIndexes(groupCount);

            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {

                if (groupIndexes.isEmpty()) {
                    groupIndexes = fillGroupIndexes(groupCount);
                }

                // a way of generating random groups
                int groupIndex = ThreadLocalRandom.current().nextInt(groupIndexes.size());
                groups.get(groupIndexes.remove(groupIndex)).add(line);

                lineCount++;
            }

            // ensure all groups are of at least three victims.
            if (lineCount / groupCount < 3) {
                throw new IllegalArgumentException("Groups created are too small. Too many groups specified or not enough emails provided.");
            }

            return groups;
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static List<Integer> fillGroupIndexes(Integer groupCount) {
        return IntStream.range(0, groupCount).boxed().collect(Collectors.toList());
    }
}
