package ch.heig.res.app.util;

import ch.heig.res.app.model.Prank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PranksTest {

    private static final int PRANK_COUNT = 15; // from pranks.txt

    private static final String SUBJECT = "Check this out";
    private static final String MESSAGE = "In the middle of a quiet classroom, just say 'LEEDLE LEEDLE LEEDLE LE!'";

    @Test
    public void pranksShouldReturnNextPrank() {
        Pranks pranks = new Pranks("src/test/resources/pranks.txt");
        Prank next = pranks.next();
        Assertions.assertEquals(SUBJECT, next.getSubject());
        Assertions.assertEquals(MESSAGE, next.getMessage());
    }

    @Test
    public void pranksShouldReturnMorePranksThanInPranksFile() {
        Pranks pranks = new Pranks("src/test/resources/pranks.txt");
        for (int i = 0; i < 3 * PRANK_COUNT; i++) {
            Prank next = pranks.next();
            Assertions.assertTrue(next.getSubject().length() != 0);
            Assertions.assertTrue(next.getMessage().length() != 0);
        }
    }
}
