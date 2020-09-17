package com.mitrais.cdc.atmsimulation.controller;

import com.mitrais.cdc.atmsimulation.service.AtmService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AtmControllerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @InjectMocks
    private AtmController atmController;

    @Mock
    private AtmService atmService;


    @Before
    public void setUp() {
        atmController = new AtmController(atmService);
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void shouldReturnInvalidAccountNumberWhenAccountNumberIsNotEqualSix() {
        InputStream in = new ByteArrayInputStream("11223".getBytes());
        System.setIn(in);
        atmController.welcomeScreen();
        assertTrue(outContent.toString().contains("Account Number should have 6 digits length"));
    }

    @Test
    public void shouldReturnInvalidAccountNumberWhenAccountNumberIsNotNumber() {
        InputStream in = new ByteArrayInputStream("11223a".getBytes());
        System.setIn(in);
        atmController.welcomeScreen();
        assertTrue(outContent.toString().contains("Account Number should only contains numbers"));
    }

}