package ch.heig.res.app.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class VictimsTest {

    @Test
    public void victimsShouldCreateGroups() {

        List<List<String>> groups = Victims.generateGroups("src/test/resources/victims.txt");
        Assertions.assertTrue(groups.size() != 0);

        for (List<String> group : groups) {
            Assertions.assertTrue(group.size() != 0);
            // TODO check max size is respected
        }
    }
}
