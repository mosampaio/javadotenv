import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public final class Dotenv {

    private Dotenv() { }

    public static void load() {
        try {
            Properties prop = new Properties();
            prop.load(Dotenv.class.getClassLoader().getResourceAsStream(".env"));
            for (Map.Entry entry: prop.entrySet()) {
                if (System.getProperty(entry.getKey().toString()) == null) {
                    System.setProperty(entry.getKey().toString(), entry.getValue().toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
