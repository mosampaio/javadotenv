import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DotenvTest {

    @Test
    public void getPropertyTestShouldReturn12345() {
        Dotenv dotenv = new Dotenv();
        dotenv.load();

        assertThat(System.getProperty("TEST"), is("12345"));
    }
}
