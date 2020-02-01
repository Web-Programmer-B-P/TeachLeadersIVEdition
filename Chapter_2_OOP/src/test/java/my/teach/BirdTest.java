package my.teach;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

public class BirdTest {
    private Bird parentBird;

    @Before
    public void setUp() throws Exception {
        parentBird = new Bird("Ястреб");
    }

    @Test
    public void whenCheckBirdName() {
        String expectedMessage = "Ястреб";
        String actualMessage = parentBird.getName();
        assertThat(actualMessage, is(expectedMessage));
    }

    @Test
    public void whenCheckBirdFunctionFly() {
        String expectedMessage = "Bird`s name is Ястреб and it`s flying";
        String actualMessage = parentBird.fly();
        assertThat(actualMessage, is(expectedMessage));
    }
}