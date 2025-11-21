package com.asset_management.dantruong;

import com.asset_management.dantruong.helper.HelpMethod;
import com.asset_management.dantruong.portfolio.Portfolio;
import com.asset_management.dantruong.user_operations.Dashboard;
import com.asset_management.dantruong.user_services.AuthService;

public class Main {
    public static void main(String[] args) {
        HelpMethod helper = HelpMethod.getInstance();
        AuthService loginRegister = new AuthService();
        String user = loginRegister.diplayOptions();

        if (user != null) {
            Portfolio portfolio = new Portfolio(helper, user);
            Dashboard dashboard = new Dashboard(user, portfolio);
            dashboard.showMenu();
        } else {
            System.out.println("\nExited the application. Bye! ");
        }

    }
}
