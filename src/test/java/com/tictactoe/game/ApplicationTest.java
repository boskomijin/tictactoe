package com.tictactoe.game;

import org.junit.jupiter.api.Test;

/**
 * The <code>ApplicationTest</code> class contains all the JUnit tests for the entry point of application.
 *
 * @author Bosko Mijin
 */
class ApplicationTest {

    /**
     * Testing if application loads with some array of arguments. This test ensures that the application is able to run
     * correctly.
     */
    @Test
    public void applicationContextTest() {
        Application.main(new String[] { "--server.port=10084" });
    }

    /**
     * Testing if application loads context. This test ensures that the application is able to load context correctly.
     */
    @Test
    public void contextLoads() {
        // There is no need to add any action, its just a context checker.
    }
}
