/*
 * Software Architectures and Solutions d.o.o. Novi Sad. Copyright 2015-2020.
 */

package com.tictactoe.game.web;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This <code>BaseControllerTest</code> class is the parent of all web controller unit test classes. The class ensures
 * that a type of application context is built and prepares a MockMvc instance and a object mapper instance for use in
 * test methods.
 *
 * @author Bosko Mijin
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BaseControllerTest {

    /** A Jackson ObjectMapper for JSON conversion. */
    @Autowired
    protected ObjectMapper mapper;

    /** The application context. */
    @Autowired
    private ApplicationContext applicationContext;

    /** The mock mvc. */
    @Autowired
    protected MockMvc mockMvc;

    /**
     * The <code>printApplicationContext</code> method performs setup activities and prints the application context
     * before each test.
     */
    @BeforeEach
    public void printApplicationContext() {
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(name -> applicationContext.getBean(name).getClass().getName()).sorted()
                .forEach(System.out::println);
    }
}
