
package com.tictactoe.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot main application class. Serves as both the runtime application entry point and the central Java
 * configuration class.
 *
 * @author Bosko Mijin
 */
@SpringBootApplication
public class Application {
    /**
     * Entry point for the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
