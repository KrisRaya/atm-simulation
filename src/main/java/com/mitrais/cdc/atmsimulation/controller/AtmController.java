package com.mitrais.cdc.atmsimulation.controller;

import com.mitrais.cdc.atmsimulation.service.AtmService;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AtmController {

    private AtmService atmService;

    public AtmController(AtmService atmService) {
        this.atmService = atmService;
    }

    public void welcomeScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Account Number: ");
        final String accountNumber = scanner.nextLine();

        if (accountNumber.length() == 6) {
            if (!accountNumber.matches("\\d+")) {
                System.out.println("Account Number should only contains numbers");
                return;
            }

            System.out.print("Enter PIN: ");
            final String pinNumber = scanner.nextLine();
            if (pinNumber.length() != 6) {
                System.out.println("PIN should have 6 digits length");
                return;
            }

            if (!pinNumber.matches("\\d+")) {
                System.out.println("PIN should only contains numbers");
            }

            final boolean authenticate = atmService.authenticate(accountNumber, pinNumber);
            if (!authenticate) {
                System.out.println("Invalid Account Number/PIN");
            } else transactionScreen(accountNumber);

        } else {
            System.out.println("Account Number should have 6 digits length");
        }

    }

    private void transactionScreen(String accountNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("1. Withdraw\n2. Fund Transfer\n3. Exit\nPlease choose option[3]: ");

        String input = scanner.nextLine();
        if (input.equals("")) {
            input = "3";
        }

        switch (input) {
            case "1":
                withdrawScreen(accountNumber);
                break;
            case "2":
                fundTransferScreen();
                break;
            case "3":
                welcomeScreen();
                break;
            default:
                transactionScreen(accountNumber);
        }
    }

    private void withdrawScreen(String accountNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("1. $10\n2. $50\n3. $100\n4. Other\n5. Back\nPlease choose option[5]: ");
        String input = scanner.nextLine();
        if (input.equals("")) {
            input = "5";
        }

        switch (input) {
            case "1": case "2": case "3":
                final long amount = convertInputToAmount(input);
                try {
                    final long balance = atmService.withdraw(accountNumber, amount);
                    withdrawSummaryScreen(accountNumber, amount, balance);
                } catch (ValidationException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "4":
                otherWithdrawScreen(accountNumber);
                break;
            default:
                transactionScreen(accountNumber);
        }
    }

    private void otherWithdrawScreen(String accountNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Other Withdraw\nEnter amount to withdraw: ");

        try {
            int amount = scanner.nextInt();
            if (amount > 1000) {
                System.out.println("Maximum amount to withdraw is $1000");
                return;
            }
            if (amount % 10 != 0) {
                System.out.println("Invalid amount");
                return;
            }
            try {
                final long balance = atmService.withdraw(accountNumber, amount);
                withdrawSummaryScreen(accountNumber, amount, balance);
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Invalid amount");
        }
    }

    private void withdrawSummaryScreen(String accountNumber, long amount, long balance) {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Summary");
        System.out.println("Date : " + LocalDate.now());
        System.out.println("Withdraw : $" + amount);
        System.out.println("Balance : $" + balance);
        System.out.print("1. Transaction\n2. Exit\nChoose option[2]: ");

        String input = scanner.nextLine();
        if ("1".equals(input)) {
            transactionScreen(accountNumber);
        } else {
            welcomeScreen();
        }
    }

    private void fundTransferScreen() {
        System.out.println();
    }

    private long convertInputToAmount(String input) {
        long amountTransaction;
        switch (input) {
            case "1":
                amountTransaction = 10;
                break;
            case "2":
                amountTransaction = 500;
                break;
            case "3":
                amountTransaction = 100;
                break;
            default:
                amountTransaction = Long.valueOf(input);
        }
        return amountTransaction;
    }
}
