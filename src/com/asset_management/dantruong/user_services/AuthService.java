package com.asset_management.dantruong.user_services;
import java.util.Map;
import java.util.Scanner;
import com.asset_management.dantruong.helper.HelpMethod;

public class AuthService{
    private final HelpMethod helper;
    private final IUserService userServices;
    private static final Scanner sc = new Scanner(System.in);
    public AuthService(){
        this.helper = HelpMethod.getInstance();
        this.userServices = UserService.getIntanceUserService();
    }

    public String diplayOptions() {
    boolean runing = true;
    while (runing) {
    System.out.println("\n---------------Welcome to DT Portfolio Management---------------");
    System.out.println("\n1. Log in to your portfolio.");
    System.out.println("\n2. Register for a portfolio management account.");
    System.out.println("\n0. exit the application.");
    int selection = helper.readInt("\nPlease select function: ", 0,2);

    switch (selection) {
        case 1:
            String loggedInUser = handleLogin();
                if (loggedInUser != null) {
                    runing = false;     
                    return loggedInUser; 
                }
                break;

        case 2:
               handleRegister();
            break;
        
        case 0:
            System.out.println("\n-------Exited the program.-------");
            runing = false;
            break;
        default: 
                System.out.println("\n Invalid selection. Please try again.");
            break;
    }
    
}  
    return null;
}

public void handleRegister(){

    System.out.println("\n--- REGISTER AN ACCOUNT ---");
    Map<String, Users> users = userServices.loadUser();

    String userAccount = helper.readAccountName("\nPlease enter your account (6 digits only): ", users);
    
    String userPassword = helper.readAccountPassWord("\npassword (include one special character, and at least one uppercase letter): ");

    String displayName = helper.readString("\nPlease enter your diplay name: ");
    
    Users nUsers = new Users(userAccount, userPassword, displayName);
    users.put(userAccount, nUsers);

    if (userServices.saveUser(users)) {
        System.out.println("\nAccount registration successful. ");
    }else{
        System.out.println("\nThe system is experiencing problems, please try again later.");
    }
}

public String handleLogin(){
    System.out.println("\n--- LOG IN ---");
    System.out.print("\nEnter usercode: ");
    String userLogin = sc.nextLine();
    System.out.print("\nEnter password: ");
    String userLoginPassword = sc.nextLine();
    Map<String, Users> usersLoginInfor = userServices.loadUser();
    if (!usersLoginInfor.containsKey(userLogin)) {
        System.out.println("\nLogin failed: Username does not exist.");
    }else {
      Users user = usersLoginInfor.get(userLogin);
      if (user.getPassWord().equals(userLoginPassword)) {
        System.out.println("\nwelcome " + user.getDiplayName() + " ! You have logged in successfully.");
        System.out.println("----------------------------------------");
        return user.getDiplayName();
      }else{
        System.out.println("\nLogin failed: Wrong password.");
      }
    }
    return null;
}

}
