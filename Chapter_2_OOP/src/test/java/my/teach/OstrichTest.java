package my.teach;

import org.junit.Test;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

public class OstrichTest {
    @Test
    public void whenCheckHowMuchToEatPerDay() {
        Ostrich ostrich = new Ostrich("Pet", 70, 120);
        int expectedMessage = 12;
        int actualMessage = ostrich.getInfoHowMuchToEatPerDay();
        assertThat(actualMessage, is(expectedMessage));
    }

    @Test
    public void whenEqualsTwoDifferedObjectTrue() {
        Ostrich ostrich1 = new Ostrich("Pet", 70, 120);
        Ostrich ostrich2 = new Ostrich("Pet", 50, 110);
        boolean actualMessage = ostrich1.equals(ostrich2);
        assertThat(actualMessage, is(false));
    }

    @Test
    public void whenDemonstrateToStringMethod() {
        Ostrich ostrich = new Ostrich("Pet", 70, 120);
        String expectedMessage = "Ostrich is a very big bird, and it run 70 km, and it weight = 120";
        String actualMessage = ostrich.toString();
        assertThat(actualMessage, is(expectedMessage));
    }
}