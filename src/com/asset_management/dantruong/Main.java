package com.asset_management.dantruong;

import com.asset_management.dantruong.helper.helpMethod;
import com.asset_management.dantruong.portfolio.Portfolio;
import com.asset_management.dantruong.user_operations.Dashboard;
import com.asset_management.dantruong.user_services.loginRegister;

public class Main {
    public static void main(String[] args) {
        helpMethod helper = helpMethod.getInstance();
        loginRegister loginRegister = new loginRegister();
        String user = loginRegister.diplayOptions();

        if (user != null) {
            Portfolio portfolio = new Portfolio(helper, user);
            portfolio.loadAsset();
            Dashboard dashboard = new Dashboard(user, portfolio);
            dashboard.showMenu();
        } else {
            System.out.println("\nExited the application. Bye! ");
        }

    }
}
