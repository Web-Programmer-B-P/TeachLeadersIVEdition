package my.teach;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

public class FalconTest {
    @Test
    public void whenCheckSpeedFly() {
        Falcon falcon = new Falcon("Сапсан", 322);
        int expectedMessage = 322;
        int actualMessage = falcon.getSpeedFly();
        assertThat(actualMessage, is(expectedMessage));
    }

    @Test
    public void whenEqualsTwoDifferedObjectFalse() {
        Falcon falcon1 = new Falcon("Сапсан", 322);
        Falcon falcon2 = new Falcon("Сапсан", 120);
        boolean actualMessage = falcon1.equals(falcon2);
        assertThat(actualMessage, is(false));
    }

    @Test
    public void whenEqualsTwoDifferedObjectTrue() {
        Falcon falcon1 = new Falcon("Сапсан", 322);
        Falcon falcon2 = new Falcon("Сапсан", 322);
        boolean actualMessage = falcon1.equals(falcon2);
        assertThat(actualMessage, is(true));
    }

    @Test
    public void whenDemonstrateToStringMethod() {
        Falcon falcon1 = new Falcon("Сапсан", 322);
        String expectedMessage = "I`m Сапсан and my speed fly is 322";
        String actualMessage = falcon1.toString();
        assertThat(actualMessage, is(expectedMessage));
    }
}