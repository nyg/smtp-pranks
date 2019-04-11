package ch.heig.res.app.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class VictimsTest {

    private static final int VICTIM_COUNT = 50; // from victims.txt
    private static final int GROUP_COUNT = 5;
    private static final int GROUP_SIZE = VICTIM_COUNT / GROUP_COUNT;

    @Test
    public void victimsShouldCreateGroups() {

        List<List<String>> groups = Victims.generateGroups("src/test/resources/victims.txt", GROUP_COUNT);
        Assertions.assertEquals(GROUP_COUNT, groups.size());

        // we may have smaller groups
        groups.forEach(group -> Assertions.assertTrue(group.size() <= GROUP_SIZE));
    }
}
