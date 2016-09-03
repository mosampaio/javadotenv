import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DotenvTest {

    @Before
    public void setUp() {
        System.getProperties().remove("TEST");
    }

    @Test
    public void getPropertyTestShouldReturn12345() {
        new Dotenv.Builder().build().load();

        assertThat(System.getProperty("TEST"), is("12345"));
    }

    @Test
    public void getPropertyTestShouldReturn54321WhenPropertyHasBeenAlreadySet() {
        System.setProperty("TEST", "54321");

        new Dotenv.Builder().build().load();


        assertThat(System.getProperty("TEST"), is("54321"));
    }

    @Test
    public void getPropertyTestShouldReturn99999WhenItLoadADifferentResource() {
        new Dotenv.Builder().resource(".env.other").build().load();

        assertThat(System.getProperty("TEST"), is("99999"));
    }

    @Test
    public void shouldNotFailIfResourceIsNotFound() {
        new Dotenv.Builder().resource(".env.notexistent").build().load();
    }
}
