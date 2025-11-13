package com.asset_management.dantruong.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.asset_management.dantruong.trasaction.Asset;
import com.asset_management.dantruong.user_services.Users;

public class helpMethod {

    private static final Scanner sc = new Scanner(System.in);

    public  int readInt(String promt, int min, int max){
        while (true) {
            System.out.print(promt);
            try {
                int value = Integer.parseInt(sc.nextLine());
                if (value < min || value > max) {
               System.out.println(("Please make sure the data you enter is valid. "));
               continue;    
            }
            return value;
            } catch (NumberFormatException e) {
               System.out.println("❌ Your selection is in the wrong format. It must be between " + min + " and " + max + ".");
            }
        }
    }

    public String readString(String prompt){
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public double readDouble(String prompt, double min){
        while (true) {
            System.out.print(prompt);
        try {
             double value = Double.parseDouble(sc.nextLine());
            if (value <= min) throw new IllegalArgumentException("The price is invaild. Please try again!");
            return value;
        } catch (Exception e) {
          System.out.println("❌ Please enter a valid number greater than " + min + ".");
        }
        }
    }

    public Date readDate (String prompt){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        while (true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine();
                return sdf.parse(input);
            } catch (ParseException e) {
               System.out.println("❌ Invalid date format. Please use dd/MM/yyyy");
            }
        }
    }

    public String readAccountName(String promt, Map<String, Users> users){
        String AccountName;
        while (true) {
            System.out.print(promt);
            try {
                AccountName = sc.nextLine().trim();
                if (users.containsKey(AccountName)) {
                System.out.println("❌ Error: Account '" + AccountName + "' already exists. Please try again.");
                continue;
                }
            if (AccountName.length() != 6) {
                System.out.println("⚠️ Account must have exactly 6 digits.");
                continue;
            }
            if (!AccountName.matches("\\d{6}")) {
                    System.out.println("❌ Invalid format! Account must contain only digits (0-9).");
                    continue;
                }
                return AccountName;
            } catch (Exception e) {
                System.out.println("❌ Error while reading input. Please try again.");
            }
        }
    }

    public String readAccountPassWord(String prompt){
        while (true) {
            System.out.print(prompt);

            try {
                String AccountPassWords = sc.nextLine().trim();

                if (AccountPassWords.length() < 8) {
                    System.out.println("⚠️ Password must be at least 8 characters long.");
                    continue;
                }

                if (!AccountPassWords.matches(".*[A-Z].*")) {
                   System.out.println("❌ Password must contain at least one uppercase letter (A–Z).");
                    continue;
                }

                if (!AccountPassWords.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                    System.out.println("❌ Password must contain at least one special character (!@#$%^&* etc).");
                    continue;
                }

                return AccountPassWords;
            } catch (Exception e) {
               System.out.println("❌ Error reading password. Please try again.");
            }
        }
    }


    public Asset isExist(String symbol, List<Asset> assetList){
        if (assetList == null) return null;
        
        for (Asset asset : assetList) {
            if (asset.getSymBol().equals(symbol)) return asset;
        }
        return null;
    }
}
