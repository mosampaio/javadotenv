package com.mosampaio.javadotenv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
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

    /**
     * It load the env vars accordingly.
     */
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
                Map<String, String> map = new LinkedHashMap<>();
                map.putAll(System.getenv());
                for (Map.Entry entry : prop.entrySet()) {
                    if (map.get(entry.getKey().toString()) == null) {
                        map.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
                EnvironmentVariablesUtil.setEnv(map);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Builder {
        private String resource = ".env";
        private boolean silent = false;

        /**
         * Define the resource file which contains the env vars.
         * Default: .env
         * @param resource
         * @return com.mosampaio.javadotenv.Dotenv.Builder
         */
        public Builder resource(String resource) {
            this.resource = resource;
            return this;
        }

        /**
         * Shows a warning message if the file does not exists. Mark as true if you want to ignore this message.
         * Default: false
         * @param silent
         * @return com.mosampaio.javadotenv.Dotenv.Builder
         */
        public Builder silent(boolean silent) {
            this.silent = silent;
            return this;
        }

        /**
         * Return the com.mosampaio.javadotenv.Dotenv instance
         * @return com.mosampaio.javadotenv.Dotenv
         */
        public Dotenv build() {
            return new Dotenv(resource, silent);
        }

    }

}
