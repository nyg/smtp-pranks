package ch.heig.res.app.util;

import ch.heig.res.smtp.util.Expeditors;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Pranks {

    private static final Logger LOG = Logger.getLogger(Pranks.class.getName());

    private List<String> pranks;
    private int position;

    /**
     * Reads file and stores its content into memory.
     *
     * @param pranksFile the file to parse
     */
    public Pranks(String pranksFile) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pranksFile)))) {
            pranks = reader.lines().collect(Collectors.toList());
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String random() {
        ThreadLocalRandom.current().nextInt(0, pranks.size());
        return pranks.get(position++ % pranks.size());
    }
}
