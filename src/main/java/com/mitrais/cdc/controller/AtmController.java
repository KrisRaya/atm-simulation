package com.mitrais.cdc.controller;

import com.mitrais.cdc.service.AtmService;

import java.util.Scanner;
import java.util.regex.Pattern;

public class AtmController {

    private AtmService atmService;

    public AtmController(AtmService atmService) {
        this.atmService = atmService;
    }

    public void welcomeScreen() {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("\\d+");
        System.out.print("Enter Account Number: ");
        final String accountNumber = scanner.nextLine();

        if (accountNumber.length() == 6) {
            if (!pattern.matcher(accountNumber).matches()) {
                System.out.println("Account Number should only contains numbers");
                return;
            }

            System.out.print("Enter PIN: ");
            final String pinNumber = scanner.nextLine();
            if (pinNumber.length() != 6) {
                System.out.println("PIN should have 6 digits length");
                return;
            }

            if (!pattern.matcher(pinNumber).matches()) {
                System.out.println("PIN should only contains numbers");
            }

            final boolean authenticate = atmService.authenticate(accountNumber, pinNumber);
            if (!authenticate) {
                System.out.println("Invalid Account Number/PIN");
            } else {
                System.out.println("OK");
            }

        } else {
            System.out.println("Account Number should have 6 digits length");
        }

    }
}
