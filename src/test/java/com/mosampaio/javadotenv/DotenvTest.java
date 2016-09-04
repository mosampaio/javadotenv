package com.mosampaio.javadotenv;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DotenvTest {

    @Before
    public void setUp() {
        EnvironmentVariablesUtil.setEnv(new HashMap<>());
    }

    @Test
    public void getPropertyTestShouldReturn12345() {
        new Dotenv.Builder().build().load();

        assertThat(System.getenv("TEST"), is("12345"));
    }

    @Test
    public void getPropertyTestShouldReturn54321WhenPropertyHasBeenAlreadySet() {
        EnvironmentVariablesUtil.setEnv(new HashMap<String, String>() {{
            put("TEST", "54321");
        }});

        new Dotenv.Builder().build().load();


        assertThat(System.getenv("TEST"), is("54321"));
    }

    @Test
    public void getPropertyTestShouldReturn99999WhenItLoadADifferentResource() {
        new Dotenv.Builder().resource(".env.other").build().load();

        assertThat(System.getenv("TEST"), is("99999"));
    }

    @Test
    public void shouldNotFailIfResourceIsNotFound() {
        new Dotenv.Builder().resource(".notexistent").build().load();
    }

    @Test
    public void shouldNotShowWarnIfResourceIsNotFoundAndSilentIsTrue() {
        new Dotenv.Builder().silent(true).resource(".nonexistent").build().load();
    }

    @Test
    public void shouldIgnoreCommentedLines() {
        new Dotenv.Builder().build().load();

        assertThat(System.getenv("BRA"), is(nullValue()));
    }
}
