package com.mitrais.cdc.atmsimulation.service;

import com.mitrais.cdc.atmsimulation.model.AccountInfo;
import com.mitrais.cdc.atmsimulation.model.ListAccount;

import javax.xml.bind.ValidationException;
import java.util.List;

public class AtmService {

    private ListAccount listAccount;

    public AtmService(ListAccount listAccount) {
        this.listAccount = listAccount;
    }

    public boolean authenticate(String accountNumber, String pinNumber) {
        final List<AccountInfo> accountInfoList = listAccount.getAccountInfoList();
        for (AccountInfo accountInfo : accountInfoList) {
            final boolean isAccountNumberExist = accountInfo.getAccountNumber().equals(accountNumber);
            if (isAccountNumberExist) {
                return accountInfo.getPin().equals(pinNumber);
            }
        }
        return false;
    }

    public long withdraw(String accountNumber, long amountTransaction) throws ValidationException {
        final List<AccountInfo> accountInfoList = listAccount.getAccountInfoList();
        final AccountInfo accountInfo = accountInfoList.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst().orElse(null);

        if (accountInfo != null) {
            if (amountTransaction > accountInfo.getBalance()) {
                throw new ValidationException("Insufficient balance $"+accountInfo.getBalance());
            }

            accountInfo.setBalance(accountInfo.getBalance() - amountTransaction);
            return accountInfo.getBalance();
        }
        return -1;
    }
}
