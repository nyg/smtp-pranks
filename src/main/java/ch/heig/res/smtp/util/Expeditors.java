package ch.heig.res.smtp.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Expeditors {

    private static final Logger LOG = Logger.getLogger(Expeditors.class.getName());

    private List<String> expeditors;
    private int position;

    /**
     * Reads file and stores its content into memory.
     *
     * @param expeditorsFile the file to parse
     */
    public Expeditors(String expeditorsFile) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(expeditorsFile)))) {
            expeditors = reader.lines().collect(Collectors.toList());
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String next() {
        return expeditors.get(position++ % expeditors.size());
    }
}
