package ch.heig.res.smtp.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExpeditorsTest {

    @Test
    public void expeditorsShouldReturnNextExpeditor() {
        Expeditors expeditors = new Expeditors("src/test/resources/expeditors.txt");
        Assertions.assertEquals(expeditors.next(), "imightb@outlook.com");
    }
}
