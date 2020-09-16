package com.mitrais.cdc.service;

import com.mitrais.cdc.model.AccountInfo;
import com.mitrais.cdc.model.ListAccount;

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
}
