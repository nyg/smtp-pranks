package ch.heig.res.app.util;

import ch.heig.res.smtp.util.Expeditors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PranksTest {

    @Test
    public void pranksShouldReturnRandomPrank() {
        Pranks pranks = new Pranks("src/test/resources/pranks.txt");
        Assertions.assertTrue(pranks.random().length() != 0);
    }
}
