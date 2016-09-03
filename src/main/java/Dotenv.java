import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public final class Dotenv {

    private static Logger LOGGER = LoggerFactory.getLogger(Dotenv.class);

    private String resource;
    private boolean silent;

    private Dotenv(String resource, boolean silent) {
        this.resource = resource;
        this.silent = silent;
    }

    public void load() {
        try {
            Properties prop = new Properties();
            InputStream inputStream = Dotenv.class.getClassLoader().getResourceAsStream(resource);
            if (inputStream == null) {
                if (!silent) {
                    LOGGER.warn("It was not possible to load properties from \"{}\" - resource not found.", resource);
                }
            } else {
                prop.load(inputStream);
                for (Map.Entry entry : prop.entrySet()) {
                    if (System.getProperty(entry.getKey().toString()) == null) {
                        System.setProperty(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Builder {
        private String resource = ".env";
        private boolean silent = false;

        public Builder resource(String resource) {
            this.resource = resource;
            return this;
        }

        public Builder silent(boolean silent) {
            this.silent = silent;
            return this;
        }

        public Dotenv build() {
            return new Dotenv(resource, silent);
        }

    }

}
