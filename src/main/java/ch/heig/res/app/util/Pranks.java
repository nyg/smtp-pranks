package ch.heig.res.app.util;

import ch.heig.res.app.model.Prank;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pranks {

    public static final String PRANK_SEPARATOR = "===";
    private static final Logger LOG = Logger.getLogger(Pranks.class.getName());
    private String pranksFile;
    private BufferedReader reader;

    public Pranks(String pranksFile) {
        this.pranksFile = pranksFile;
        openFile();
    }

    /**
     * Returns next prank. If end of file is reached, we start again from the
     * beginning.
     */
    public Prank next() {

        try {
            Prank prank = new Prank();
            String line = reader.readLine();

            while (line != null && !line.equals(PRANK_SEPARATOR)) {
                prank.add(line);
                line = reader.readLine();
            }

            // we have reached EOF
            if (line == null) {
                openFile();
            }

            return prank;
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void openFile() {

        try {
            if (reader != null) {
                reader.close();
            }

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(pranksFile)));
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
