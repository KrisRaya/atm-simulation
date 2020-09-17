package com.mitrais.cdc.atmsimulation.service;

import com.mitrais.cdc.atmsimulation.model.AccountInfo;
import com.mitrais.cdc.atmsimulation.model.ListAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AtmServiceTest {

    @InjectMocks
    private AtmService atmService;

    @Mock
    private ListAccount listAccount;

    @Before
    public void setUp() {
        final List<AccountInfo> accountInfos = new ArrayList<>(Arrays.asList(
                new AccountInfo("John Doe", "112233", 100, "012108"),
                new AccountInfo("Jane Doe", "112244", 30, "932012")));

        when(listAccount.getAccountInfoList()).thenReturn(accountInfos);
    }

    @Test
    public void shouldReturnFalseIfAccountNumberIsInvalid() {
        final boolean authenticate = atmService.authenticate("111222", "112233");
        assertFalse(authenticate);
    }

    @Test
    public void shouldReturnFalseIfAccountNumberIsValidAndPinNumberInvalid() {
        final boolean authenticate = atmService.authenticate("012108", "112244");
        assertFalse(authenticate);
    }

    @Test
    public void shouldReturnTrueIfAccountNumberIsValidAndPinNumberIsValid() {
        final boolean authenticate = atmService.authenticate("012108", "112233");
        assertTrue(authenticate);
    }

    @Test
    public void shouldReturnSuccessToDeductBalance() throws ValidationException {
        atmService.withdraw("012108", 25);
        assertEquals(75, listAccount.getAccountInfoList().get(0).getBalance());
    }

    @Test
    public void shouldThrowsExceptionWhenInsufficientBalance() {
        try {
            atmService.withdraw("012108", 125);
        } catch (ValidationException e) {
            assertEquals("Insufficient balance $100", e.getMessage());
        }
    }
}