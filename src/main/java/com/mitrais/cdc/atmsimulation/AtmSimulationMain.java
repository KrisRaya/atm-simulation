package com.mitrais.cdc.atmsimulation;

import com.mitrais.cdc.atmsimulation.model.AccountInfo;
import com.mitrais.cdc.atmsimulation.controller.AtmController;
import com.mitrais.cdc.atmsimulation.model.ListAccount;
import com.mitrais.cdc.atmsimulation.service.AtmService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AtmSimulationMain {

    public static void main(String[] args) {
        final List<AccountInfo> accountInfos = new ArrayList<>(Arrays.asList(
                new AccountInfo("John Doe", "112233", 100, "012108"),
                new AccountInfo("Jane Doe", "112244", 30, "932012")));
        final ListAccount listAccount = new ListAccount();
        listAccount.setAccountInfoList(accountInfos);

        final AtmService atmService = new AtmService(listAccount);
        final AtmController atmController = new AtmController(atmService);
        atmController.welcomeScreen();
    }
}
