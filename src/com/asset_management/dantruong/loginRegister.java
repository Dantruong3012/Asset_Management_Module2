package com.asset_management.dantruong;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.asset_management.dantruong.helper.helpMethod;

public class loginRegister {
    private static final Scanner sc = new Scanner(System.in);
    public static void diplayOptions() {
    System.out.println("\n---------------Welcome to DT Portfolio Management---------------");
    System.out.println("\n1. Log in to your portfolio.");
    System.out.println("\n2. Register for a portfolio management account.");
    System.out.println("\n0. exit the application.");
    int selection = helpMethod.readInt("\nPlease select function: ", 0,2);

    switch (selection) {
        case 1:
               handleLogin();
            break;

        case 2:
               handleRegister();
            break;
        
        case 0:
                System.out.println("\n-------Exited the program.-------");
            break;
        default: 
            break;
    }
    
}

public static void handleRegister(){

    System.out.println("\n--- REGISTER AN ACCOUNT ---");
    Map<String, String> users = loadUser();

    String userAccount = helpMethod.readAccountName("\nPlease enter your account (6 digits only): ", users);
    
    String userPassword = helpMethod.readAccountPassWord("\npassword (include one special character, and at least one uppercase letter): ");
    
    users.put(userAccount, userPassword);

    if (saveUser(users)) {
        System.out.println("\nAccount registration successful. ");
    }else{
        System.out.println("\nThe system is experiencing problems, please try again later.");
    }
}

public static void handleLogin(){
    System.out.println("\n--- LOG IN ---");
    System.out.print("\nEnter username: ");
    String userLoginName = sc.nextLine();
    System.out.print("\nEnter password: ");
    String userLoginPassword = sc.nextLine();
    Map<String, String> usersLoginInfor = loadUser();
    if (!usersLoginInfor.containsKey(userLoginName)) {
        System.out.println("\nLogin failed: Username does not exist.");
    }else if (usersLoginInfor.get(userLoginName).equals(userLoginPassword)) {
        System.out.println("----------------------------------------");
        System.out.println("welcome" + "! You have logged in successfully.");
        System.out.println("----------------------------------------");
        System.out.println("\nWhat would you like to do to day?");

        // HIỆN THỊ CÁC LỰA CHỌN ĐỂ XEM DOANH MỤC CÁ NHÂN Ở ĐÂY.

        System.out.println("\nThe dashboard is implementing!");
    }else{
        System.out.println("\nLogin failed: Wrong password.");
    }
}


@SuppressWarnings("unchecked")

public static Map<String, String> loadUser(){
    String FILE_PATH = "src/com/asset_management/dantruong/data/loginInfor_signinInfor/user.dat";
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
        return (Map<String, String>) ois.readObject(); // Đọc một Object từ file và ép kiểu nó về Map
    } catch (FileNotFoundException | EOFException e) {
        // Đây là trường hợp QUAN TRỌNG:
        // File chưa tồn tại (lần chạy đầu tiên) hoặc file rỗng.
        // Chúng ta trả về một HashMap rỗng để chương trình bắt đầu.
        return new HashMap<>();
    } catch (IOException | ClassNotFoundException e){
        System.out.println("Lỗi nghiêm trọng khi đọc 'database': " + e.getMessage());
        // Trong trường hợp lỗi, trả về Map rỗng để tránh sập chương trình
        return new HashMap<>();
    }
}

public static boolean saveUser(Map<String, String> users){
    String FILE_PATH = "src/com/asset_management/dantruong/data/loginInfor_signinInfor/user.dat";
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
        oos.writeObject(users);
        return true;
    } catch (IOException e) {
       System.out.println("error saving user 'database': " + e.getMessage());
       return false;
    }
}

}
